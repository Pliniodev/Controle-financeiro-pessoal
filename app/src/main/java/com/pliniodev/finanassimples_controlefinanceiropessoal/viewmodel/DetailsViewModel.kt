package com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository.TransactionRepository

class DetailsViewModel(application: Application): AndroidViewModel(application) {
    private val mContext = application.applicationContext
    private val mTransactionRepository: TransactionRepository = TransactionRepository(mContext)

    private var mTransaction = MutableLiveData<TransactionModel>()
    val transactionToShow: LiveData<TransactionModel> = mTransaction

    fun load(mTransactionId: Int) {
        mTransaction.value = mTransactionRepository.get(mTransactionId)
    }

    fun delete(mTransactionId: Int) {
        val transaction: TransactionModel = mTransactionRepository.get(mTransactionId)
        mTransactionRepository.delete(transaction)
    }

    fun updatePaidOut(mTransactionId: Int) {
        TODO("Not yet implemented")
    }


}