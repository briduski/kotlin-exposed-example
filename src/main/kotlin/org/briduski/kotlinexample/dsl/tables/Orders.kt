package org.briduski.kotlinexample.dsl.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object Orders : Table("ORDERS") {
    val id = integer("order_id").autoIncrement("Order_Id_Seq")// this name is not used?, generated: ORDERS_order_id_seq
    val date = datetime("order_date") // Column<String>
    val price = integer("price").nullable() // Column<Int?>
    val customer_id = integer("customer_id") references Customers.id
    val completed = bool("order_completed").default(false)
    override val primaryKey = PrimaryKey(id, name = "PK_Order_Id")
}