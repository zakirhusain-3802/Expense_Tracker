package com.yasma.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yasma.expensetracker.data.ViewModelData
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*

class MonthAdapter1(
    private val dates: List<Date>,
    private var mUserViewModel: ViewModelData,
    private val printmont: String,
    private val today: Int,
    private var recycleInterface: recycle_Interface
) : RecyclerView.Adapter<MonthAdapter1.MonthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_data, parent, false)

        return MonthViewHolder(view, mUserViewModel, printmont, today, recycleInterface)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date)
    }

    override fun getItemCount(): Int = dates.size

    inner class MonthViewHolder(
        itemView: View,
        private var mUserViewModel: ViewModelData,
        private val printmont: String,
        private val today: Int,
        private val recycleInterface: recycle_Interface
    ) : RecyclerView.ViewHolder(itemView) {

        private val dateTextView: TextView = itemView.findViewById(R.id.dailydate)
        private val inctxy: TextView = itemView.findViewById(R.id.dailyincom)
        private val exptxt: TextView = itemView.findViewById(R.id.dailyexpense)
        private val monthdiv: LinearLayout = itemView.findViewById(R.id.dailydiv)
        private val childecycle: RecyclerView = itemView.findViewById(R.id.chilrecyle)
        private val datenum: TextView = itemView.findViewById(R.id.date_num)
        val adapter = ListAdapter1(recycleInterface)
        val recycleView = childecycle

        //        private val balance:TextView=itemView.findViewById(R.id.balace_month)
        private var i: Int = 0
        private var e: Int = 0
        private var show: Boolean = false
        private var alway: Boolean = false

        fun bind(date: Date) {

            monthdiv.visibility = View.GONE
            val dateFormat = SimpleDateFormat("d")
            val dateFormat1 = SimpleDateFormat("E")
            val curr_date = dateFormat.format(date)
            val curr_date1 = dateFormat1.format(date)
            val month_data = curr_date.toString() + "/" + printmont
           println(month_data)


            recycleView.layoutManager = LinearLayoutManager(itemView.context)
//            recycleView.visibility=View.GONE


            mUserViewModel.readTodat(month_data.toString())
                .observe(itemView.context as LifecycleOwner, { year -> adapter.setData(year) })

            recycleView.adapter = adapter

            mUserViewModel.monthdata(month_data, "Income")
                .observe(itemView.context as LifecycleOwner,
                    androidx.lifecycle.Observer { sum -> notprint(sum.toString()) })
            mUserViewModel.monthdata(month_data, "Expense")
                .observe(itemView.context as LifecycleOwner,
                    androidx.lifecycle.Observer { sum -> notprintexp(sum.toString()) })
            dateTextView.text = curr_date1.toString()
            datenum.text = curr_date.toString()



        }

        //
        private fun notprint(toString: String) {

            if (toString != "null") {
                monthdiv.visibility = View.VISIBLE
                inctxy.text = toString.toString() + ".00"
                i = Integer.parseInt(toString.toString())


            } else {
                inctxy.text = "0" + ".00"
                display()

            }


        }

        private fun notprintexp(toString: String) {

            if (toString != "null") {
                monthdiv.visibility = View.VISIBLE
                exptxt.text = toString.toString() + ".00"
                e = Integer.parseInt(toString.toString())
            } else {
                exptxt.text = "0" + ".00"
                display()

            }


        }

        private fun display() {
            if (inctxy.text == "0.00" && exptxt.text == "0.00") {
                monthdiv.visibility = View.GONE

            } else {
//
            }
        }
    }

}
//if(sum!=null) {inctxy.text = sum.toString()}else{inctxy.text="0"  }