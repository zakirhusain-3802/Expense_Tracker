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





        return view
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


}










