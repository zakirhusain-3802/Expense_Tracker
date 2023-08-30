package com.yasma.expensetracker.data

import android.icu.text.CaseMap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date
import java.time.Month
import java.util.*

@Entity(tableName="user_data")
data class yeardata(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val d_date:String,
    val type:String,
    val title: String,
    val description:String,
    val month:String,
    val price: Int

)




data class twodata (
    val d_date: String,
    val price:Int
        )
