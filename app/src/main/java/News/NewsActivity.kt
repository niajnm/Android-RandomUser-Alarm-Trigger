package News

import News.Utils.Companion.APIKEY
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.APIClient
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Spinner


class NewsActivity : AppCompatActivity() {

    var newsAdapter: NewsAdapter? = null
    private var country: String = "us"
    var countryAdapter: ArrayAdapter<String>? = null
    var countryArray: Array<String>? = null
    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsAdapter = NewsAdapter(this)
        newsRecycler_id.adapter = newsAdapter
        newsRecycler_id.layoutManager = LinearLayoutManager(this)
        getNewsData(country)

        setSupportActionBar(newsToolbar_id)
        title = "Health News"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        newsToolbar_id!!.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        this.menuInflater.inflate(R.menu.country_menu, menu)
        val item = menu!!.findItem(R.id.menuCountry_id)
        val spinner = item.actionView as Spinner

        countryArray = resources.getStringArray(R.array.country_name)
        countryAdapter = ArrayAdapter<String>(
            this, R.layout.support_simple_spinner_dropdown_item, countryArray!!
        )
        spinner.adapter = countryAdapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    context,
                    countryArray!![position].toString() + " selected",
                    Toast.LENGTH_SHORT
                ).show()
                countrySet(countryArray!!.get(position))
                newsProgress_id.visibility= View.VISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


//        if (item.itemId == R.id.menuShare_id) {
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "text/app"
//            val subject = "Arg News:"
//            val head: String? = URl
//            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
//            intent.putExtra(Intent.EXTRA_TEXT, head)
//            startActivity(Intent.createChooser(intent, "Share with"))
//            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
//        }

        return super.onOptionsItemSelected(item)
    }

    private fun getNewsData(country: String) {
        val api = Retrofitnews.getClient()
        val call = api.getNews("health", country, APIKEY)

        call.enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                if (response.isSuccessful) {
                    newsProgress_id.visibility= View.GONE
                    val code = response.code()
                    var list = mutableListOf<NewsData.Article>()
                    val value = response.body()!!.articles as MutableList<NewsData.Article>
                    list.addAll(value)
                    newsAdapter?.setData(list)
                    Log.d("TAG", "DataResponse $list")
                }
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
             //   TODO("Not yet implemented")
            }

        })
    }

    fun passData(newsTitle: String, newsDesc: String, newsUrl: String) {

        val intent = Intent(this, WebNewsActivity::class.java)
        intent.putExtra("title", newsTitle)
        intent.putExtra("desc", newsDesc)
        intent.putExtra("url", newsUrl)
        startActivity(intent)
    }

    private fun countrySet(s: String) {
        when (s) {
            "USA" -> {
                country = "us"
                getNewsData(country)
            }
            "India" -> {
                country = "in"
                getNewsData(country)
            }
            "China" -> {
                country = "cn"
                getNewsData(country)
            }
            "Argentina" -> {
                country = "ar"
                getNewsData(country)
            }
            "Hongkong" -> {
                country = "hk"
                getNewsData(country)
            }
            "Canada" -> {
                country = "ca"
                getNewsData(country)
            }
            "Japan" -> {
                country = "jp"
                getNewsData(country)
            }
            else -> {
                Toast.makeText(context, "This Country is Developing ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}