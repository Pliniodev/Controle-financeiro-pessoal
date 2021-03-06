package com.pliniodev.finanassimples_controlefinanceiropessoal.view.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pliniodev.finanassimples_controlefinanceiropessoal.R
import com.pliniodev.finanassimples_controlefinanceiropessoal.databinding.ActivityRegisterBinding
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.constants.TransactionConstants.Companion.TRANSACTIONID
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.utils.MoneyTextWatcher
import com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Activity onde são feitos os registros de despesas e receitas do usuário
     */

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mViewModel: RegisterViewModel
    private var mTransactionId: Int = 0
    private var mMes = 0
    private var mCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        loadData()
        setListeners()
        observe()
        setCategoryList()

        binding.switchPay.isChecked = false
        binding.radioExpense.isChecked = true//default value for transactionType
    }

    private fun setCategoryList() {
        val categories = listOf("Consumo doméstico", "Cartão de crédito", "Educação", "Lazer")
        val adapter = ArrayAdapter(this, R.layout.list_items, categories)
        (binding.editCategory).setAdapter(adapter)
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mTransactionId = bundle.getInt(TRANSACTIONID)
            mViewModel.load(mTransactionId)
        }
    }

    private fun observe() {
        mViewModel.saveTransaction.observe(this, Observer{
            if (it){
                Toast.makeText(applicationContext, "Sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha!", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.transactionToShow.observe(this, Observer {
            //os campos recebem a data do objeto selecionado
            binding.radioExpense.isChecked = it.transactionType
            binding.radioIncome.isChecked = !it.transactionType
            binding.editName.setText(it.name)
            binding.editDescription.setText(it.description)
            binding.editPrice.setText(it.price.toString())
            binding.editCategory.setText(it.category)
            binding.textEditDate.setText(it.dueDate)
            binding.switchPay.isChecked = it.paidOut
            binding.observationExtra.setText(it.observation)
        })
    }

    private fun setListeners() {
        binding.textEditDate.setOnClickListener(this)
        binding.switchPay.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
        binding.editPrice.addTextChangedListener(MoneyTextWatcher(binding.editPrice))
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val date = sdf.format(mCalendar.time)
        binding.textEditDate.setText(date.toString())
    }

    override fun onClick(view: View) {

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                mCalendar.set(Calendar.YEAR, year)
                mCalendar.set(Calendar.MONTH, monthOfYear)
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                getMonthTransaction()
            }

        val id = view.id

        if (id == R.id.text_edit_date) {
            DatePickerDialog(
                this, dateSetListener,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        } else if (id == R.id.switch_pay) {
            if (binding.switchPay.isChecked){
                binding.switchPay.text = getString(R.string.pago)
            } else{
                binding.switchPay.text = getString(R.string.pendente)
            }

        } else if (id == R.id.button_save) {
            val transactionType = binding.radioExpense.isChecked
            val name = binding.editName.text.toString()
            val description = binding.editDescription.text.toString()

            val price = binding.editPrice.text.toString()

            val category = binding.editCategory.text.toString()
            val dueDate = binding.textEditDate.text.toString()
            val month = mMes
            val paidOut = binding.switchPay.isChecked
            val observation = binding.observationExtra.text.toString()

            mViewModel.save(
                mTransactionId,
                transactionType,
                name,
                description,
                price,
                category,
                dueDate,
                month,
                paidOut,
                observation
            )
        }
    }

    private fun getMonthTransaction() {
        val myFormat = "MM"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val date = sdf.format(mCalendar.time)
        this.mMes = date.toInt()
    }
}
