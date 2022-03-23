package BloodPressure.fragment

import Database.DatabaseHelper
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomuser.R
import kotlinx.android.synthetic.main.fragment_bp_analysis.*
import kotlinx.android.synthetic.main.fragment_bp_analysis.view.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.SimpleDateFormat
import java.util.*

class BpAnalysisFragment : Fragment() {
    var dt1: Long = 0
    var dt2: Long = 0
    var date = ""
    lateinit var calender: Calendar
    lateinit var calender2: Calendar
    var mPieChart: PieChart? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_bp_analysis, container, false)
        mPieChart = v.findViewById<View>(R.id.piechart) as PieChart
        var databaseHelper = DatabaseHelper(requireContext())
        val key = 2
        val key2 = 4
        val key3 = 6
        val key4 = 8
        val key5 = 200
        val key6 = 10
        val cursor = databaseHelper!!.DisplayChartData(key)
        val cursor2 = databaseHelper!!.DisplayChartData(key2)
        val cursor3 = databaseHelper!!.DisplayChartData(key3)
        val cursor4 = databaseHelper!!.DisplayChartData(key4)
        val cursor5 = databaseHelper!!.DisplayChartData(key5)
        val cursor6 = databaseHelper!!.DisplayChartData(key6)
        val Rdata = databaseHelper!!.loadBpData(cursor)
        val date2 = SimpleDateFormat("dd-M-yyyy").format(Date())
        v.srchDate2_id.text = date2
        calender = Calendar.getInstance()
        dt2 = calender.timeInMillis

        v.srchDate1_id.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            val datepickerdialog =
                DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                    var month = monthOfYear + 1
                    //  date = "$dayOfMonth-$month-$year"
                    srchDate1_id.text = "$dayOfMonth-$month-$year"
                    calender = Calendar.getInstance()
                    calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender.set(Calendar.MONTH, monthOfYear)
                    calender.set(Calendar.YEAR, year)
                    calender.set(Calendar.HOUR, 0)
                    calender.set(Calendar.MINUTE, 0)
                    calender.set(Calendar.SECOND, 0)
                    calender.set(Calendar.MILLISECOND, 0)
                    dt1 = calender.timeInMillis
                    Log.d(TAG, "first time: $dt1")
                }, y, m, d)
            datepickerdialog.show()
        }

        v.srchDate2_id.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            // val dt1= cal.timeInMillis

            val datepickerdialog =
                DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                    var month = monthOfYear + 1
                    srchDate2_id.text = "$dayOfMonth-$month-$year"
                    calender2 = Calendar.getInstance()
                    calender2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender2.set(Calendar.MONTH, monthOfYear)
                    calender2.set(Calendar.YEAR, year)
                    dt2 = calender2.timeInMillis
                    Log.d(TAG, "first time: $dt2")
                    // searchAnalysis(dt1, dt2)
                }, y, m, d)

            datepickerdialog.show()
        }

        //itmedicus123
        v.analysis_ok_button.setOnClickListener {
            searchAnalysis(dt1, dt2)
        }

        v.tvlow.setText(Integer.toString(cursor5.count))
        v.tvR.setText(Integer.toString(cursor.count))
        v.tvPython.setText(Integer.toString(cursor2.count))
        v.tvCPP.setText(Integer.toString(cursor3.count))
        v.tvJava.setText(Integer.toString(cursor4.count))
        v.tvh3.setText(Integer.toString(cursor6.count))

        // Set the data and color to the pie chart
        val mPieChart = v.findViewById<View>(R.id.piechart) as PieChart
        mPieChart.addPieSlice(
            PieModel(
                "Low",
                v.tvlow.text.toString().toFloat(),
                Color.parseColor("#00BCD4")
            )
        )
        mPieChart.addPieSlice(
            PieModel(
                "Normal",
                v.tvR.text.toString().toFloat(),
                Color.parseColor("#4CAF50")
            )
        )
        mPieChart.addPieSlice(
            PieModel(
                "Elevated",
                v.tvPython.text.toString().toFloat(),
                Color.parseColor("#FF9800")
            )
        )
        mPieChart.addPieSlice(
            PieModel(
                "High S1",
                v.tvCPP.text.toString().toFloat(),
                Color.parseColor("#FF5722")
            )
        )
        mPieChart.addPieSlice(
            PieModel(
                "High S2",
                v.tvJava.text.toString().toFloat(),
                Color.parseColor("#EA2C1E")
            )
        )
        mPieChart.addPieSlice(
            PieModel(
                "High S3",
                v.tvh3.text.toString().toFloat(),
                Color.parseColor("#830202")
            )
        )
        mPieChart.startAnimation()
        return v
    }

    private fun searchAnalysis(d1: Long, d2: Long) {
        var databaseHelper = DatabaseHelper(requireContext())
        val cursorS = databaseHelper!!.searchChartData(d1, d2)
        val searchData = databaseHelper!!.searchChartDataload(cursorS)
        var normal = 0
        var elevate = 0
        var high1 = 0
        var high2 = 0
        var high3 = 0
        var low = 0
        Log.d(TAG, "cursorAnalysis: ${cursorS.count}")
        Log.d(TAG, "searchAnalysis: ${searchData.indices}")

        for (i in searchData.indices) {
            when (searchData[i].bpColor) {
                2 -> normal += +1
                4 -> elevate += +1
                6 -> high1 += +1
                8 -> high2 += +1
                10 -> high3 += +1
                200 -> low += +1
            }
        }
        chart(low, normal, elevate, high1, high2, high3)
    }

    private fun chart(low: Int, normal: Int, elevate: Int, high1: Int, high2: Int, high3: Int) {
        tvlow.setText(Integer.toString(low))
        tvR.text = Integer.toString(normal)
        tvPython.text = Integer.toString(elevate)
        tvCPP.text = Integer.toString(high1)
        tvJava.text = Integer.toString(high2)
        tvh3.text = Integer.toString(high3)

        // Set the data and color to the pie chart
        mPieChart?.clearChart()
        mPieChart?.addPieSlice(PieModel("Low", low.toFloat(), Color.parseColor("#00BCD4")))
        mPieChart?.addPieSlice(PieModel("Normal", normal.toFloat(), Color.parseColor("#4CAF50")))
        mPieChart?.addPieSlice(PieModel("Elevated", elevate.toFloat(), Color.parseColor("#FF9800")))
        mPieChart?.addPieSlice(PieModel("High S1", high1.toFloat(), Color.parseColor("#FF5722")))
        mPieChart?.addPieSlice(PieModel("High S2", high2.toFloat(), Color.parseColor("#EA2C1E")))
        mPieChart?.addPieSlice(PieModel("High S3", high3.toFloat(), Color.parseColor("#830202")))

        mPieChart?.startAnimation()
    }
}