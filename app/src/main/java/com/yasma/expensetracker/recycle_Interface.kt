package com.yasma.expensetracker

import com.yasma.expensetracker.data.yeardata

interface recycle_Interface {
    fun onItemClick(currentItem: yeardata)
    fun deletitem(currentItem: yeardata)
    fun monthdat(date:String)
    fun nodata()
}