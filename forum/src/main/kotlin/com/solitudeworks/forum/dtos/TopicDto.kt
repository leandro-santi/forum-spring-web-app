package com.solitudeworks.forum.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TopicDto(
    @field:NotEmpty @field:Size(min = 5, max = 100) val title: String,
    @field:NotEmpty @field:Size(min = 5, max = 300) val question: String,
    @field:NotNull val idCourse: Int,
    @field:NotNull val idAuthor: Int
) {
}