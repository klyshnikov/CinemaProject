package ru.hse.cinema.project.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class User (
    @JsonProperty("password")
    val password : String
) {

}