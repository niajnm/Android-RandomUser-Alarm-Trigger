package com.example.randomuser.AlarmClock

import Database.DataModel
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuser.R
import com.example.randomuser.UserActivity
import com.example.randomuser.model.Result
import com.squareup.picasso.Picasso
import java.util.ArrayList
import android.widget.CompoundButton




class AlarmAdapter(var context: Context, val rdata: ArrayList<DataModel>) : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {

    val cntx = context as AlarmActivity2

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewTitle: TextView
        var viewStatus: TextView
        var viewTime: TextView
        var imageViewLogo: ImageView
        var imageViewdelete: ImageView
        var switch: Switch

        init {
            viewTitle = itemView.findViewById(R.id.MedicineTextView_id)
            viewStatus = itemView.findViewById(R.id.StatusTextView_id)
            viewTime = itemView.findViewById(R.id.alarmTextViewT_id)
            imageViewLogo = itemView.findViewById(R.id.alarmImg_id)
            imageViewdelete = itemView.findViewById(R.id.delete_button_id)
            switch = itemView.findViewById(R.id.switch1)
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



   holder.itemView.setOnClickListener {



   }


            holder.imageViewdelete.setOnClickListener {
                val builder1 = AlertDialog.Builder(context)
                builder1.setMessage(Html.fromHtml("<font color='#3555d3'>Do you want to delete?</font>"))
                builder1.setCancelable(true)

                builder1.setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel()

                        val key = rdata[position].alarmMagicKey

                        if (key != null) {
                            cntx.deleteAlarm(key)
                           notifyItemRemoved(position)
                        }

                    })

                builder1.setNegativeButton(
                    "No",
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

                val alert11: AlertDialog = builder1.create()
                alert11.show()

                val nbutton: Button = alert11.getButton(DialogInterface.BUTTON_NEGATIVE)
                val pbutton: Button = alert11.getButton(DialogInterface.BUTTON_POSITIVE)


                nbutton.setTextColor(Color.parseColor("#3555d3"))
                pbutton.setTextColor(Color.parseColor("#3555d3"))

            }

        holder.switch.setOnClickListener {

            if (holder.switch.isChecked){

            }else{
                val key = rdata[position].alarmMagicKey

                if (key != null) {
                    cntx.offAlarm(key)

                }

            }
        }



    }

    override fun getItemCount(): Int {
        return rdata.size

        Log.d(ContentValues.TAG, "getItemCount: " + rdata.size)
    }

}