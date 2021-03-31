package com.pliniodev.finanassimples_controlefinanceiropessoal.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
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
    private val mRecyclerView: RecyclerView? = null

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
        val mRecyclerView = binding.root.findViewById<RecyclerView>(R.id.wallet_recycler)

        //2º Definir um layout
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        //3º - Definir um adapter
        mRecyclerView.adapter = mAdapter


        //implementação do listener
        setListeners()
        defineCurrency()
        mAdapter.attachListener(mListener)

        observer()
        initUi()
        configuraSwipe()

        return binding.root
    }

    private fun initUi() {
        setTextDate(mDateTime.plusMonths(countMonth))//inicializa o texto da data
    }

    private fun setListeners() {
        mListener = object : TransactionListener {
            override fun onCLick(id: Int) {
//                val intent = Intent(context, RegisterActivity::class.java)
                val intent = Intent(context, DetailsActivity::class.java)

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
        binding.buttonSomeDetails.setOnClickListener(this)
        binding.fab.setOnClickListener(this)
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
        mViewModel.monthExpense.observe(viewLifecycleOwner, Observer {
            binding.totalMonthExpense.text = mCurrency + it.toString()
        })
        mViewModel.monthIncome.observe(viewLifecycleOwner, Observer {
            binding.totalMonthIncome.text = mCurrency + it.toString()
        })
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.button_next_month) {

            countMonth++

        } else if (id == R.id.button_last_month) {

            countMonth--

        } else if (id == R.id.button_some_details) {
            if (binding.textMonthTransactions.visibility == View.GONE) {
                binding.textMonthTransactions.visibility = View.VISIBLE
                binding.viewSomeDetailsExtra.visibility = View.VISIBLE
                binding.buttonSomeDetails.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.ic_close_some_details_24
                    )
                )
            } else {
                binding.textMonthTransactions.visibility = View.GONE
                binding.viewSomeDetailsExtra.visibility = View.GONE
                binding.buttonSomeDetails.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.ic_expand__some_details_24
                    )
                )
            }
        } else if (id == R.id.fab) {
            startActivity(Intent(activity, RegisterActivity::class.java))
        }

        setTextDate(mDateTime.plusMonths(countMonth))
        mViewModel.load(mDateTime.plusMonths(countMonth).monthOfYear)
    }

    fun setTextDate(month: DateTime) {
        val fmt: DateTimeFormatter = DateTimeFormat.forPattern("MMM, yyyy")
        val portugueseFmt = fmt.withLocale(Locale("pt", "BR"))
        binding.textCurrentDate.text = month.toString(portugueseFmt)
    }

    private fun configuraSwipe() {

    }
}