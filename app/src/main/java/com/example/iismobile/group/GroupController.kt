package com.example.iismobile.group

import com.example.iismobile.common.data.GroupMember
import com.example.iismobile.common.data.InternalException
import com.example.iismobile.common.data.SpecialGroupMember
import com.example.iismobile.group.data.Group
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GroupController (
    val model: IGroupModel,
) {

    fun updateGroupInfo(forceUpdate: Boolean): Single<Group> =
            Single.create<Group> {
                val groupInfo = model.getGroupInfo(allowCache = !forceUpdate)
                        ?: return@create it.onError(InternalException("Невозможно получить данные о группе"))
                val supervisorFilter = { m: GroupMember -> m.position == "Староста" }
                val supervisor = groupInfo.members.firstOrNull(supervisorFilter)
                val leader = groupInfo.members.firstOrNull { m -> m.position == "Староста группы" }
                val group = Group(
                        groupInfo.numberGroup,
                        supervisor?.let(::SpecialGroupMember),
                        leader?.let(::SpecialGroupMember),
                        groupInfo.members
                                .filterNot(supervisorFilter)
                                .sortedBy(GroupMember::fullName)
                )
                it.onSuccess(group)
            }.subscribeOn(Schedulers.io())

}