package org.briduski.kotlinexample.dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object CoursesTopic : IntIdTable("COURSES_TOPIC") {
    val course_topic = text("course_topic")
    val topic_id = integer("topic_id")
}
