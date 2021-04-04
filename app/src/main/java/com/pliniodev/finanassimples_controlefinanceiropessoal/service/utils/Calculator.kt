package com.pliniodev.finanassimples_controlefinanceiropessoal.service.utils

import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel

class Calculator {

    fun sumTotalTransactionsInMonth(transactionList: List<TransactionModel>): Double {
        var result = 0.0
        for (transaction in transactionList) {
            if (!transaction.transactionType){//se for despesa
                result += transaction.price
            } else {
                result -= transaction.price
            }
        }
        return result
    }

    fun sumIncomeTransactions(transactionList: List<TransactionModel>): Double {
        var result = 0.0
        for (transaction in transactionList) {
            if (!transaction.transactionType){//se for receita
                result += transaction.price
            }
        }
        return result
    }

    fun sumExpenseTransactions(transactionList: List<TransactionModel>): Double {
        var result = 0.0
        for (transaction in transactionList) {
            if (transaction.transactionType){//se for despesa
                result += transaction.price
            }
        }
        return result
    }

}