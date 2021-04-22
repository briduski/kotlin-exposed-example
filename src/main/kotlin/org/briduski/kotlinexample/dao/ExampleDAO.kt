package org.briduski.kotlinexample.dao

import org.briduski.kotlinexample.dao.model.Author
import org.briduski.kotlinexample.dao.model.Course
import org.briduski.kotlinexample.dao.model.CourseTopic
import org.briduski.kotlinexample.dao.tables.Authors
import org.briduski.kotlinexample.dao.tables.Courses
import org.briduski.kotlinexample.dao.tables.CoursesTopic
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration

fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
//    Database.connect("jdbc:postgresql://localhost:7878/postgres", driver = "org.postgresql.Driver", user = "postgres", password = "postgres")
    transaction {
        SchemaUtils.drop (Courses, Authors, CoursesTopic)
    }

    transaction {
     //   addLogger(StdOutSqlLogger)

        SchemaUtils.create(CoursesTopic,Courses, Authors )
        val topic1 = CourseTopic.new {
            course_topic = "Kafka"
            topic_id = 100
        }
       val topic2 = CourseTopic.new {
            course_topic = "Java"
            topic_id = 200
        }
        val author1 = Author.new {
            name = "Stephan M."
            rate = 5454
        }
        val author2 = Author.new {
            name = "Dilip D."
            rate = 8787
        }
       val course1 = Course.new {
            name = "Kafka Streams Introduction"
            duration = Duration.ofHours(5L).toHours()
            topic_id = 100
            author = author1
        }
        val course2 = Course.new {
            name = "Kafka Connect - Advanced concepts"
            duration = Duration.ofHours(9L).toHours()
            topic_id = 100
            author = author1
        }
        val course3 = Course.new {
            name = "Spring Complete"
            duration = Duration.ofHours(9L).toHours()
            topic_id = 200
            author = author2
        }
        println("\n * * * * * * * * ")
        println("-> All Authors:")

        for (author in Authors.selectAll()) {
            println(" ->  id: ${author[Authors.id]}: " + " name: ${author[Authors.name]}+  rate: ${author[Authors.rate]}")
        }
        println("\n * * * * * * * * ")
        println("-> All CoursesTopics:")

        for (courseTopic in CoursesTopic.selectAll()) {
            println(" ->  ${courseTopic[CoursesTopic.topic_id]}:" +   " ${courseTopic[CoursesTopic.course_topic]}")
        }

        println("\n * * * * * * * *")
        println("-> All courses:")

        for (course in Courses.selectAll()) {
            println(" -> courseName: ${course[Courses.name]}:" +   ", courseTopicId: ${course[Courses.topic]}:" +
                    ", courseId:  ${course[Courses.id]}:" +   ",  authorId: ${course[Courses.author]}:")
        }

//        println(" * * * * * * * * \n")
//        println("<><><>  Authors: ${Author.all().joinToString {it.name}}")
//        println("<><><> Courses from author ${author1.name}: => " +
//                "${author1.authors.joinToString {it.name}}")
//
//        println(" * * * * * * * * \n")
//        println("\n== == Extended courses: ${Course.find { Courses.duration greaterEq 6 }.joinToString {it.name}}")

        println("\n * * * * * * * * ")
        val userTable1 = CoursesTopic.alias("1")
//        public fun <C1 : org.jetbrains.exposed.sql.ColumnSet, C2 : org.jetbrains.exposed.sql.ColumnSet>
//                C1.innerJoin(otherTable: C2,
//                                onColumn: C1.() -> org.jetbrains.exposed.sql.Expression<*>,
//                             otherColumn: C2.() -> org.jetbrains.exposed.sql.Expression<*>)
//
//                : org.jetbrains.exposed.sql.Join { /* compiled code */ }

         Courses
                .innerJoin(userTable1 , {Courses.topic}, {userTable1[CoursesTopic.topic_id]} )
//                .innerJoin(userTable1 , {Courses.topic}, {CoursesTopic.topic_id} ) /// error!!!
                .selectAll()
                .map {
                    println("A1: ${it[Courses.name]}" +
                            " - course_id-orig: ${it[Courses.topic]} " +
                            " - course_id-ref: ${it[userTable1[CoursesTopic.topic_id]]} " +
                            " - course_name-ref: ${it[userTable1[CoursesTopic.course_topic]]} ")
                }

/*
 Cannot join with org.briduski.kotlinexample.dao.tables.CoursesTopic@5af9d5a8 as there is no matching primary key/foreign key pair and constraint missing

        (Courses innerJoin CoursesTopic)
                .selectAll()
            //.select { Courses.topic eq CoursesTopic.topic_id }
            .map {
                println("A2: ${it[Courses.name]}" +
                        " - course_id-orig: ${it[Courses.topic]} " +
                        " - course_id-ref: ${it[CoursesTopic.topic_id]} " +
                        " - course_name-ref: ${it[CoursesTopic.course_topic]} ")
            }
 */
        val complexJoin = Join(
                Courses, CoursesTopic,
                onColumn = Courses.topic, otherColumn = CoursesTopic.topic_id,
                joinType = JoinType.INNER)
        complexJoin.selectAll().map {
            println("A3: ${it[Courses.name]}" +
                    " - course_id-orig: ${it[Courses.topic]} " +
                    " - course_id-ref: ${it[CoursesTopic.topic_id]} " +
                    " - course_name-ref: ${it[CoursesTopic.course_topic]} ")
        }
 




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
