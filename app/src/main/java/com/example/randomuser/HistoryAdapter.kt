package com.example.randomuser

import Database.DataModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.ArrayList

class HistoryAdapter (var context: Context, var rdata :ArrayList<DataModel>) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {



    val ctx = context as HistoryActivity


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewTitle: TextView
        var viewPrice: TextView
        var viewDesc: TextView

        var imageViewLogo: ImageView

        init {
            viewTitle = itemView.findViewById(R.id.textView_title)
            viewPrice = itemView.findViewById(R.id.textViewCart_price)
            viewDesc = itemView.findViewById(R.id.textView_desc)

            imageViewLogo = itemView.findViewById(R.id.sampleImg_id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.sample_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userPosition = rdata[position]

        val firstName = userPosition.historyName
        val userGender = userPosition.historyGender
        val userMail = userPosition.historyMail
        val userlocation = userPosition.historyCountry
        val userphone = userPosition.historyPhone
        val userAge = userPosition.historyAge
      //  val userImage2 = userPosition.picture.medium
        val imgData: String? = userPosition.historyImg


        holder.viewTitle.text = firstName
        holder.viewDesc.text = userGender
        holder.viewPrice.text = userMail


        Picasso.get().load(imgData).into(holder.imageViewLogo)


        holder.itemView.setOnClickListener({

            ctx.passHistoryDetails(firstName,userGender,userMail,imgData,userlocation,userphone,userAge)

        })
    }

    override fun getItemCount(): Int {
        return  rdata.size
    }
}