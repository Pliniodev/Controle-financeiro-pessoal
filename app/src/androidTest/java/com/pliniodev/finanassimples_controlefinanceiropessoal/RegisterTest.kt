package com.pliniodev.finanassimples_controlefinanceiropessoal

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
class RegisterTest {

    private lateinit var transactionDAO: TransactionDAO
    private lateinit var db: RegisterDatabase

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
    fun testRegisterTransaction(){
        var t1 : TransactionModel = TransactionModel(
            transactionType = false,
            name = "Nome da receita de teste",
            price = "200.0",
            description = "Teste de descrição",
            category = "Conta simples",
            dueDate = "11/04/21",
            dueDateMonth = 4,
            paidOut = true,
            observation = "observação de teste 1"
        )
        var result = false
        var linesInTableToSave = transactionDAO.save(t1)
        if (linesInTableToSave > 0) {
            result = true
        }

        assertEquals(true,result)
        closeDb()
    }

}