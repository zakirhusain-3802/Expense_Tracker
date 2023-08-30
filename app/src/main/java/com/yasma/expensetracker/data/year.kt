package com.yasma.expensetracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Query
import java.lang.reflect.Array
import java.util.*


@Dao
interface year {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun adddata(yeardata: yeardata)
    @Update
    suspend fun updateuser(yeardata: yeardata)
    @Delete
    suspend fun deletdata(yeardata: yeardata)

    @Query("SElECT * FROM user_data ORDER BY id DESC")
    fun readDailyData(): LiveData<List<yeardata>>

    @Query("SElECT d_date,price FROM user_data WHERE d_date=:months AND type= :value ORDER BY id")
    fun Month_expense_chart(months:String,value: String):  LiveData<List<twodata>>

    @Query("SELECT * FROM user_data WHERE d_date=:today ORDER BY id DESC")
    fun todayData(today:String): LiveData<List<yeardata>>

@Query("SELECT * FROM user_data WHERE d_date=:today ORDER BY id DESC")
fun returndates(today:String): LiveData<List<yeardata>>


    @Query("SELECT SUM(price) FROM user_data WHERE d_date=:today AND type= :value ")
    fun monthdata(today:String,value: String): LiveData<Int>

    @Query("SELECT SUM(price) FROM user_data WHERE month=:today AND type= :value ")
    fun yeardata(today:String,value: String): LiveData<Int>

    @Query("SELECT SUM(price) FROM user_data WHERE type= :value ")
    fun  sumof_expense(value:String):LiveData<Int>

    @Query("SELECT SUM(price) From user_data WHERE month=:value")
    fun  checkdata(value:String):LiveData<Int>

    @Query("SELECT SUM(price) FROM user_data WHERE type= :value ")
    fun  sumof_income(value:String):LiveData<Int>

    @Query("SELECT SUM(price) FROM user_data WHERE month LIKE :value AND type=:tp")
    fun yearincmoe(value: String,tp:String):LiveData<Int>
    @Query("SELECT SUM(price) FROM user_data WHERE month LIKE :value AND type=:tp")
    fun yearexpense(value: String,tp:String):LiveData<Int>



}