package com.yasma.expensetracker

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.yasma.expensetracker.data.ViewModelData
import com.yasma.expensetracker.data.yeardata
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var sel_mont1=""

/**
 * A simple [Fragment] subclass.
 * Use the [update_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class update_fragment() : DialogFragment() {
    private lateinit var duViewmodel: ViewModelData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_update_fragment, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.status)
            dialog?.window?.setBackgroundDrawableResource(R.drawable.input_main)
            //   activity?.window?.
        }
        duViewmodel = ViewModelProvider(this)[ViewModelData::class.java]
        val t: EditText = view.findViewById(R.id.up_editTextTextPersonName)
        val d: EditText = view.findViewById(R.id.up_editTextTextPersonName2)
        val p: EditText = view.findViewById(R.id.up_editTextTextPersonName3)
        val dat:TextView=view.findViewById(R.id.up_date)
        val e: RadioButton = view.findViewById(R.id.up_radioButton)
        val i: RadioButton = view.findViewById(R.id.up_radioButton1)
        var currentdates: String
        val month: String
        val margs = arguments
        if (margs != null) {
            t.setText(margs.getString("title", "hello"))
            d.setText(margs.getString("des", "hello"))
            p.setText(margs.getString("amount", "hello"))
            dat.setText(margs.getString("currentdate", "hello"))
            val ty: String = margs.getString("type", "hello")

            if (ty == "Expense") {
                e.setChecked(true)
            } else {
                i.setChecked(true)
            }

        }

        val up: Button = view.findViewById(R.id.up_button5)
        up.setOnClickListener() {
            val margs = arguments
            if (margs != null) {
                val curren = margs.getString("currentdate", "hello").toString()
                var mon = margs.getString("month", "heloo").toString()
                 currentdates=dat.getText().toString()

                val id = margs.getInt("id")

//                val delim="/"
//                var list=currentdate.split(delim)
//                mon=list[1]+"/"+list[2]


                update(t,d,p,e,i,currentdates,mon,id)


            }

        }
        dat.setOnClickListener(){
            showDatePickerDialog(requireContext(),dat)
        }
        return view
    }

    private fun update(
        t: EditText,
        d: EditText,
        p: EditText,
        e: RadioButton,
        i: RadioButton,
        currentdate: String,
        month: String,
        id: Int
    ) {
        val title: String = t.text.toString()
        val des: String = d.text.toString()
        val am: String = p.text.toString()


//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentDate = sdf.format(Date())
//        val m=SimpleDateFormat("MM")
//        val month=m.format(Date())
        val type: String

        if (title == "") {
            Toast.makeText(context, "Plz Enter Title", Toast.LENGTH_SHORT).show()
        } else if (des == "") {
            Toast.makeText(context, "Plz Enter des", Toast.LENGTH_SHORT).show()
        } else if (am == "") {
            Toast.makeText(context, "Plz Enter amount", Toast.LENGTH_SHORT).show()
        } else if (!((e.isChecked) || (i.isChecked))) {
            Toast.makeText(context, "Plz select type", Toast.LENGTH_SHORT).show()
        } else {
//            Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
            val amount: Int = Integer.parseInt(am.toString())

            if (e.isChecked) {
                type = "Expense"
                Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
            } else {
                type = "Income"
                Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
            }
            val updateUSER = yeardata(id, currentdate, type, title, des, sel_mont1, amount)
            duViewmodel.updateData(updateUSER)
            dismiss()
        }
    }

    fun showDatePickerDialog(context: Context, textView:TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context,
            { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                sel_mont1 = "${selectedMonth + 1}/$selectedYear"
                textView.text = selectedDate
            },
            year, month, dayOfMonth)

        datePickerDialog.show()
    }

}