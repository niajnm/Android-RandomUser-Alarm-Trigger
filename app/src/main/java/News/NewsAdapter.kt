package News

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
import com.squareup.picasso.Picasso

class NewsAdapter (var context: Context) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var userList = mutableListOf<NewsData.Article>()
    val ctx = context as NewsActivity

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewTitle: TextView = itemView.findViewById(R.id.TextViewTitle_id)
        var viewPublish: TextView = itemView.findViewById(R.id.textviewPublish_id)
        var viewDesc: TextView = itemView.findViewById(R.id.textviewDetail_id)

        var imageViewLogo: ImageView

        init {

            imageViewLogo = itemView.findViewById(R.id.imgFlag_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.news_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userPosition = userList[position]

        val newsTitle = userPosition.title
        val newsPublish = userPosition.publishedAt
        val newsDesc = userPosition.description
        val newsUrl = userPosition.url
//        val userlocation = userPosition.location.country.toString()
//        val userphone = userPosition.phone
//        val userAge = userPosition.nat
//        val userImage2 = userPosition.picture.medium
        val imgData: String? = userPosition.urlToImage


        holder.viewTitle.text = newsTitle
        holder.viewDesc.text = newsDesc
        holder.viewPublish.text = newsPublish


        Picasso.get().load(imgData).into(holder.imageViewLogo)


        holder.itemView.setOnClickListener {

            ctx.passData(newsTitle,newsDesc,newsUrl)

        }

    }

    override fun getItemCount(): Int {
        return userList.size

        Log.d(ContentValues.TAG, "getItemCount: " + userList.size)
    }

    fun setData(userList: MutableList<NewsData.Article>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}