package com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository.TransactionRepository
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.utils.Calculator
import java.math.BigDecimal

class WalletViewModel(application: Application) : AndroidViewModel(application) {

    private val mTransactionRepository = TransactionRepository(application.applicationContext)
    private val mCalc = Calculator()

    private val mTransactionList = MutableLiveData<List<TransactionModel>>()
    val transactionList: LiveData<List<TransactionModel>> = mTransactionList

    private val mTotalWallet = MutableLiveData<String>()
    val totalWallet = mTotalWallet

    private val mMonthIncome = MutableLiveData<String>()
    val monthIncome = mMonthIncome

    private val mMonthExpense = MutableLiveData<String>()
    val monthExpense = mMonthExpense

    /**
     * Pega a lista de transações que foram realizadas neste mês e
     *  faz a conta do valor total que sobrou na carteira,
     *  Soma as transações do tipo despesa
     *  Soma as transações do tipo receita
     *  Carrega os resultados
     * */
    fun load(currentMonth: Int) {
        mTransactionList.value = mTransactionRepository.getTransactionCurrentMonth(currentMonth)//
        totalWallet.value = mCalc.sumTotalTransactionsInMonth(mTransactionRepository.getTransactionCurrentMonth(currentMonth))
        monthIncome.value = mCalc.sumIncomeTransactions(mTransactionRepository.getTransactionCurrentMonth(currentMonth))
        monthExpense.value = mCalc.sumExpenseTransactions(mTransactionRepository.getTransactionCurrentMonth(currentMonth))
    }

    fun delete(id: Int) {
        val guest = mTransactionRepository.get(id)
        mTransactionRepository.delete(guest)
    }



}