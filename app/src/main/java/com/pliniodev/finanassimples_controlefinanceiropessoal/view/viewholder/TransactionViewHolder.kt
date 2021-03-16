package com.pliniodev.finanassimples_controlefinanceiropessoal.view.viewholder

import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pliniodev.finanassimples_controlefinanceiropessoal.R
import com.pliniodev.finanassimples_controlefinanceiropessoal.databinding.WalletCardBinding
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.view.listener.TransactionListener

class TransactionViewHolder(itemView: View, val listener: TransactionListener): RecyclerView.ViewHolder(itemView) {

    private val textName : TextView = itemView.findViewById(R.id.text_name_transaction)
    private val textPrice : TextView = itemView.findViewById(R.id.text_price_transaction)
    private val cardView : CardView = itemView.findViewById(R.id.card_view)

    fun bind(transaction: TransactionModel) {

        textName.text = transaction.name
        textPrice.text = transaction.price.toString()

        isPaid(transaction.paidOut)

        itemView.setOnClickListener {
            listener.onCLick(transaction.id)
        }

        itemView.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remove_transaction)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) {dialog, which ->
                    listener.onDelete(transaction.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()

            true
        }
    }

    private fun isPaid(paidOut: Boolean) {
        if (paidOut) {
            cardView.setBackgroundColor(Color.parseColor("#DC746C"))
        } else {
            cardView.setBackgroundColor(Color.parseColor("#E49B83"))
        }
    }


}
