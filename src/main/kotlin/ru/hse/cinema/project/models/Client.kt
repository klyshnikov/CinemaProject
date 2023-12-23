package ru.hse.cinema.project.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Client(
    @JsonProperty("name")
    var name: String
) {}