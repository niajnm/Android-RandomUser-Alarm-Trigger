package com.example.randomuser.AlarmClock

import Database.DataModel
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuser.R
import com.example.randomuser.UserActivity
import com.example.randomuser.model.Result
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AlarmAdapter(var context: Context, val rdata: ArrayList<DataModel>) : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewTitle: TextView
        var viewStatus: TextView
        var viewTime: TextView

        var imageViewLogo: ImageView

        init {
            viewTitle = itemView.findViewById(R.id.MedicineTextView_id)
            viewStatus = itemView.findViewById(R.id.StatusTextView_id)
            viewTime = itemView.findViewById(R.id.alarmTextViewT_id)

            imageViewLogo = itemView.findViewById(R.id.alarmImg_id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.alarm_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.viewTime.text= rdata[position].alarmTime
            holder.viewTitle.text= rdata[position].medicineName
            holder.viewStatus.text= rdata[position].alarmDays



    }

    override fun getItemCount(): Int {
        return rdata.size

        Log.d(ContentValues.TAG, "getItemCount: " + rdata.size)
    }



}