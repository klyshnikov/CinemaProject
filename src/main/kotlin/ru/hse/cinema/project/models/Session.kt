package ru.hse.cinema.project.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate


@JsonInclude(JsonInclude.Include.NON_NULL)
data class Session(
    @JsonProperty("start")
    var start: LocalDate,

    @JsonProperty("end")
    var end: LocalDate,

    @JsonProperty("seats")
    var seats: ArrayList<Seat>,

    @JsonProperty("name")
    var name: String,

    @JsonProperty("seatNumber")
    var seatsNumber: Int
) {}