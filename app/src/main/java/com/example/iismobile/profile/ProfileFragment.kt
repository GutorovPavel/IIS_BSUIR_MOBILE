package com.example.iismobile.profile

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.example.iismobile.R
import com.example.iismobile.announcements.AnnouncementsController
import com.example.iismobile.announcements.holders.AnnouncementViewHolder
import com.example.iismobile.common.SharedModel
import com.example.iismobile.common.TextWatchWrapper
import com.example.iismobile.common.adapters.SimpleAdapter
import com.example.iismobile.common.api.views.ModelDataFragment
import com.example.iismobile.common.data.Announcement
import com.example.iismobile.common.data.Skill
import com.example.iismobile.profile.data.ProfileInfo
import com.example.iismobile.common.utils.asVisibility
import com.example.iismobile.profile.adapters.SkillsAdapter
import com.example.iismobile.profile.data.BadgeType
import com.example.iismobile.profile.dialogs.ReferenceAddDialog
import com.example.iismobile.profile.dialogs.SkillAddDialog
import com.example.iismobile.profile.dialogs.SummaryHelpDialog
import com.example.iismobile.profile.dialogs.SummarySubmitDialog
import com.example.iismobile.profile.holders.ReferenceViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.f_profile.view.*
import kotlinx.android.synthetic.main.i_profile_content.*
import kotlinx.android.synthetic.main.i_profile_content.view.*
import kotlinx.android.synthetic.main.i_profile_header.view.*
import org.kodein.di.generic.instance
import kotlin.contracts.ExperimentalContracts
import kotlin.math.abs

@ExperimentalContracts
class ProfileFragment : ModelDataFragment<ProfileFragment.ProfileViewState, ProfileInfo>(R.layout.f_profile) {

    sealed class ProfileViewState {

        abstract val headerContentVisibility: Boolean
        abstract val headerProgressVisibility: Boolean
        abstract val headerErrorVisibility: Boolean
        abstract val headerCanCollapse: Boolean
        abstract val buttonsAvailable: Boolean

        class ProfileFilled(val profileInfo: ProfileInfo): ProfileViewState() {
            override val headerContentVisibility: Boolean = true
            override val headerProgressVisibility: Boolean = false
            override val headerCanCollapse: Boolean = true
            override val headerErrorVisibility: Boolean = false
            override val buttonsAvailable: Boolean = true
        }

        class ProfileError(val message: String, val retryHandler: View.() -> Unit): ProfileViewState() {
            override val headerContentVisibility: Boolean = false
            override val headerProgressVisibility: Boolean = false
            override val headerCanCollapse: Boolean = false
            override val headerErrorVisibility: Boolean = true
            override val buttonsAvailable: Boolean = false
        }

        object ProfileLoading: ProfileViewState() {
            override val headerContentVisibility: Boolean = false
            override val headerProgressVisibility: Boolean = true
            override val headerCanCollapse: Boolean = true
            override val headerErrorVisibility: Boolean = false
            override val buttonsAvailable: Boolean = false
        }
    }

    private val model: SharedModel by instance()

    private val controller by lazy { ProfileController(model) }
    private val announcementsController by lazy { AnnouncementsController(model) }

    private val skillsAdapter by lazy { SkillsAdapter(::onSkillAddClick, ::onSkillClick, ::onSkillRemove) }
    private val referencesAdapter by lazy { SimpleAdapter(R.layout.i_reference, ::ReferenceViewHolder) }
    private val announcementsAdapter by lazy { SimpleAdapter(R.layout.i_announcement,
            ::AnnouncementViewHolder) }
    private val summaryTextListener = TextWatchWrapper(onTextChanged = ::onSummaryEditChange)

