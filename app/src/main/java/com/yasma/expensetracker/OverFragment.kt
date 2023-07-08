package com.yasma.expensetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.yasma.expensetracker.data.ViewModelData
import com.yasma.expensetracker.data.twodata
import com.yasma.expensetracker.data.year
import com.yasma.expensetracker.data.yeardata
import kotlinx.android.synthetic.main.fragment_month.view.*
import kotlinx.android.synthetic.main.fragment_over.view.*
import java.util.*

import java.text.SimpleDateFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [OverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverFragment : Fragment(),recycle_Interface {
    // TODO: Rename and change types of parameters
    private lateinit var mUserViewModel: ViewModelData
    private lateinit var year: year



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_over, container, false)

        val y = SimpleDateFormat("yyyy")
        var year = Integer.parseInt(y.format(Date()))
        var y1=year
        val d = SimpleDateFormat("dd")
        var today = Integer.parseInt(d.format(Date()))

        // getting Current Month
        val m = SimpleDateFormat("M")
        var month = Integer.parseInt(m.format(Date()))

        var data= mutableListOf<Pair<String,Int>>()

//        var pie = AnyChart.pie()
//
//        var data: MutableList<DataEntry> = ArrayList()
//        data.add(ValueDataEntry("John", 10000))
//        data.add(ValueDataEntry("Jake", 12000))
//        data.add(ValueDataEntry("Peter", 18000))

//        println(data+"  data")
//
//        var anyChartView = view.findViewById(R.id.any_chart_view) as AnyChartView
//        pie.data(data)
//        anyChartView.setChart(pie)

         fristfun(year,month,view,data)
//         printarray()



        return view

    }
    fun fristfun(year: Int, month: Int, view: View, data: MutableList<Pair<String, Int>>) {
        mUserViewModel = ViewModelProvider(this).get(ViewModelData::class.java)

        mUserViewModel.Month_expense_chart("7/2023","Income").observe(viewLifecycleOwner,{data1->adddata(year,month-1,view,data) })

    }

    private fun adddata(year: Int, month: Int, view: View, data1: MutableList<Pair<String, Int>>) {

      var dates= getDatesForMonth(year,month)
        val s=dates.size


var d_arry= arrayOf<twodata>()

        for(i in 0 until s step 1) {
//            println(dates[i]+"  dd")
//            data.clear()


            mUserViewModel.Month_expense_chart(dates[i], "Income")
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {d->
                    if (d.isNotEmpty() ){

//                    println(d.first().price)
                        d_arry+=twodata(d.first().d_date,d.first().price)

                }})
        }
         mUserViewModel.monthdata("7","hrlo").observe(viewLifecycleOwner, androidx.lifecycle.Observer { printarray(d_arry) })



//        }




    }

    override fun onItemClick(currentItem: yeardata) {
        TODO("Not yet implemented")
    }

    override fun deletitem(currentItem: yeardata) {
        TODO("Not yet implemented")
    }

    override fun monthdat(date: String) {
        TODO("Not yet implemented")
    }

    override fun nodata() {
        TODO("Not yet implemented")
    }

    fun getDatesForMonth(year: Int, month: Int): List<String> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val dateFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())

        val dates = mutableListOf<String>()
        while (calendar.get(Calendar.MONTH) == month) {
            val formattedDate = dateFormat.format(calendar.time)
            dates.add(formattedDate)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dates

    }

    fun printarray(d_arry: Array<twodata>)
    {
        println(d_arry.size)
    }


}










