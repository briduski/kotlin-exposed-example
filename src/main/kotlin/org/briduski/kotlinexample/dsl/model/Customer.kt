package org.briduski.kotlinexample.dsl.model

data class Customer(
        val name:String,
        val status:String,
        val id:Int?,
        val pendingOrder:Boolean?
)