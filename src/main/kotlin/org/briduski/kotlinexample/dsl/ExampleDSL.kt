package org.briduski.kotlinexample.dsl


//import no.norsktipping.customerovis.domain.CstStatusPerCardTable
import org.briduski.kotlinexample.dsl.model.Customer
import org.briduski.kotlinexample.dsl.model.Order
import org.briduski.kotlinexample.dsl.tables.Customers
import org.briduski.kotlinexample.dsl.tables.Orders
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

//http://www.lib4dev.in/info/JetBrains/Exposed/11765017
// https://github.com/JetBrains/Exposed/issues/1164
fun getLongSeqNumber(seqName: String): String {
    // other: https://github.com/JetBrains/Exposed/wiki/DSL#sequence

    // oracle: https://community.safe.com/s/article/making-use-of-oracle-sequences-when-writing-to-an
    val seqNumber = arrayListOf<Long>()
    transaction {
//                exec("SELECT NEXT VALUE FOR $seqName AS 'seq_no'") { rs ->
        exec("SELECT nextval ('$seqName') AS seq_no") { rs ->
            while (rs.next()) {
                seqNumber.add(rs.getLong("seq_no"))
            }
        }
    }
    return  seqNumber.first().toString()
}
fun main() {
//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
    Database.connect("jdbc:postgresql://localhost:7878/postgres", driver = "org.postgresql.Driver", user = "postgres", password = "postgres")

    // Delete tables in case they exists
    transaction {
        SchemaUtils.drop (Customers, Orders)
    }

    transaction {
        //addLogger(StdOutSqlLogger)
        transaction {
            // Cleaning DB
            SchemaUtils.drop (Customers, Orders)
            SchemaUtils.dropSequence(Sequence("Customer_Id_Seq"))
            SchemaUtils.dropSequence(Sequence("Order_Id_Seq"))
        }
        transaction {
            SchemaUtils.createSequence(Sequence("Customer_Id_Seq", startWith = 5000))
            SchemaUtils.createSequence(Sequence("Order_Id_Seq", startWith = 2000))
            SchemaUtils.create (Orders, Customers)
            // Use the name of the manually created sequences
            exec("  alter table CUSTOMERS alter column customer_id set default nextval('Customer_Id_Seq') ")
            exec("  alter table ORDERS alter column order_id set default nextval('Order_Id_Seq') ")
        }

        transaction {
            val c_id = insertCustomer(Customer("rar","active", null,false))
            val o_id1 = insertOrder(Order(LocalDateTime.now(), 150,c_id,false, null))
            val o_id2 = insertOrder(Order(LocalDateTime.now(), 250,c_id,true, null))
            val o_id3 = insertOrder(Order(LocalDateTime.now(), 350,c_id,false, null))
            val c1 = Customers.select { Customers.name eq "rar" }.map { customerResultSet(it) }.firstOrNull()
            println("customer: $c1")
            val order1 = Orders.select { Orders.completed eq true  }.map { orderResultSet(it) }.firstOrNull()
            println("order-completed: $order1")
            val order2 = Orders.select { Orders.customer_id eq c_id  }
                    .orderBy(Orders.price to SortOrder.DESC)
                    .map { orderResultSet(it) }.firstOrNull()
            println("order-higher-price: $order2")
        }

    }
}

fun insertCustomer(customer: Customer) :Int{
     val c_id=  Customers.insert {
        it[name] = customer.name
        it[status] = customer.status
      //  it[pending_order] = customer.pendingOrder

    } get Customers.id
    println("Inserted customer: ${c_id}")
    return c_id
}
fun insertOrder(order: Order):Int {
    val o_id =    Orders.insert {
        it[date] = order.date
        it[price] = order.price
        it[customer_id] = order.customer
        it[completed] = order.orderCompleted

    } get Orders.id
    println("Inserted order: ${o_id}")
    return o_id
}
fun customerResultSet(r:ResultRow):Customer {
    return Customer(
            r[Customers.name],
            r[Customers.status]?:"",
            r[Customers.id]?:-1,
            r[Customers.pending_order]
    )
}
fun orderResultSet(r:ResultRow):Order {
    return Order(
            r[Orders.date],
        r[Orders.price]?:-1,
            r[Orders.customer_id],
            r[Orders.completed],
            r[Orders.id]?:-1
    )
}