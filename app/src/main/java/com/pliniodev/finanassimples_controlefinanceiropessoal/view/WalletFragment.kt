package com.pliniodev.finanassimples_controlefinanceiropessoal.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pliniodev.finanassimples_controlefinanceiropessoal.R
import com.pliniodev.finanassimples_controlefinanceiropessoal.databinding.FragmentWalletBinding
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.constants.TransactionConstants.Companion.TRANSACTIONID
import com.pliniodev.finanassimples_controlefinanceiropessoal.view.adapter.TransactionAdapter
import com.pliniodev.finanassimples_controlefinanceiropessoal.view.listener.TransactionListener
import com.pliniodev.finanassimples_controlefinanceiropessoal.viewmodel.WalletViewModel
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*


class WalletFragment : Fragment(), View.OnClickListener {
    /**
     * Fragment onde estão listadas todas as contas
     */
    //binding fragment
    private var root: FragmentWalletBinding? = null
    private val binding get() = root!!

    private lateinit var mViewModel: WalletViewModel
    private val mAdapter: TransactionAdapter = TransactionAdapter()
    private lateinit var mListener: TransactionListener
    private var mCurrency = ""
    private var countMonth = 0
    private val mDateTime = DateTime()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        //obs root que armazena a criação do layout
        root = FragmentWalletBinding.inflate(inflater, container, false)
        //RecyclerView
        //1º Obter a recyclerView
        val recycler = binding.root.findViewById<RecyclerView>(R.id.wallet_recycler)

        //2º Definir um layout
        recycler.layoutManager = LinearLayoutManager(context)

        //3º - Definir um adapter
        recycler.adapter = mAdapter


        //implementação do listener
        setListeners()
        defineCurrency()
        mAdapter.attachListener(mListener)

        observer()


        return binding.root
    }

    private fun setListeners() {
        mListener = object : TransactionListener {
            override fun onCLick(id: Int) {
                val intent = Intent(context, RegisterActivity::class.java)

                //utilizando o Bundle() é possível a passagem de parâmetros entre activities
                val bundle = Bundle()
                bundle.putInt(TRANSACTIONID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(mDateTime.monthOfYear)
            }
        }

        binding.buttonLastMonth.setOnClickListener(this)
        binding.buttonNextMonth.setOnClickListener(this)

    }



    private fun defineCurrency() {
        mCurrency = "R$"
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(mDateTime.monthOfYear)
    }

    override fun onDestroy() {
        super.onDestroy()
        root = null
    }

    private fun observer() {
        mViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateTransactions(it)
        })
        mViewModel.totalWallet.observe(viewLifecycleOwner, Observer {
            binding.textTotalWallet.text = mCurrency + it.toString()
        })

    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.button_next_month) {

            countMonth++
            mDateTime.plusMonths(countMonth).monthOfYear()

        } else if (id == R.id.button_last_month) {

            countMonth--
            mDateTime.plusMonths(countMonth).monthOfYear()

        }
        val selectedMonth = mDateTime.plusMonths(countMonth)
        val fmt: DateTimeFormatter = DateTimeFormat.forPattern("MMM, yyyy")
        val portugueseFmt = fmt.withLocale(Locale("pt","BR"))

        binding.textCurrentDate.text = selectedMonth.toString(portugueseFmt)

        mViewModel.load(selectedMonth.monthOfYear)
    }


}