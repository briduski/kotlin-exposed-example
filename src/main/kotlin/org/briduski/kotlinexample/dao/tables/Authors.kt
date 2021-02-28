package org.briduski.kotlinexample.dao.tables

import org.briduski.kotlinexample.dsl.tables.Customers.autoIncrement
import org.briduski.kotlinexample.dsl.tables.Customers.nullable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

/*object Authors : Table("AUTHORS"){
    val id = integer("author_id").autoIncrement("Author_Id_Seq")// this name is not used?, generated: AUTHORS_author_id_seq
    val rate = integer("rate").nullable() // Column<Int>
    val name = text("name")  // Column<String>
    override val primaryKey = PrimaryKey(id, name = "PK_Author_Id")
}*/
object Authors : IntIdTable("AUTHORS") {
    val name = varchar("author_name", 50)
    val rate = integer("author_rate")
}