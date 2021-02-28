package org.briduski.kotlinexample.dao

import org.briduski.kotlinexample.dao.model.Author
import org.briduski.kotlinexample.dao.model.Course
import org.briduski.kotlinexample.dao.tables.Authors
import org.briduski.kotlinexample.dao.tables.Courses
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration

fun main() {
//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
    Database.connect("jdbc:postgresql://localhost:7878/postgres", driver = "org.postgresql.Driver", user = "postgres", password = "postgres")
    transaction {
        SchemaUtils.drop (Courses, Authors)
    }

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Courses, Authors)
        val author1 = Author.new {
            name = "Stephan M."
            rate = 10
        }
        val author2 = Author.new {
            name = "Dilip D."
            rate = 8
        }
        val course1 = Course.new {
            name = "Kafka Streams Introduction"
            duration = Duration.ofHours(5L).toHours()
            topic = "Kafka"
            author = author1
        }
        val course2 = Course.new {
            name = "Kafka Connect - Advanced concepts"
            duration = Duration.ofHours(9L).toHours()
            topic = "Kafka"
            author = author1
        }
        val course3 = Course.new {
            name = "Spring Complete"
            duration = Duration.ofHours(9L).toHours()
            topic = "Java"
            author = author2
        }

        println("-> All courses:")

        for (course in Courses.selectAll()) {
            println(" ->  ${course[Courses.name]}:" +   " ${course[Courses.topic]}:" +
                    " ${course[Courses.id]}:" +   " ${course[Courses.author]}:")
        }

        println(" * * * * * * * * ")
        println("<><><>  Authors: ${Author.all().joinToString {it.name}}")
        println("<><><> Courses from author ${author1.name}: => " +
                "${author1.authors.joinToString {it.name}}")

        println("== == Extended courses: ${Course.find { Courses.duration greaterEq 6 }.joinToString {it.name}}")
        println(" * * * * * * * * ")
    }
}
/*
fun courseaResultSet(r:ResultRow): Course {
    val c = Course()
    return Course(
            r[Courses.name],
            r[Courses.duration],
            r[Courses.topic]
    )
}
*/
