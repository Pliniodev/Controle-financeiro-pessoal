package com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository

import androidx.room.*
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel

@Dao
interface TransactionDAO {

    @Insert
    fun save(transaction: TransactionModel): Long

    @Update
    fun update(transaction: TransactionModel): Int

    @Query("UPDATE Transactions SET paidOut = :paidOut WHERE id = :id")
    fun updatePaidOut(paidOut: Boolean, id: Int): Int

    @Delete
    fun delete(guest: TransactionModel)

    @Query("SELECT * FROM Transactions WHERE id = :id")
    fun get(id: Int): TransactionModel

    @Query("SELECT * FROM Transactions")//Retorna todos os convidados
    fun getAll(): List<TransactionModel>

    @Query("SELECT * FROM Transactions WHERE transactionType = :transactionType")
    fun getOneTypeTransaction(transactionType: Boolean): List<TransactionModel>

    @Query("SELECT * FROM Transactions WHERE dueDateMonth = :month")
    fun getMonth(month: Int): List<TransactionModel>

}