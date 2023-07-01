package com.yasma.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.yasma.expensetracker.data.ViewModelData
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class MonthAdapter(
    private val Month: List<Month>,
    private var mUserViewModel: ViewModelData,
    private val printmont: String,
     var nodta: Boolean=false,
    private final var recycleInterface: recycle_Interface
) : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.month_re, parent, false)
         
        return MonthViewHolder(view, mUserViewModel, printmont,true, recycleInterface)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val Months = Month[position]
        holder.bind(Months)
    }

    override fun getItemCount(): Int = Month.size

    inner class MonthViewHolder(
        itemView: View,
        private var mUserViewModel: ViewModelData,
        private val printmont: String,
        var nodta: Boolean,
        private val recycleInterface: recycle_Interface
    ) : RecyclerView.ViewHolder(itemView) {

        private val dateTextView: TextView = itemView.findViewById(R.id.date_month)
        private val inctxy: TextView = itemView.findViewById(R.id.month_inc)
        private val exptxt: TextView = itemView.findViewById(R.id.month_exp)
        private val month_div: FrameLayout = itemView.findViewById(R.id.monthlayout)
        private val balance: TextView = itemView.findViewById(R.id.balace_month)
        private var i: Int = 0
        private var monthindex: Int = 0
        private var e: Int = 0
        private var t:Int=0

        fun bind(Month: Month) {

            monthindex = getMonthIndex(Month)


//            i = monthindex
            val cal = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd")
            val dateFormat1 = SimpleDateFormat("MMM")
//            val curr_date = dateFormat.format(date)


            cal[Calendar.MONTH] = monthindex - 1
            val curr_date1 = dateFormat1.format(cal.time)
            val month_data = monthindex.toString() + printmont
//            println(month_data)

            month_div.setVisibility(View.GONE)


            mUserViewModel.yeardata(month_data, "Income")
                .observe(itemView.context as LifecycleOwner, androidx.lifecycle.Observer{ sum -> notprint(sum.toString()) })
            mUserViewModel.yeardata(month_data, "Expense")
                .observe(itemView.context as LifecycleOwner,
                    androidx.lifecycle.Observer { sum -> notprintexp(sum.toString()) })
            dateTextView.text = curr_date1.toString()


            month_div.setOnClickListener() {
                if (recycleInterface != null) {

                    recycleInterface.monthdat(month_data)
                }


            }



        }

        private fun notprint(toString: String) {

            if (toString != "null") {
                month_div.setVisibility(View.VISIBLE)
                inctxy.text = toString.toString() + ".00"
                i = Integer.parseInt(toString.toString())



            } else {
                inctxy.text = "0" + ".00"
                i=0

            }
            display(recycleInterface)

        }

        private fun notprintexp(toString: String) {

            if (toString != "null") {
                month_div.setVisibility(View.VISIBLE)
                exptxt.text = toString.toString() + ".00"
                e = Integer.parseInt(toString.toString())


            } else {
                exptxt.text = "0" + ".00"
                e=0


            }

            display(recycleInterface)
        }

        private fun display(recycleInterface: recycle_Interface) {
            if (inctxy.text == "0.00" && exptxt.text == "0.00") {
                month_div.setVisibility(View.GONE)
                recycleInterface.nodata()


            } else {

                    balance.text = ((i - e).toString() + ".00")


                    balance.text = ((i - e).toString() + ".00")
//                recycleInterface.nodata()

//
            }
            if(nodta!==false){

//                if(recycleInterface!==null){
//                    recycleInterface.nodata()
//                }
            }
        }

    }

    fun getMonthIndex(monthname: Month): Int {
        val months = listOf(
            "JANUARY",
            "FEBRUARY",
            "MARCH",
            "APRIL",
            "MAY",
            "JUNE",
            "JULY",
            "AUGUST",
            "SEPTEMBER",
            "OCTOBER",
            "NOVEMBER",
            "DECEMBER"
        )

        return months.indexOf(monthname.toString()) + 1
    }
}
//if(sum!=null) {inctxy.text = sum.toString()}else{inctxy.text="0"  }