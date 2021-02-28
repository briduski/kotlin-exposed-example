package org.briduski.kotlinexample.dao.tables

import org.briduski.kotlinexample.dao.tables.Authors.autoIncrement
import org.briduski.kotlinexample.dao.tables.Authors.nullable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import java.time.Duration

/*
object Courses: Table("COURSES") {
    val id = integer("course_id").autoIncrement("Courses_Id_Seq")// this name is not used?, generated: COURSES_course_id_seq
    val name = text("name")  // Column<String>
    val duration = integer("duration")  // Column<String>
    val topic = text("name").nullable()  // Column<String?>
    override val primaryKey = PrimaryKey(id, name = "PK_Course_Id")
}*/

object Courses: IntIdTable("COURSES") {
    val name = varchar("course_name", 50).index()  // Column<String>
    val duration = long("course_duration")  // Column<Long>
    val topic = text("course_topic").nullable()
    val author = reference("author", Authors) // foreign key
}

