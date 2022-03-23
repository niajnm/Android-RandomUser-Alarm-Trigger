package News

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_web_news.*

class WebNewsActivity : AppCompatActivity() {
    var URl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_news)

        setSupportActionBar(webToolbar)
        title = "Heath News"
        val displayHomeAsUpEnabled = supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webToolbar.setNavigationOnClickListener { onBackPressed() }
        val intent = intent
        URl = intent.getStringExtra("url")

        webView_id.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                webProgress_id.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                webProgress_id.visibility = View.GONE

            }

        }
        val webSettings: WebSettings = webView_id.settings
        webSettings.javaScriptEnabled = true
        //  webSettings.pluginState = WebSettings.PluginState.ON
        webSettings.domStorageEnabled = true
        // webView_id.webViewClient = WebViewClient()
        webView_id.loadUrl(URl!!)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.webmenu_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuShare_id) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/app"
            val subject = "Arg News:"
            val head: String? = URl
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, head)
            startActivity(Intent.createChooser(intent, "Share with"))
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
        }
        if (item.itemId == R.id.menuReport_id) {
            Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}