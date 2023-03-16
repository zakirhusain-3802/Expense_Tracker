package com.yasma.expensetracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yasma.expensetracker.data.yeardata
import kotlinx.android.synthetic.main.exp_div1.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter1(private final var recycleInterface: recycle_Interface) :
    RecyclerView.Adapter<com.yasma.expensetracker.ListAdapter1.MyViewHolde>() {
    private var yearData = emptyList<yeardata>()


    class MyViewHolde(itemview: View) : RecyclerView.ViewHolder(itemview) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolde {

        return MyViewHolde(
            LayoutInflater.from(parent.context).inflate(R.layout.exp_div1, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return yearData.size
    }


    override fun onBindViewHolder(holder: MyViewHolde, position: Int) {
        val currentItem = yearData[position]
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        val date: String = currentDate

        val type = currentItem.type.toString()
        if (type == "Expense") {
            holder.itemView.textView51.setTextColor(Color.parseColor("#A30000"))
            holder.itemView.textView51.text = "- " + currentItem.price.toString()
        } else {
            holder.itemView.textView51.setTextColor(Color.parseColor("#3F51B5"))
            holder.itemView.textView51.text = currentItem.price.toString() + ".00"
        }
        holder.itemView.textView31.text = currentItem.title.toString()
        holder.itemView.textView41.text = currentItem.d_date.toString()
//        holder.itemView.textView5.text = currentItem.price.toString()

        holder.itemView.rowlayout1.setOnClickListener() {
            if (recycleInterface !== null) {
                recycleInterface.onItemClick(currentItem)
            }

        }
        holder.itemView.setOnLongClickListener { delet(recycleInterface,currentItem) }



    }

    private fun delet(recycleInterface: recycle_Interface, currentItem: yeardata): Boolean {
       if(recycleInterface!==null){
        delets(recycleInterface,currentItem)
       }
         return true
    }

    private fun delets(recycleInterface: recycle_Interface, currentItem: yeardata) {
       if (recycleInterface!==null){
           recycleInterface.deletitem(currentItem)
       }
    }

    fun setData(year: List<yeardata>) {
        this.yearData = year
        notifyDataSetChanged()
    }


}




