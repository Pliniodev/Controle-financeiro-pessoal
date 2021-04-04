package com.pliniodev.finanassimples_controlefinanceiropessoal.service.utils

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository.RegisterDatabase
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository.TransactionDAO
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class CalculatorTest {

    private lateinit var transactionDAO: TransactionDAO
    private lateinit var db: RegisterDatabase
    private var monthTest = 4
    private val calc = Calculator()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
//        db = Room.inMemoryDatabaseBuilder(
            context, RegisterDatabase::class.java, "registerDB").build()
        transactionDAO = db.transactionDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testCalcTotalTransactionInMonth(){

        val expenses = transactionDAO.getOneTypeTransaction(true, monthTest)
        val incomes = transactionDAO.getOneTypeTransaction(false,monthTest)

        var totalExpenseInMonth = 0.0
        for(transaction: TransactionModel in expenses) {
            totalExpenseInMonth += transaction.price
        }

        var totalIncomesInMonth = 0.0
        for (transaction: TransactionModel in incomes) {
            totalIncomesInMonth += transaction.price
        }

        val expectedResult = totalIncomesInMonth-totalExpenseInMonth

        val totalSum = calc.sumTotalTransactionsInMonth(transactionDAO.getMonth(monthTest))

        assertEquals(expectedResult, totalSum)

        closeDb()
    }

    @Test
    @Throws(Exception::class)
    fun testCalcTotalIncomesInMonth() {
        var expectedResult = 0.0

        val incomes = transactionDAO.getOneTypeTransaction(false, monthTest)
        for (transaction: TransactionModel in incomes) {
            expectedResult += transaction.price
        }

        val totalIncome = calc.sumIncomeTransactions(transactionDAO.getMonth(monthTest))

        assertEquals(expectedResult, totalIncome)
    }
}