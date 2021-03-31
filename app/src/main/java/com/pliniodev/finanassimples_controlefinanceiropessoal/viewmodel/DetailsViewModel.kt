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

    private var mUpdatePaidOut = MutableLiveData<Boolean>()
    val updatePaidOut: LiveData<Boolean> = mUpdatePaidOut

    fun load(mTransactionId: Int) {
        mTransaction.value = mTransactionRepository.get(mTransactionId)
    }

    fun delete(id: Int) {
        val transaction: TransactionModel = mTransactionRepository.get(id)
        mTransactionRepository.delete(transaction)
    }

    fun updatePaidOut(mPaidOut: Boolean, id: Int) {
        mUpdatePaidOut.value = mTransactionRepository.updatePaidOut(mPaidOut,id)
    }


}