package com.example.iismobile.common.typeadapters

import com.example.iismobile.common.data.*
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

object GsonScheduleTypeAdapter : TypeAdapter<ScheduleType>() {
    override fun write(out: JsonWriter, value: ScheduleType) {
        out.value(value.stringValue)
    }

    override fun read(reader: JsonReader): ScheduleType {
        val string = reader.nextString()

        return setOf(Lecture, Practice, Lab)
            .firstOrNull { it.stringValue == string }
            ?: UnknownType(string)
    }
}