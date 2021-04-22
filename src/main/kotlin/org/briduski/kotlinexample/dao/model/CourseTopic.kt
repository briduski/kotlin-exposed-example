package org.briduski.kotlinexample.dao.model

import org.briduski.kotlinexample.dao.tables.Courses
import org.briduski.kotlinexample.dao.tables.CoursesTopic
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class CourseTopic(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CourseTopic>(CoursesTopic)
    var course_topic by  CoursesTopic.course_topic
    var topic_id by  CoursesTopic.topic_id
}
