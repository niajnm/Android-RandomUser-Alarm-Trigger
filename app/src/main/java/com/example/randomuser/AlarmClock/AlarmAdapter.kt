package com.example.randomuser.AlarmClock

import Database.DataModel
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.preference.PreferenceManager
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
import kotlinx.android.synthetic.main.alarm_picker_dialogue_layout.view.*
import java.util.ArrayList


class AlarmAdapter(var context: Context, val rdata: ArrayList<DataModel>) :
    RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {

    val cntx = context as AlarmActivity2
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewTitle: TextView = itemView.findViewById(R.id.MedicineTextView_id)
        var viewStatus: TextView = itemView.findViewById(R.id.StatusTextView_id)
        var viewTime: TextView = itemView.findViewById(R.id.alarmTextViewT_id)
        var imageViewLogo: ImageView = itemView.findViewById(R.id.alarmImg_id)
        var imageViewdelete: ImageView = itemView.findViewById(R.id.delete_button_id)
        var switch: Switch = itemView.findViewById(R.id.switch1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.alarm_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val time = rdata[position].alarmTime
        val time2 = rdata[position].alarmTime2
        val name = rdata[position].medicineName
        val name2 = rdata[position].medicineName2
        holder.viewTime.text = "$time $time2"
        holder.viewTitle.text = "$name $name2"
        holder.viewStatus.text = rdata[position].alarmDays

        holder.itemView.setOnClickListener {
            val time = rdata[position].alarmTime
            val time2 = rdata[position].alarmTime2
            val name = rdata[position].medicineName
            val name2 = rdata[position].medicineName2
            val days = rdata[position].alarmDays
            cntx.alarmDetails(time, time2, name, name2, days)
        }

        holder.imageViewdelete.setOnClickListener {
            val builder1 = AlertDialog.Builder(context)
            builder1.setMessage(Html.fromHtml("<font color='#3555d3'>Do you want to delete?</font>"))
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()

                    val key = rdata[position].alarmMagicKey

                    if (key != null) {
                        cntx.deleteAlarm(key)
                        notifyItemRemoved(position)
                    }
                })

            builder1.setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }

            val alert11: AlertDialog = builder1.create()
            alert11.show()

            val nbutton: Button = alert11.getButton(DialogInterface.BUTTON_NEGATIVE)
            val pbutton: Button = alert11.getButton(DialogInterface.BUTTON_POSITIVE)

            nbutton.setTextColor(Color.parseColor("#3555d3"))
            pbutton.setTextColor(Color.parseColor("#3555d3"))

        }
        val key = rdata[position].alarmMagicKey!!
        val tues = sharedPreferences.getBoolean("$key", true)
        holder.switch.isChecked = tues
        holder.switch.setOnClickListener {

            if (holder.switch.isChecked) {
                val key = rdata[position].alarmMagicKey!!
                editor.putBoolean("$key", true)
                editor.apply()

                    cntx.onAlarm(key)
            } else {
                val key = rdata[position].alarmMagicKey!!
                editor.putBoolean("$key", false)
                editor.apply()
                cntx.offAlarm(key)
            }
        }
    }

    override fun getItemCount(): Int {
        return rdata.size

        Log.d(ContentValues.TAG, "getItemCount: " + rdata.size)
    }

}