    private val summarySubmitDialog by lazy { SummarySubmitDialog(cacheController, controller,
            ::onSummaryUpdated, ::onSummaryUpdateFailed) }
    private val summaryHelpDialog by lazy { SummaryHelpDialog() }
    private val referenceAddDialog by lazy { ReferenceAddDialog(cacheController, controller, {
        referencesAdapter.setData(it)
    }, { message ->
        view?.let { Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
    }) }
    private val skillAddDialog by lazy { SkillAddDialog({
        view?.updateData(
                useCurrentCredentials = true,
                forceUpdate = true
        ) }, cacheController, controller) }


    override fun View.onView() {
        with(skills_view) {
            adapter = skillsAdapter
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        }
        with(references_view) {
            adapter = referencesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        with(announcements_view) {
            adapter = announcementsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        profile_app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBar, offset ->
            if (appBar.totalScrollRange == 0) return@OnOffsetChangedListener
            val value = abs(offset).toFloat() / appBar.totalScrollRange.toFloat()
            profile_title_name.alpha = value
            profile_header_content.referencedIds.forEach { findViewById<View>(it).alpha = 1 - value }
        })

        button_papers.setOnClickListener {
            findNavController().navigate(R.id.fragment_papers)
        }

        button_exam_sheets.setOnClickListener {
            findNavController().navigate(R.id.fragment_sheets)
        }


        button_settings.setOnClickListener {
            findNavController().navigate(R.id.fragment_preferences)
        }

        profile_logout.setOnClickListener {
            findNavController().navigate(R.id.action_profile_logout,
                    bundleOf("logout" to true))
        }

        info_hint.setOnClickListener {
            summaryHelpDialog.createNew(context)
        }
        reference_add.setOnClickListener {
            referenceAddDialog.createNew(context, bundleOf(
                "list" to referencesAdapter.list.toTypedArray()
            ))
        }

        updateAnnouncements(emptyList())
        updateData(true)
    }

    private fun getCountUpdate(type: BadgeType, f: () -> Single<Int>) {
        f().observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it == 0)
                updateBadge(type)
            else updateBadge(type, it)
        }, { updateBadge(type) })
    }

    private fun getAnnouncementsUpdate() {
        announcementsController.updateAnnouncements(false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view?.updateAnnouncements(it) }, {  })
    }

    override fun onDataUpdated(data: ProfileInfo) {
        getCountUpdate(BadgeType.PAPERS, controller::updatePapersCount)
        getCountUpdate(BadgeType.SHEETS, controller::updateSheetsCount)
        getAnnouncementsUpdate()
    }

    override fun getDataUpdate(forceUpdate: Boolean): Single<ProfileInfo> =
            controller.updateProfileInfo(forceUpdate)

    override fun getLoadingState(): ProfileViewState =
        ProfileViewState.ProfileLoading

    override fun getErrorState(it: Throwable, msg: String, retryAction: View.() -> Unit): ProfileViewState =
        ProfileViewState.ProfileError(msg, retryAction)

    override fun getFilledState(it: ProfileInfo): ProfileViewState =
        ProfileViewState.ProfileFilled(it)

    override fun View.applyState(newState: ProfileViewState) = with(newState) {
        profile_header_content.visibility = headerContentVisibility.asVisibility()
        profile_title_name.visibility = headerContentVisibility.asVisibility()
        profile_progress.visibility = headerProgressVisibility.asVisibility()
        profile_error.visibility = headerErrorVisibility.asVisibility()
        profile_summary.visibility = headerContentVisibility.asVisibility()

        arrayOf(button_papers, button_exam_sheets, button_settings, info_edit, reference_add)
            .forEach { it.isEnabled = buttonsAvailable }

        val params = profile_collapsing_toolbar.layoutParams as AppBarLayout.LayoutParams
        if (headerCanCollapse) {
            params.scrollFlags =
                SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or SCROLL_FLAG_SNAP or SCROLL_FLAG_SCROLL
        } else {
            params.scrollFlags = 0
            profile_app_bar.setExpanded(true, true)
            profile_content_scroll.smoothScrollTo(0, 0)
        }

        // On every state update
        // we'll need to reset info edit state
        // i guess..
        setInfoViewState()

        if (this is ProfileViewState.ProfileFilled) {
            with(profileInfo) {
                profile_header_name.text = fullName
                profile_title_name.text = fullName

                profile_birth_date.text = birthDate
                profile_faculty.text = facultyString
                profile_rate_bar.progress = rate
                profile_rate_bar.max = 10

                Glide.with(context)
                    .load(photoUrl)
                    .into(profile_image)

                skillsAdapter.setData(skills)
                skillsAdapter.cleanRemovable()

                referencesAdapter.setData(references)
                profile_no_references.visibility = references.isEmpty().asVisibility()

                if (summary.isNullOrBlank()) {
                    profile_summary.text?.clear()
                    profile_summary.setHint(R.string.no_summary)
                } else {
                    profile_summary.setText(summary)
                }
                context.storeProfileSummary(summary ?: "")
            }

        }

        if (this is ProfileViewState.ProfileError) {
            profile_error_message.text = message
            profile_refresh.setOnClickListener(retryHandler)
        }
    }

    private fun updateBadge(type: BadgeType, value: Int? = null) = view?.run {
        val view = when (type) {
            BadgeType.PAPERS -> papers_badge
            BadgeType.SHEETS -> sheets_badge
        }
        if (value == null) {
            clearBadge(view)
        } else {
            setBadge(view, value)
        }
    }

    private fun setBadge(badge: TextView, count: Int) {
        badge.visibility = View.VISIBLE
        badge.text = "$count"
    }

    private fun clearBadge(badge: TextView) {
        badge.visibility = View.GONE
    }

    private fun setInfoEditState() {
        profile_summary.inputType =
                InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                        InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
        profile_summary.setTextIsSelectable(true)
        profile_summary.setBackgroundResource(com.google.android.material.R.drawable.abc_edit_text_material)
        profile_summary.addTextChangedListener(summaryTextListener)

        info_edit.setImageResource(R.drawable.ic_round_check_24)
        info_edit.setOnClickListener { onSummarySubmit() }
    }

    private fun setInfoViewState() {
        profile_summary.inputType = InputType.TYPE_NULL
        profile_summary.setTextIsSelectable(false)
        profile_summary.setBackgroundResource(0)
        profile_summary.removeTextChangedListener(summaryTextListener)

        info_edit.setImageResource(R.drawable.ic_round_edit_24)
        info_edit.setOnClickListener { setInfoEditState() }
    }

    private fun onSkillAddClick(v: View) = view?.run {
        skillAddDialog.createNew(context)
    }

    private fun onSkillClick(skill: Skill, index: Int) {
        if (skillsAdapter.removableIndex == index) {
            skillsAdapter.cleanRemovable()
        } else {
            skillsAdapter.removableIndex = index
        }
    }

    private fun onSkillRemove(skill: Skill) {
        view?.run {
            // TODO: should i make dialog?
            controller.removeSkill(skill)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ updateData(useCurrentCredentials = true, forceUpdate = true) }) {
                        Toast.makeText(context, getString(R.string.remove_skill_fail, skill.name), Toast.LENGTH_SHORT).show()
                    }
            skillsAdapter.cleanRemovable()
        }
    }

    private fun onSummaryEditChange(s: CharSequence, a: Int, b: Int, c: Int) {
        context?.getSharedPreferences("profile", Context.MODE_PRIVATE)?.edit {
            putString("edit_summary", "$s")
        }
    }

    private fun onSummarySubmit() = view?.run {
        val text = profile_summary.text?.toString() ?: ""
        val original = context?.getSharedPreferences("profile", Context.MODE_PRIVATE)
                ?.getString("summary", "") ?: ""

        if (text == original)
            return setInfoViewState()

        val displayText = if (text.isBlank()) "<b>&lt;пусто&gt;</b>" else text

        summarySubmitDialog.createNew(context, bundleOf(
            "displayText" to displayText,
            "text" to text
        ))
    }

    private fun onSummaryUpdated() = view?.run {
        updateData(useCurrentCredentials = true, forceUpdate = true)
    }

    private fun onSummaryUpdateFailed(alert: Boolean) = view?.run {
        if (alert) {
            Toast.makeText(context, "Не удалось обновить информацию.", Toast.LENGTH_LONG).show()
        }
        updateData(useCurrentCredentials = true, forceUpdate = false)
    }

    private fun View.updateAnnouncements(list: List<Announcement>) {
        announcementsAdapter.setData(list)
        announcements_placeholder.visibility = list.isEmpty().asVisibility()
    }

    private fun Context.storeProfileSummary(summary: String) {
        getSharedPreferences("profile", Context.MODE_PRIVATE).edit {
            putString("summary", summary)
        }
    }

}
