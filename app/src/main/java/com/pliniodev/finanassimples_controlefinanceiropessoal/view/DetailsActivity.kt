package com.pliniodev.finanassimples_controlefinanceiropessoal.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pliniodev.finanassimples_controlefinanceiropessoal.R
import com.pliniodev.finanassimples_controlefinanceiropessoal.databinding.ActivityDetailsBinding
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.constants.TransactionConstants
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.constants.TransactionConstants.Companion.TRANSACTIONID
import com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel.DetailsViewModel

class DetailsActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var mViewModel: DetailsViewModel
    private var mTransactionId: Int = 0
    var mCurrency = "R$"
    private var mPaidOut: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        setListeners()
        observe()
        loadData()
    }


    private fun setListeners() {
        binding.buttonDelete.setOnClickListener(this)
        binding.buttonEdit.setOnClickListener(this)
        binding.buttonEditPaidOut.setOnClickListener(this)
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mTransactionId = bundle.getInt(TransactionConstants.TRANSACTIONID)
            mViewModel.load(mTransactionId)
        }
    }
    private fun observe() {
        mViewModel.transactionToShow.observe(this, Observer {
            if (it.transactionType) {
                binding.textTypeTransactionDetail.setBackgroundResource(R.drawable.shape_type_expense)
                binding.textTypeTransactionDetail.text = getString(R.string.expense)
            } else{
                binding.textTypeTransactionDetail.setBackgroundResource(R.drawable.shape_type_income)
                binding.textTypeTransactionDetail.text = getString(R.string.income)
            }

            binding.textNameDetail.text = it.name
            binding.textPriceDetail.text = getString(R.string.price_detail,mCurrency,it.price.toString())
            binding.textDescDetail.setText(it.description)
            binding.textCategoryDetail.text = getString(R.string.category_detail,it.category)
            binding.textDueDateDetail.text = getString(R.string.due_date_detail,it.dueDate)

            val paid = getString(R.string.pago)
            val notPaid = getString(R.string.pendente)
            if (it.paidOut) {
                binding.imgPaidOutDetail.setImageResource(R.drawable.ic_baseline_assignment_turned_in_24)
                binding.buttonEditPaidOut.text = getString(R.string.update_paid_out_detail, notPaid)
                binding.textSituationDetail.text = paid
                mPaidOut = true
            } else {
                binding.imgPaidOutDetail.setImageResource(R.drawable.ic_not_paid_24)
                binding.buttonEditPaidOut.text = getString(R.string.update_paid_out_detail, paid)
                binding.textSituationDetail.text = notPaid
                mPaidOut = false
            }

            binding.textObs.setText(it.observation)
        })

        mViewModel.updatePaidOut.observe(this, Observer {
            if (it){
                mViewModel.load(mTransactionId)
                Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(view: View) {
        val id = view.id

        if (id == R.id.button_delete) {
            AlertDialog.Builder(this)
                .setTitle(R.string.remove_transaction)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) {dialog, which ->
                    mViewModel.delete(mTransactionId)
                    Toast.makeText(this, "Transação removida com sucesso",Toast.LENGTH_LONG).show()
                    finish()
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()
        } else if (id == R.id.button_edit) {

            val intent = Intent(this, RegisterActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(TRANSACTIONID, mTransactionId)

            intent.putExtras(bundle)
            startActivity(intent)
            finish()

        } else if (id == R.id.button_edit_paid_out) {
            var paidOutInverted = !mPaidOut
            mViewModel.updatePaidOut(paidOutInverted, mTransactionId)
        }

    }


}