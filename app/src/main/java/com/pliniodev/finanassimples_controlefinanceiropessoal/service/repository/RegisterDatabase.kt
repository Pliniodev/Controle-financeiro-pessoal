package com.pliniodev.finanassimples_controlefinanceiropessoal.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.constants.TransactionConstants.Companion.REGISTERDB
import com.pliniodev.finanassimples_controlefinanceiropessoal.service.model.TransactionModel

@Database(entities = [TransactionModel::class], version = 1)
abstract class RegisterDatabase : RoomDatabase() {

    abstract fun transactionDAO(): TransactionDAO

    companion object {
        private lateinit var INSTANCE: RegisterDatabase

        fun getDatabase(context: Context): RegisterDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(RegisterDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, RegisterDatabase::class.java, REGISTERDB)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}