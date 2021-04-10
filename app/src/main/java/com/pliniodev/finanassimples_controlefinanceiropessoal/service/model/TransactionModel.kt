package com.pliniodev.finanassimples_controlefinanceiropessoal.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Transactions")
data class TransactionModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "transactionType")
    var transactionType: Boolean = true,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "price")
    var price: Double,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "dueDate")
    var dueDate: String,

    @ColumnInfo(name = "dueDateMonth")
    var dueDateMonth: Int,

    @ColumnInfo(name = "paidOut")
    var paidOut: Boolean = false,

    @ColumnInfo(name = "observation")
    var observation: String = ""
)