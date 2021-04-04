package com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository

import android.content.Context
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel

class TransactionRepository (context: Context){

    //Acesso ao BD
    private val mDatabase = RegisterDatabase.getDatabase(context).transactionDAO()

    //busca registro específico
    fun get(id: Int): TransactionModel {
        return mDatabase.get(id)
    }

    // Salva registro de despesa
    fun save(transaction: TransactionModel): Boolean {
        return mDatabase.save(transaction) > 0
    }

    //listagem de todas as transações
    fun getAll(): List<TransactionModel> {
        return mDatabase.getAll()
    }

    //Atualiza a transação
    fun update(transaction: TransactionModel): Boolean {
        return mDatabase.update(transaction) > 0 // (>0) refere-se a : se atualizar mais q 0 linhas, faça
    }

    //Atualiza a o estado (pago ou não)
    fun updatePaidOut(paidOut: Boolean, id: Int) : Boolean{
        return mDatabase.updatePaidOut(paidOut, id) > 0
    }

    //deleta a transação
    fun delete(transaction: TransactionModel){
        mDatabase.delete(transaction)
    }

    //pega as transações do mês atual
    fun getTransactionCurrentMonth(month: Int): List<TransactionModel> {
        return mDatabase.getMonth(month)
    }
}