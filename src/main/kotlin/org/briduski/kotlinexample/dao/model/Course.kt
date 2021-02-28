package org.briduski.kotlinexample.dao.model

import org.briduski.kotlinexample.dao.tables.Courses
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/*
data class Course (
        val name:String,
        val duration: Duration,
        val topic:String?,
        val id:Int?
)*/
class Course(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Course>(Courses)
    var name by  Courses.name
    var duration by  Courses.duration
    var topic by  Courses.topic
    var author by Author referencedOn Courses.author // foreign key
}

