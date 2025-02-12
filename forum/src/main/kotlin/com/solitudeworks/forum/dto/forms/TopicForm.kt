package com.solitudeworks.forum.dto.forms

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TopicForm(
    @field:NotNull val id: Int,
    @field:NotEmpty(message = "It can not be empty.") @field:Size(min = 5, max = 100) val title: String,
    @field:NotEmpty(message = "It can not be empty.") @field:Size(min = 5, max = 300) val message: String,
    @field:NotNull(message = "It can not be null.") val idCourse: Int,
    @field:NotNull(message = "It can not be null.") val idAuthor: Int,
)
