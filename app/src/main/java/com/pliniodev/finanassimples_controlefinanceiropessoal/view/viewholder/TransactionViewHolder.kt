package com.pliniodev.finanassimples_controlefinanceiropessoal.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pliniodev.finanassimples_controlefinanceiropessoal.R

import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.view.listener.TransactionListener

class TransactionViewHolder(itemView: View, val listener: TransactionListener): RecyclerView.ViewHolder(itemView) {

    private val textName : TextView = itemView.findViewById(R.id.text_name_transaction)
    private val textPrice : TextView = itemView.findViewById(R.id.text_price_transaction)
    private val textDueDate: TextView = itemView.findViewById(R.id.text_due_date)
    private val textTransactionType: TextView = itemView.findViewById(R.id.text_transaction_type)

    private val transactionSituation: TextView = itemView.findViewById(R.id.transaction_situation)
    private val cardView : CardView = itemView.findViewById(R.id.card_view)
    private val viewColorAlert: View = itemView.findViewById(R.id.view_color_alert)
    private val imgSituation: ImageView = itemView.findViewById(R.id.img_transaction_situation)



    fun bind(transaction: TransactionModel) {

        textName.text = transaction.name
        textPrice.text = transaction.price.toString()
        textDueDate.text = transaction.dueDate

        isPaid(transaction.paidOut)
        isExpense(transaction.transactionType)

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

    private fun isExpense(transactionType: Boolean) {
        if (transactionType) {
            textTransactionType.setBackgroundResource(R.drawable.shape_type_expense)
            textTransactionType.text = "Despesa"
        } else {
            textTransactionType.setBackgroundResource(R.drawable.shape_type_income)
            textTransactionType.text = "Receita"
        }
    }

    private fun isPaid(paidOut: Boolean) {
        if (paidOut) {//se estiver pago
            cardView.setBackgroundResource(R.drawable.shape_paid)
            viewColorAlert.setBackgroundResource(R.color.green)
            transactionSituation.setText(R.string.pago)
            imgSituation.setImageResource(R.drawable.ic_baseline_assignment_turned_in_24)
        } else {
            cardView.setBackgroundResource(R.drawable.shape_not_paid)
            viewColorAlert.setBackgroundResource(R.color.red)
            transactionSituation.setText(R.string.pendente)
            imgSituation.setImageResource(R.drawable.ic_not_paid_24)
        }
    }


}
