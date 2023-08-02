package com.yasma.expensetracker.data

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.anychart.anychart.DataEntry
import com.yasma.expensetracker.HomeFragment

import  kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Array
import java.text.SimpleDateFormat
import java.util.*

class ViewModelData(application: Application) : AndroidViewModel(application) {
    val readalldata: LiveData<List<yeardata>>


    private val repositary: yearRepositary


    init {
        val yearDao = yearDatabase.getDatabase(application).year()

        repositary = yearRepositary(yearDao)
        // sum = repositary.sumof_Expense(v)
       val  fragment=HomeFragment()

        readalldata = repositary.readAlldata
    }



    fun addData(yeardata: yeardata) {

        viewModelScope.launch(Dispatchers.IO) {
            repositary.adddata(yeardata)
        }


    }

    fun updateData(yeardata: yeardata) {

        viewModelScope.launch(Dispatchers.IO) {
            repositary.updatedata(yeardata)
        }
    }

    fun deletData(yeardata: yeardata) {
        viewModelScope.launch(Dispatchers.IO) {
            repositary.deletData(yeardata)
        }
    }

    fun readTodat(today: String): LiveData<List<yeardata>> {


        return repositary.todatData(today)

    }
    fun monthdata(today: String,value: String): LiveData<Int> {


        return repositary.monthData(today,value)

    }
    fun yeardata(today: String,value: String): LiveData<Int> {


        return repositary.yeardata(today,value)

    }
    fun statusvalue() = repositary.readAlldata

    fun sumod_expense(value: String) = repositary.sumof_Expense(value)
    fun sumod_income(value: String) = repositary.sumof_income(value)

    fun Month_expense_chart(months: String,value: String):  LiveData<List<twodata>> {
        return repositary.Month_expense_chart(months,value)
    }
    fun checkdta(value: String): LiveData<Int> {

        return repositary.checkdta(value)
    }
}