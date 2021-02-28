package org.briduski.kotlinexample.dsl.model

import java.time.LocalDateTime

data class Order (
        val date:LocalDateTime,
        val price: Int,
        val customer:Int,
        val orderCompleted:Boolean,
        val id:Int?
)