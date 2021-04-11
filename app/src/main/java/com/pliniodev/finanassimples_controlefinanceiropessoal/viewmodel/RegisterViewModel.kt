package com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository.TransactionRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

   private val mContext = application.applicationContext
   private val mTransactionRepository: TransactionRepository = TransactionRepository(mContext)

   private var mSaveTransaction = MutableLiveData<Boolean>()
   val saveTransaction: LiveData<Boolean> = mSaveTransaction

   private var mTransaction = MutableLiveData<TransactionModel>()
   val transactionToShow: LiveData<TransactionModel> = mTransaction

    fun load(mTransactionId: Int) {//busca o registro através do id recebido pelo bundle na register activity
         mTransaction.value = mTransactionRepository.get(mTransactionId)
    }

   fun save(
      id: Int,
      transactionType: Boolean,
      name: String,
      description: String,
      price: String,
      category: String,
      dueDate: String,
      month: Int,
      paidOut: Boolean,
      observation: String
   ) {
      val transactionToSave = TransactionModel(
         id = id,
         transactionType = transactionType,
         name = name,
         description = description,
         price = price,
         category = category,
         dueDate = dueDate,
         dueDateMonth = month,
         paidOut = paidOut,
         observation = observation,
      )
//         .apply {
//         this.id = id
//         this.transactionType = transactionType
//         this.name = name
//         this.description = description
//         this.price = price
//         this.category = category
//         this.dueDate = dueDate
//         this.dueDateMonth = month
//         this.paidOut = paidOut
//         this.observation = observation
//      }
      if (id == 0) {
         mSaveTransaction.value = mTransactionRepository.save(transactionToSave)
      } else {
         mSaveTransaction.value = mTransactionRepository.update(transactionToSave)
      }
   }

}