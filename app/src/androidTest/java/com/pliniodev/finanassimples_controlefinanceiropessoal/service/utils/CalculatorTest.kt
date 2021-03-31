package com.pliniodev.finanassimples_controlefinanceiropessoal.service.utils

import android.content.Context
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

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RegisterDatabase::class.java).build()
        transactionDAO = db.transactionDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testCalcula(){
        var expense = transactionDAO.getOneTypeTransaction(true)
        var income = transactionDAO.getOneTypeTransaction(false)

        var expectedResult = income-expense

        val calc = Calculator()
        var totalSum = calc.sumTotalTransactions(transactionDAO.getAll())
        assertEquals(expectedResult, totalSum)

        closeDb()
    }
}