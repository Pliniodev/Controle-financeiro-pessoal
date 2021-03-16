package com.pliniodev.finanassimples_controlefinanceiropessoal.view.adapter

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pliniodev.finanassimples_controlefinanceiropessoal.R
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.view.listener.TransactionListener
import com.pliniodev.finanassimples_controlefinanceiropessoal.view.viewholder.TransactionViewHolder

class TransactionAdapter: RecyclerView.Adapter<TransactionViewHolder>(){

    private var mTransactionList: List<TransactionModel> = arrayListOf()
    private lateinit var mListener: TransactionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.wallet_card, parent,false)
        return TransactionViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(mTransactionList[position])
    }

    override fun getItemCount(): Int {
        return mTransactionList.count()
    }

    fun updateTransactions(list: List<TransactionModel>?) {
        if (list != null) {
            mTransactionList = list
        }
        notifyDataSetChanged()
    }

    fun attachListener(listener: TransactionListener) {
        mListener = listener
    }




}