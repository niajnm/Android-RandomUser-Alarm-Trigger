package Yoga

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_beginner.*
import org.eazegraph.lib.models.PieModel
import org.eazegraph.lib.charts.PieChart
import android.view.View
import org.eazegraph.lib.models.BarModel

import org.eazegraph.lib.charts.BarChart

class BeginnerActivity : AppCompatActivity() {
    var pieChart: PieChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.randomuser.R.layout.activity_beginner)
        setData()

    }

    private fun setData() {
        // Set the percentage of language used
        val mBarChart = findViewById<View>(com.example.randomuser.R.id.barchart) as BarChart

        mBarChart.addBar(BarModel("Normal",120.3f, Color.parseColor("#06ad03")))
        mBarChart.addBar(BarModel("Elevated",2f, Color.parseColor("#56B7F1")))
        mBarChart.addBar(BarModel("Stage 1",3.3f, Color.parseColor("#ff9705")))
        mBarChart.addBar(BarModel("Stage 2",1.1f, Color.parseColor("#ff3305")))
        mBarChart.addBar(BarModel("Stage 3",2.7f, Color.parseColor("#ba0600")))


        mBarChart.startAnimation()
        // Set the data and color to the pie chart


    }
}