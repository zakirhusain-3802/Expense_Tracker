package com.yasma.expensetracker

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yasma.expensetracker.data.ViewModelData
import com.yasma.expensetracker.data.yeardata
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import android.widget.DatePicker
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), recycle_Interface {

    private lateinit var mUserViewModel: ViewModelData
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        firebaseAuth = FirebaseAuth.getInstance()


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val daily_month: TextView = view.findViewById(R.id.daily_month)
        val cal = Calendar.getInstance()

        val recycleView = view.Recycle
        val sdf = SimpleDateFormat("MMM")
        val currentDate = sdf.format(Date())

        ///
        val y = SimpleDateFormat("yyyy")
        var year = Integer.parseInt(y.format(Date()))
        var y1 = year
        val d = SimpleDateFormat("dd")
        var today = Integer.parseInt(d.format(Date()))

        // getting Current Month
        val m = SimpleDateFormat("M")
        var month = Integer.parseInt(m.format(Date()))
        var m1 = month

        val m_title = SimpleDateFormat("MMM")

        daily_month.text = currentDate.toString() + " " + year.toString()

        cal[Calendar.MONTH] = month - 1
        var mon_title = m_title.format(cal.time)
        val current_month = month.toString() + "/" + year.toString()

//        currentMonth.text=mon_title.toString()+" "+year.toString()
        ///
        val layoytmanager = LinearLayoutManager(requireContext())
        layoytmanager.reverseLayout = true
        layoytmanager.stackFromEnd = true

        recycleView.layoutManager = layoytmanager
        mUserViewModel = ViewModelProvider(this).get(ViewModelData::class.java)

        var printmont: String = month.toString() + "/" + year.toString()

        val dates =
            getDatesForMonth(year, month - 1) // a method to get a list of dates for the month
        var monthAdapter = MonthAdapter1(dates, mUserViewModel, printmont, today, this)
        recycleView.adapter = monthAdapter


        mUserViewModel = ViewModelProvider(this).get(ViewModelData::class.java)

        mUserViewModel.yeardata(current_month, "Expense")
            .observe(viewLifecycleOwner, Observer { sum -> print_exp(sum.toString()) })


        mUserViewModel.yeardata(current_month, "Income")
            .observe(viewLifecycleOwner, Observer { sum -> print_inc(sum.toString()) })

        val b: ImageButton = view.findViewById(R.id.imageButton)
        b.setOnClickListener {

            val update = input_data()
            update.show(childFragmentManager, "Heloo")

        }

        val ch_back: ImageView = view.findViewById(R.id.ch_month_backward)
        ch_back.setOnClickListener {
            val cal = Calendar.getInstance()
            if (m1 > 1) {
                m1 = m1 - 1
            } else {
                m1 = 12
                y1 = y1 - 1
            }


            cal[Calendar.MONTH] = m1 - 1
            val dateFormat1 = SimpleDateFormat("MMM")
            val curr_date1 = dateFormat1.format(cal.time)
            daily_month.text = curr_date1 + " " + y1.toString()
            println(curr_date1 + " " + m1.toString())

            val dates =
                getDatesForMonth(y1, m1 - 1) // a method to get a list of dates for the month
            var monthAdapter = MonthAdapter1(
                dates, mUserViewModel, (m1.toString() + "/" + y1.toString()), today, this
            )
            recycleView.adapter = monthAdapter

            mUserViewModel.yeardata(m1.toString() + "/" + y1.toString(), "Expense")
                .observe(viewLifecycleOwner, Observer { sum -> print_exp(sum.toString()) })


            mUserViewModel.yeardata(m1.toString() + "/" + y1.toString(), "Income")
                .observe(viewLifecycleOwner, Observer { sum -> print_inc(sum.toString()) })

        }
        val ch_for: ImageView = view.findViewById(R.id.ch_month_forward)
        ch_for.setOnClickListener {
            val cal = Calendar.getInstance()
            if (m1 < 12) {
                m1 = m1 + 1
            } else {
                m1 = 1
                y1 = y1 + 1
            }


            cal[Calendar.MONTH] = m1 - 1
            val dateFormat1 = SimpleDateFormat("MMM")
            val curr_date1 = dateFormat1.format(cal.time)
            daily_month.text = curr_date1 + " " + y1.toString()

            val dates =
                getDatesForMonth(y1, m1 - 1) // a method to get a list of dates for the month
            var monthAdapter = MonthAdapter1(
                dates, mUserViewModel, (m1.toString() + "/" + y1.toString()), today, this
            )
            recycleView.adapter = monthAdapter

            mUserViewModel.yeardata(m1.toString() + "/" + y1.toString(), "Expense")
                .observe(viewLifecycleOwner, Observer { sum -> print_exp(sum.toString()) })


            mUserViewModel.yeardata(m1.toString() + "/" + y1.toString(), "Income")
                .observe(viewLifecycleOwner, Observer { sum -> print_inc(sum.toString()) })

        }
        daily_month.setOnClickListener {

        }

        return view

    }

    private fun starts(recycleView: RecyclerView?, currentDate: String) {

    }


    fun print_inc(inputs: String) {
        var ex = expense.text.toString()

        var income1: Int
        if (inputs != "null") {
            income1 = Integer.parseInt(inputs.toString())
            income.text = (income1.toString())
        } else {
            income1 = 0
            income.text = "0"
        }
        var expense1: Int = Integer.parseInt(ex.toString())

        if (income1 > expense1) {
            status.setTextColor(Color.parseColor("#007500"))
        } else {
            status.setTextColor(Color.parseColor("#A30000"))
        }
        status.text = (income1 - expense1).toString()
        nodata.isVisible = status.text == "0"

    }

    fun print_exp(exp: String) {

        var i = income.text.toString()

        var income1: Int = Integer.parseInt(i.toString())
        var expense1: Int
        if (exp != "null") {
            expense1 = Integer.parseInt(exp.toString())
            expense.text = (expense1.toString())
        } else {
            expense1 = 0
            expense.text = "0"
        }


        if (income1 > expense1) {
            status.setTextColor(Color.parseColor("#007500"))
        } else {
            status.setTextColor(Color.parseColor("#A30000"))

        }
        status.text = (income1 - expense1).toString()
        nodata.isVisible = status.text == "0"
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

        update(position)
    }

    override fun deletitem(currentItem: yeardata) {

        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage("")
            // if the dialog is cancelable
            .setCancelable(false)
            .setNegativeButton("Cancle", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            }).setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
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

    private fun retrieveUsername(): String {
        return sharedPreferences.getString("username", "1") ?: "1"
    }

}







