package com.pliniodev.finanassimples_controlefinanceiropessoal.view.listener

import java.util.*

//Listener para itemview
interface TransactionListener {
    fun onCLick(id: Int)
    fun onDelete(id: Int)
}