package com.solitudeworks.forum.service

import com.solitudeworks.forum.dto.forms.TopicForm
import com.solitudeworks.forum.dto.forms.UpdateTopicForm
import com.solitudeworks.forum.dto.views.TopicByCategoryView
import com.solitudeworks.forum.dto.views.TopicView
import com.solitudeworks.forum.exception.NotFoundException
import com.solitudeworks.forum.mapper.TopicFormMapper
import com.solitudeworks.forum.mapper.TopicViewMapper
import com.solitudeworks.forum.model.Topic
import com.solitudeworks.forum.repository.TopicRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val topicRepository: TopicRepository,
    private var topics: List<Topic>,
    private val topicViewMapper: TopicViewMapper,
    private val topicFormMapper: TopicFormMapper,
) {
    private val notFoundExceptionMessage: String = "TOPIC WAS NOT FOUND."

    // GET
    fun list(
        nameCourse: String?,
        pagination: Pageable,
    ): Page<TopicView> {
        val topics =
            if (nameCourse == null) {
                topicRepository
                    .findAll(pagination)
            } else {
                topicRepository
                    .findByCourseName(nameCourse, pagination)
            }
        return topics.map { topic -> topicViewMapper.map(topic) }
    }

    fun searchById(id: Int): TopicView {
        val topic =
            topicRepository.findById(id).orElseThrow {
                NotFoundException(notFoundExceptionMessage)
            }

        return topicViewMapper.map(topic)
    }

    fun report(): List<TopicByCategoryView> = topicRepository.report()

    // POST
    fun registerTopic(form: TopicForm): TopicView {
        val topic = topicFormMapper.map(form)
        topicRepository.save(topic)

        return topicViewMapper.map(topic)
    }

    // PUT
    fun updateTopic(form: UpdateTopicForm): TopicView {
        val topic =
            topicRepository.findById(form.id).orElseThrow {
                NotFoundException(notFoundExceptionMessage)
            }

        topic.title = form.title
        topic.message = form.message

        return topicViewMapper.map(topic)
    }

    // DELETE
    fun deleteTopic(id: Int) {
        topicRepository.deleteById(id)
    }

    // PATCH
    fun updateFieldsTopic(form: UpdateTopicForm) {
        for (topic in topics) {
            if (topic.id == form.id) {
                topic.title = form.title
                topic.message = form.message
                return
            }
        }
        throw NotFoundException(notFoundExceptionMessage)
    }
}
