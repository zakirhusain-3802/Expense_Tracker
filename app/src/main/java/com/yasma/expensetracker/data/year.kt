package com.yasma.expensetracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Query


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

    @Query("SELECT * FROM user_data WHERE d_date=:today ORDER BY id DESC")
    fun todayData(today:String): LiveData<List<yeardata>>
//    @Query( "SELECT * FROM user_data WHERE LIKE '%"+":today'"+" ORDER BY id DESC")
//    fun todayData(today:String): LiveData<List<yeardata>>
@Query("SELECT * FROM user_data WHERE d_date=:today ORDER BY id DESC")
fun returndates(today:String): LiveData<List<yeardata>>


    @Query("SELECT SUM(price) FROM user_data WHERE d_date=:today AND type= :value ")
    fun monthdata(today:String,value: String): LiveData<Int>

    @Query("SELECT SUM(price) FROM user_data WHERE month=:today AND type= :value ")
    fun yeardata(today:String,value: String): LiveData<Int>

    @Query("SELECT SUM(price) FROM user_data WHERE type= :value ")
    fun  sumof_expense(value:String):LiveData<Int>



    @Query("SELECT SUM(price) FROM user_data WHERE type= :value ")
    fun  sumof_income(value:String):LiveData<Int>

}