package com.yasma.expensetracker

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasma.expensetracker.data.ViewModelData
import com.yasma.expensetracker.data.yeardata
import kotlinx.android.synthetic.main.fragment_month.view.*
import kotlinx.android.synthetic.main.fragment_over.view.*
import java.text.SimpleDateFormat
import java.util.*

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_over, container, false)
        val y = SimpleDateFormat("yyyy")
        var year = Integer.parseInt(y.format(Date()))
        val d = SimpleDateFormat("dd")
        var today = Integer.parseInt(d.format(Date()))

        // getting Current Month
        val m = SimpleDateFormat("M")
        var month = Integer.parseInt(m.format(Date()))




        val recycleView = view.over_recycle
        val layoytmanager = LinearLayoutManager(requireContext())
        layoytmanager.setReverseLayout(true)
        layoytmanager.setStackFromEnd(true);
        recycleView.layoutManager = layoytmanager
        mUserViewModel = ViewModelProvider(this).get(ViewModelData::class.java)
        var printmont:String= month.toString()+"/"+year.toString()

        val dates = getDatesForMonth(year, month-1) // a method to get a list of dates for the month
//        val monthAdapter = MonthAdapter1(dates, mUserViewModel,printmont,today,this)
//        recycleView.adapter = monthAdapter
        return view
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
        println(dates)
        return dates

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

    override fun onItemClick(currentItem: yeardata) {
      Toast.makeText(context,"hello",Toast.LENGTH_SHORT).show()
        update(currentItem)
    }

    override fun deletitem(currentItem: yeardata) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage("")
            // if the dialog is cancelable
            .setCancelable(false)
            .setNegativeButton("Cancle", DialogInterface.OnClickListener{
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
        Toast.makeText(context,"Welcome ",Toast.LENGTH_SHORT).show()
    }

    override fun nodata() {

    }

}



