package com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository.TransactionRepository
import org.joda.time.DateTime

class WalletViewModel(application: Application) : AndroidViewModel(application) {

    private val mTransactionRepository = TransactionRepository(application.applicationContext)

    private val mTransactionList = MutableLiveData<List<TransactionModel>>()
    val transactionList: LiveData<List<TransactionModel>> = mTransactionList

    private val mTotalWallet = MutableLiveData<Double>()
    val totalWallet = mTotalWallet

    /**
     * Pega a lista de transações que foram realizadas neste mês e
     * faz a conta do valor total que sobrou na carteira
     * */
    fun load() {
//        mTransactionList.value = mTransactionRepository.getAll()//pega todas as transações
//        totalWallet.value = sumTransactions(mTransactionRepository.getAll())//soma todas as transações

        mTransactionList.value = mTransactionRepository.getTransactionCurrentMonth(getCurrentMonth())//
        totalWallet.value = sumTransactions(mTransactionRepository.getTransactionCurrentMonth(getCurrentMonth()))
    }

    private fun getCurrentMonth(): Int{
        val now = DateTime()
        return now.monthOfYear
    }

    private fun sumTransactions(transactionList: List<TransactionModel>): Double {
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

    fun delete(id: Int) {
        val guest = mTransactionRepository.get(id)
        mTransactionRepository.delete(guest)
    }



}