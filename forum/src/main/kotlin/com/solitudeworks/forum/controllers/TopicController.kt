package com.solitudeworks.forum.controllers

import com.solitudeworks.forum.dtos.forms.TopicForm
import com.solitudeworks.forum.dtos.forms.UpdateTopicForm
import com.solitudeworks.forum.dtos.views.TopicView
import com.solitudeworks.forum.services.TopicService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topics")
class TopicController(private val service: TopicService) {

    //region GET
    @GetMapping
    fun listTopics(): List<TopicView> {
        return service.list()
    }

    @GetMapping("/{id}")
    fun searchById(@PathVariable id: Int): TopicView {
        return service.searchById(id)
    }
    //endregion GET

    //region POST -> Returns code 201 with a response body
    @PostMapping
    fun registerTopic(
        @RequestBody @Valid form: TopicForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicView> {
        val topicView = service.registerTopic(form)
        val uri = uriBuilder.path("/topics/${topicView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicView)
    }
    //endregion

    //region PUT -> Returns code 200 with a response body
    @PutMapping
    fun updateTopic(@RequestBody @Valid form: UpdateTopicForm): ResponseEntity<TopicView> {
        val topicView = service.updateTopic(form)
        return ResponseEntity.ok(topicView)

    }
    //endregion

    //region DELETE -> Returns code 204 with no content as response
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTopic(@PathVariable id: Int) {
        service.deleteTopic(id)
    }
    //endregion

    //region PATCH
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateFieldsTopic(@RequestBody @Valid form: UpdateTopicForm) {
        service.updateFieldsTopic(form)
    }
    //endregion

}