package com.yasma.expensetracker

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yasma.expensetracker.data.ViewModelData
import com.yasma.expensetracker.data.year
import com.yasma.expensetracker.data.yeardata
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_month.*
import kotlinx.android.synthetic.main.fragment_month.view.*
import java.text.SimpleDateFormat
import java.time.Month
import java.time.YearMonth

import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MonthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MonthFragment : Fragment(), recycle_Interface {
    // TODO: Rename and change types of parameters
    private lateinit var mUserViewModel: ViewModelData
    private var i: Int = 0
    private lateinit var nodata: ImageView



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_month, container, false)
        val month_name: TextView = view.findViewById(R.id.month_name)
        nodata = view.findViewById(R.id.nodata1)


        var nodta: Boolean = false
        //getting current year
        val y = SimpleDateFormat("yyyy")
        var year = Integer.parseInt(y.format(Date()))

        // getting Current Month
        val m = SimpleDateFormat("M")
        var month = Integer.parseInt(m.format(Date()))


        val adapter = ListAdapter(this)
        val recycleView = view.month_recycle

        var printmont: String = "/" + year.toString()


        val arr_back: Button = view.findViewById(R.id.arr_back)
        val arr_up: Button = view.findViewById(R.id.arr_up)

//        val inc_mont:TextView=view.findViewById(R.id.income_month)
//        val exp_mont:TextView=view.findViewById(R.id.expense_month)
//        val bal:TextView=view.findViewById(R.id.total_month)






        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        mUserViewModel = ViewModelProvider(this).get(ViewModelData::class.java)

//        mUserViewModel.yeardata("02/07/2023","Income").observe(viewLifecycleOwner,Observer{ sum ->inc_mont.text=inc.toString()})
          mUserViewModel.yearincome("%$year","Income").observe(viewLifecycleOwner,Observer{
              sum->print_inc(sum.toString())
          })
        mUserViewModel.yearexpense("%$year","Expense").observe(viewLifecycleOwner,Observer{
                sum->print_exp(sum.toString())
//            println(sum.toString()+" Sum of Income")
        })



//        getdata(mfor_data, adapter)
        //printing month name on title
        val cal = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM")
        cal[Calendar.MONTH] = month - 1
        val mon_name = month_date.format(cal.time)
        month_name.text = year.toString()
        printmont = "/" + year.toString()
        val dates =
            getDatesForMonth(year, month - 1) // a method to get a list of dates for the month
        val monthAdapter = MonthAdapter(years_month(year), mUserViewModel, printmont, nodta, this)
        starts(monthAdapter, recycleView)

        //recycleView.adapter = monthAdapter

        arr_back.setOnClickListener {
            i = 0
            year = year - 1
            printmont = "/" + year.toString()
            val monthAdapter =
                MonthAdapter(years_month(year), mUserViewModel, printmont, nodta, this)
            starts(monthAdapter, recycleView)
            month_name.text = year.toString()

//        mUserViewModel.yeardata("02/07/2023","Income").observe(viewLifecycleOwner,Observer{ sum ->inc_mont.text=inc.toString()})
            mUserViewModel.yearincome("%$year","Income").observe(viewLifecycleOwner,Observer{
                    sum->print_inc(sum.toString())
            })
            mUserViewModel.yearexpense("%$year","Expense").observe(viewLifecycleOwner,Observer{
                    sum->print_exp(sum.toString())
//            println(sum.toString()+" Sum of Income")
            })


        }
        arr_up.setOnClickListener {

            i = 0
            year = year + 1
            printmont = "/" + year.toString()
            val monthAdapter =
                MonthAdapter(years_month(year), mUserViewModel, printmont, nodta, this)
            starts(monthAdapter, recycleView)
            month_name.text = year.toString()

//        mUserViewModel.yeardata("02/07/2023","Income").observe(viewLifecycleOwner,Observer{ sum ->inc_mont.text=inc.toString()})
            mUserViewModel.yearincome("%$year","Income").observe(viewLifecycleOwner,Observer{
                    sum->print_inc(sum.toString())
            })
            mUserViewModel.yearexpense("%$year","Expense").observe(viewLifecycleOwner,Observer{
                    sum->print_exp(sum.toString())
//            println(sum.toString()+" Sum of Income")
            })

        }
        return view

    }

    private fun starts(monthAdapter: MonthAdapter, recycleView: RecyclerView?) {
        recycleView?.adapter = monthAdapter

    }


    private fun printtoast(toString: String) {

        Toast.makeText(context, toString, Toast.LENGTH_SHORT).show()
    }

    private fun getMonth_add(monthInd: Int): Int {
        var month_ind = monthInd
        var month_name = 0
        if (month_ind < 12) {
            month_ind += 1
            month_name = month_ind

        } else if (month_ind == 12) {
            month_ind = 1
            month_name = month_ind


        }

//        println("month name index: "+month_name)

        return month_name
    }


    private fun getMonth_minus(mforData: Int): Int {
        var month_ind = mforData
        var month_name: Int = 0
        if (month_ind > 1) {
            month_ind -= 1
            month_name = month_ind


        } else if (month_ind == 1) {
            month_ind = 12
            month_name = month_ind
        }


//        println("month name index: "+month_name)

        return month_name


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
        update.arguments = args

        update.show(childFragmentManager, "Heloo")

    }

    override fun onItemClick(position: yeardata) {

    }

    override fun monthdat(date: String) {

        val args: Bundle = Bundle()
        args.putString("date", date)

        Toast.makeText(context, date, Toast.LENGTH_SHORT).show()
        val fragmentManager = parentFragmentManager
        val ft = fragmentManager.beginTransaction()
        val newFragment = monthdaliy()
        newFragment.arguments = args
        ft.setCustomAnimations(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
        ft.replace(R.id.newfragmnet, newFragment)
        ft.addToBackStack(null)
        ft.commit()

    }

    override fun nodata() {
        i = i + 1
        println(i)
        checknodta()
    }

    override fun deletitem(currentItem: yeardata) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage("")
            // if the dialog is cancelable
            .setCancelable(false)
            .setNegativeButton("Cancle", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()

            })

        val alert = dialogBuilder.create()
        alert.setTitle("Do you want to Delete record ?")
        alert.show()
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()

    }

    private fun getDatesForMonth(year: Int, month: Int): List<Date> {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun years_month(year: Int): MutableList<Month> {
        val months = mutableListOf<Month>()
        for (month in Month.values()) {
            val yearMonth = YearMonth.of(year, month)
            if (yearMonth.year == year) {
                months.add(month)
            }
        }
        return months
    }

    fun checknodta() {
        if (nodata != null) {
            nodata.isVisible = i == 12
        }
    }

    fun print_inc(inputs: String) {
        var ex = exp_month.text.toString()

        var income1: Int
        if (inputs != "null") {
            income1 = Integer.parseInt(inputs.toString())
            inc_month.text = (income1.toString())
        } else {
            income1 = 0
            inc_month.text = "0"
        }
        var expense1: Int = Integer.parseInt(ex.toString())
        tot_month.text=(income1-expense1).toString()




    }

    fun print_exp(exp: String) {


        var i = inc_month.text.toString()


        var income1: Int = Integer.parseInt(i.toString())
        var expense1: Int
        if (exp != "null") {
            expense1 = Integer.parseInt(exp.toString())
            exp_month.text = (expense1.toString())
        } else {
            expense1 = 0
            exp_month.text = "0"
        }
        tot_month.text=(income1-expense1).toString()




    }


}