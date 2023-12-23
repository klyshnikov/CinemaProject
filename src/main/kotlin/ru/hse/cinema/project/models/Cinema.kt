package ru.hse.cinema.project.models

import com.fasterxml.jackson.annotation.JsonInclude
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Objects

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Cinema(
    val cinemaName:String
) {
    companion object {
        var sessionsNames: ArrayList<String> = arrayListOf()
        val currentTime: LocalDate
            get() = LocalDate.now()
    }
}