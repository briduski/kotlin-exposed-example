package org.briduski.kotlinexample.dao.model

import org.briduski.kotlinexample.dao.tables.Authors
import org.briduski.kotlinexample.dao.tables.Courses
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

/*
data class Author (
       val name:String,
       val rate: Int?,
       val id:Int?)*/

class Author(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Author>(Authors)
    var name by Authors.name
    var rate by Authors.rate
    val authors by Course referrersOn Courses.author // Foreign key for table Courses, used by type Course
}