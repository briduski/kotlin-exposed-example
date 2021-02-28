package org.briduski.kotlinexample.dsl.tables


import org.jetbrains.exposed.sql.Table

object Customers : Table("CUSTOMERS") {
    val id = integer("customer_id").autoIncrement("Customer_Id_Seq")// this name is not used?, generated: CUSTOMERS_customer_id_seq
    //   id            int4 NOT NULL DEFAULT nextval('CST_SSPC_STATUS_PER_CARD_PRN'::regclass),
    val name = text("name") // Column<String>
    val status = text("status").nullable()  // Column<String?>
    val pending_order = bool("pending_order").nullable()
    override val primaryKey = PrimaryKey(id, name = "PK_Customer_Id")
}