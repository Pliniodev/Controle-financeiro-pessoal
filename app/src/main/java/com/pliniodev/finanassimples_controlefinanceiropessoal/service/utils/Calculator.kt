package com.pliniodev.finanassimples_controlefinanceiropessoal.service.utils

import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Calculator  {
    private var mResult : BigDecimal = BigDecimal.ZERO

    fun sumTotalTransactionsInMonth(transactionList: List<TransactionModel>): String {
        mResult = BigDecimal.ZERO
        for (transaction in transactionList) {

            if (!transaction.transactionType){//se for despesa
                mResult += transaction.priceInBigDecimal

            } else {

                mResult -= transaction.priceInBigDecimal
            }
        }

        return changeResultToString(mResult)
    }

    fun sumIncomeTransactions(transactionList: List<TransactionModel>): String {
        mResult = BigDecimal.ZERO

        for (transaction in transactionList) {

            if (!transaction.transactionType){//se for receita
                mResult += transaction.priceInBigDecimal
            }
        }

        return changeResultToString(mResult)
    }

    fun sumExpenseTransactions(transactionList: List<TransactionModel>): String {
        mResult = BigDecimal.ZERO

        for (transaction in transactionList) {
            if (transaction.transactionType){//se for despesa
                mResult += transaction.priceInBigDecimal

            }
        }
        return changeResultToString(mResult)
    }

    private fun changeResultToString(mResult: BigDecimal): String {
        val s = mResult.toString()

        val parsed: BigDecimal = BigDecimal(s).setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)

        val locale = Locale("pt", "BR")

        return NumberFormat.getCurrencyInstance(locale).format(parsed)
    }
}