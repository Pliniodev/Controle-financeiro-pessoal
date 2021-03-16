package com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository

import androidx.room.*
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel

@Dao
interface TransactionDAO {

    @Insert
    fun save(transaction: TransactionModel): Long

    @Update
    fun update(guest: TransactionModel): Int

    @Delete
    fun delete(guest: TransactionModel)

    @Query("SELECT * FROM Expense WHERE id = :id")
    fun get(id: Int): TransactionModel

    @Query("SELECT * FROM Expense")//Retorna todos os convidados
    fun getAll(): List<TransactionModel>

}