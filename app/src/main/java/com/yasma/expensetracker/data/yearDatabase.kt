package com.yasma.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [yeardata::class], version = 1, exportSchema = false)
abstract class yearDatabase :RoomDatabase(){

    abstract fun year(): year
    companion object{
        @Volatile
        private var INSTANCE:yearDatabase?=null
        fun getDatabase(context: Context):yearDatabase{
            val tempInstace= INSTANCE
            if(tempInstace!=null){
                return tempInstace
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    yearDatabase::class.java,
                    "year_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }


}
