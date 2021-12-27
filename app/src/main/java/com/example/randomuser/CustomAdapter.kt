package com.example.randomuser

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuser.model.Result
import com.squareup.picasso.Picasso

class CustomAdapter(var context: Context) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    private var userList = mutableListOf<Result>()

    val ctx = context as UserActivity


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
        val userPosition = userList[position]

        val firstName = userPosition.name.first
        val userGender = userPosition.gender
        val userMail = userPosition.email
        val userlocation = userPosition.location.country.toString()
        val userphone = userPosition.phone
        val userAge = userPosition.phone
        val userImage2 = userPosition.picture.medium
        val imgData: String? = userPosition.picture.large


        holder.viewTitle.text = firstName
        holder.viewDesc.text = userGender
        holder.viewPrice.text = userMail


        Picasso.get().load(imgData).into(holder.imageViewLogo)


        holder.itemView.setOnClickListener({

            ctx.passDetails(firstName,userGender,userMail,imgData,userlocation,userphone,userAge)

        })

    }

    override fun getItemCount(): Int {
        return userList.size

        Log.d(TAG, "getItemCount: " + userList.size)
    }

    fun setData(userList: MutableList<Result>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}