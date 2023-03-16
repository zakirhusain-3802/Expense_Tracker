package com.yasma.expensetracker

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasma.expensetracker.data.ViewModelData
import com.yasma.expensetracker.data.yeardata
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_monthdaliy.*
import kotlinx.android.synthetic.main.fragment_monthdaliy.view.*
import java.time.Month
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [monthdaliy.newInstance] factory method to
 * create an instance of this fragment.
 */
class monthdaliy : Fragment(),recycle_Interface {
    // TODO: Rename and change types of parameters
    private lateinit var mUserViewModel: ViewModelData

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_monthdaliy, container, false)
        val setdate:TextView=view.findViewById(R.id.dailydate)
        val adapter = ListAdapter(this)
        val recycleView = view.daliy_m_recle
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        mUserViewModel = ViewModelProvider(this).get(ViewModelData::class.java)
        val getargs=arguments
        val dates:String
        val monthname:Month
        val mont:Int
        val year:Int


        if(getargs!=null){
           dates= getargs.getString("date", "hello")


            val delim="/"
            var list=dates.split(delim)


            mont=Integer.parseInt(list[0])
            year=Integer.parseInt(list[1])
            monthname=Month.of(mont)
            var printmont: String = mont.toString() + "/" + year.toString()
            val dates =
                getDatesForMonth(year, mont - 1) // a method to get a list of dates for the month
            var monthAdapter = MonthAdapter1(dates, mUserViewModel, printmont, mont, this)
            recycleView.adapter = monthAdapter


            mUserViewModel = ViewModelProvider(this).get(ViewModelData::class.java)

            setdate.text=monthname.toString()+"  "+year.toString()

            mUserViewModel.yeardata( mont.toString() + "/" + year.toString(),"Expense")
                .observe(viewLifecycleOwner, Observer { sum -> print_exp(sum.toString()) })


            mUserViewModel.yeardata( mont.toString() + "/" + year.toString(),"Income")
                .observe(viewLifecycleOwner, Observer { sum -> print_inc(sum.toString()) })


        }




        return view
    }

    override fun onItemClick(currentItem: yeardata) {
        update(currentItem)

    }

    override fun deletitem(currentItem: yeardata) {

        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage("")
            // if the dialog is cancelable
            .setCancelable(false)
            .setNegativeButton("Cancle",DialogInterface.OnClickListener{
                    dialog, id ->
                dialog.dismiss()
            })
            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    dialog, id ->
                mUserViewModel.deletData(currentItem)
                dialog.dismiss()

            })

        val alert = dialogBuilder.create()
        alert.setTitle("Do you want to Delete record ?")
        alert.show()
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
    }


    override fun monthdat(date: String) {
    }

    override fun nodata() {

    }

    fun update(position: yeardata) {
        val args: Bundle = Bundle()
        args.putString("title", position.title.toString())
        args.putString("des", position.description.toString())
        args.putString("amount", position.price.toString())
        args.putString("type", position.type.toString())
        args.putString("currentdate", position.d_date.toString())
        args.putString("month", position.month.toString())
        args.putInt("id", position.id)

        val update = update_fragment()
        update.setArguments(args)

        update.show(childFragmentManager, "Heloo")

    }
    fun getDatesForMonth(year: Int, month: Int): List<Date> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val dates = mutableListOf<Date>()
        while (calendar.get(Calendar.MONTH) == month) {
            dates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dates

    }
    fun print_inc(inputs: String) {
        var ex = expense_month.text.toString()

        var income1: Int
        if (inputs != "null") {
            income1 = Integer.parseInt(inputs.toString())
            income_month.text = (income1.toString())
        } else {
            income1 = 0
            income_month.text = "0"
        }
        var expense1: Int = Integer.parseInt(ex.toString())

//        if (income1 > expense1) {
//            status.setTextColor(Color.parseColor("#007500"))
//        } else {
//            status.setTextColor(Color.parseColor("#A30000"))
//        }
        total_month.text = (income1 - expense1).toString()

    }

    fun print_exp(exp: String) {


        var i = income_month.text.toString()


        var income1: Int = Integer.parseInt(i.toString())
        var expense1: Int
        if (exp != "null") {
            expense1 = Integer.parseInt(exp.toString())
            expense_month.text = (expense1.toString())
        } else {
            expense1 = 0
            expense_month.text = "0"
        }


//        if (income1 > expense1) {
//            status.setTextColor(Color.parseColor("#007500"))
//        } else {
//            status.setTextColor(Color.parseColor("#A30000"))
//        }
        total_month.text = (income1 - expense1).toString()
    }

}