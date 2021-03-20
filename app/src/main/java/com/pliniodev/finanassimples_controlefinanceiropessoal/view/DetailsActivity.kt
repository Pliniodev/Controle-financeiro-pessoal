package com.pliniodev.finanassimples_controlefinanceiropessoal.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pliniodev.finanassimples_controlefinanceiropessoal.R
import com.pliniodev.finanassimples_controlefinanceiropessoal.databinding.ActivityDetailsBinding
import com.pliniodev.finanassimples_controlefinanceiropessoal.databinding.ActivityRegisterBinding
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.constants.TransactionConstants
import com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel.DetailsViewModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel.RegisterViewModel

class DetailsActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var mViewModel: DetailsViewModel
    private var mTransactionId: Int = 0
    var mCurrency = "R$"

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

            var textPaidOut = ""
            if (it.paidOut) {
                textPaidOut = "Pago"
                binding.imgPaidOutDetail.setImageResource(R.drawable.ic_baseline_assignment_turned_in_24)
            } else {
                textPaidOut = "Pendente"
                binding.imgPaidOutDetail.setImageResource(R.drawable.ic_not_paid_24)
            }
            binding.textSituationDetail.text = textPaidOut
            binding.textObs.setText(it.observation)
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
            mViewModel.updatePaidOut(mTransactionId)
        }

    }


}