package com.yasma.expensetracker.data

import androidx.lifecycle.LiveData
//import androidx.lifecycle.Transformations
import java.util.*

class yearRepositary(private val Year: year) {
    val readAlldata: LiveData<List<yeardata>> = Year.readDailyData()

    suspend fun adddata(yeardata: yeardata) {
        Year.adddata(yeardata)
    }

    suspend fun updatedata(yeardata: yeardata) {
        Year.updateuser(yeardata)
    }

    suspend fun deletData(yeardata: yeardata) {
        Year.deletdata(yeardata)
    }

    fun todatData(today: String): LiveData<List<yeardata>> {
        return Year.todayData(today)
    }

    fun Month_expense_chart(months: String,value: String):  LiveData<List<twodata>> {
        return Year.Month_expense_chart(months, value)
    }


    fun monthData(today: String, value: String): LiveData<Int> {
        return Year.monthdata(today, value)
    }
    fun yeardata(today: String, value: String): LiveData<Int> {
        return Year.yeardata(today, value)
    }

    fun sumof_Expense(value: String): LiveData<Int> {
        return Year.sumof_expense(value)
    }

    fun sumof_income(value: String): LiveData<Int> {
        return Year.sumof_income(value)
    }
    fun checkdta(value: String): LiveData<Int> {

        return Year.checkdata(value)
    }

}