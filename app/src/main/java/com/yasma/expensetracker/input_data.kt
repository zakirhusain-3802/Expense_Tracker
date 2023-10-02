package com.yasma.expensetracker

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yasma.expensetracker.data.ViewModelData
import com.yasma.expensetracker.data.yeardata

import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
 private var sel_month=""

/**
 * A simple [Fragment] subclass.
 * Use the [input_data.newInstance] factory method to
 * create an instance of this fragment.
 */
class input_data : DialogFragment() {


    // TODO: Rename and change types of parameters
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Change status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.status)
            dialog?.window?.setBackgroundDrawableResource(R.drawable.input_main)
         //   activity?.window?.
        }
    }
    private lateinit var dViewmodel: ViewModelData
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_input_data, container, false)
        dViewmodel = ViewModelProvider(this)[ViewModelData::class.java]
        val dat:TextView=view.findViewById(R.id.in_date)
        var sel_mont=""
        val sdf = SimpleDateFormat("d/M/yyyy")
        var currentDate = sdf.format(Date())

        println(currentDate)
        var today=""
        val m=SimpleDateFormat("M/yyyy")
        val month=m.format(Date())
        val clos: Button = view.findViewById(R.id.button5)
        clos.text = "ADD"

        dat.text = currentDate.toString()


        dat.setOnClickListener {
         today=  showDatePickerDialog(requireContext(),dat)
            println(today)
        }
        clos.setOnClickListener {
            tryd(view,today,month)

        }

        return view
    }
    fun tryd(view: View,today:String ,month:String){


        val t:EditText=view.findViewById(R.id.editTextTextPersonName)
        val d:EditText=view.findViewById(R.id.editTextTextPersonName2)
        val a:EditText=view.findViewById(R.id.editTextTextPersonName3)
        val dat:TextView=view.findViewById(R.id.in_date)
        val ex:RadioButton=view.findViewById(R.id.radioButton)
        val inc:RadioButton=view.findViewById(R.id.radioButton1)
        val ra:RadioGroup=view.findViewById(R.id.radioGroup)

        val title:String=t.text.toString()
        val des:String=d.text.toString()
        val am:String=a.text.toString()
        val todaydate=dat.text.toString()
       // println(today+" today")
//        currentDate=dat.getText().toString()
//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentDate = sdf.format(Date())
//        val m=SimpleDateFormat("MM")
//        val month=m.format(Date())
        val type:String

        if(title==""){
            Toast.makeText(context, "Plz Enter Title", Toast.LENGTH_SHORT).show()
        }
        else if(des==""){
            Toast.makeText(context, "Plz Enter des", Toast.LENGTH_SHORT).show()
        }
       else if(am==""){
            Toast.makeText(context, "Plz Enter amount", Toast.LENGTH_SHORT).show()
        }
        else if(!((ex.isChecked) || (inc.isChecked))){
            Toast.makeText(context, "Plz select type", Toast.LENGTH_SHORT).show()
        }

        else{
//            Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
          val amount:Int=Integer.parseInt(am.toString())

           if(ex.isChecked){
                type="Expense"
//               Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
           }
            else{
                 type ="Income"
//               Toast.makeText(context, type, Toast.LENGTH_SHORT).show()
            }
            println(todaydate+"....")
            val try_date=todaydate




            val delim="/"
            var list=try_date.split(delim)
//            println(list+" checking")
            var try_month= list[1].toString()+"/"+list[2].toString()
            println(try_month.toString())

            val deardata=yeardata(0,try_date,type,title,des, try_month, amount)
            dViewmodel.addData(deardata)
            dismiss()
//            val fragmentManager=parentFragmentManager
//            val ft=fragmentManager.beginTransaction()
//            val newFragment=HomeFragment()
//            ft.replace(R.id.new_home,newFragment)
//            ft.commit()
        }




    }

    fun showDatePickerDialog(context: Context, textView1:TextView): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        var selectedDate=""

        val datePickerDialog = DatePickerDialog(context,
            { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                 selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                sel_month = "${selectedMonth + 1}/$selectedYear"
                textView1.text = selectedDate

            },
            year, month, dayOfMonth)

        datePickerDialog.show()
        return selectedDate
    }


}







