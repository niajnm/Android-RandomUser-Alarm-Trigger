package BloodPressure.fragment

import BloodPressure.BpAdapter
import Database.DatabaseHelper
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuser.R
import kotlinx.android.synthetic.main.fragment_bp_history.*
import kotlinx.android.synthetic.main.fragment_bp_history.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class bpHistoryFragment : Fragment() {

    lateinit var calender: Calendar
    lateinit var calender2: Calendar
    var recycle: RecyclerView? = null
    var dt1: Long = 0
    var dt2: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bp_history, container, false)
        val date2 = SimpleDateFormat("dd-M-yyyy").format(Date())
        v.historyDate2_id.text = date2
        recycle = v.findViewById(R.id.bpRecycer_id)

        dataShowList()
        calender2 = Calendar.getInstance()
        dt2 = calender2.timeInMillis

        v.history_ok_button.setOnClickListener {
            searchAnalysis(dt1, dt2)
        }

        v.historyDate1_id.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            val datepickerdialog =
                DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                    var month = monthOfYear + 1
                    val d1 = "$dayOfMonth-$month-$year"
                    historyDate1_id.text = d1
                    calender = Calendar.getInstance()
                    calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender.set(Calendar.MONTH, monthOfYear)
                    calender.set(Calendar.YEAR, year)
                    calender.set(Calendar.HOUR, 0)
                    calender.set(Calendar.MINUTE, 0)
                    calender.set(Calendar.SECOND, 0)

                    dt1 = calender.timeInMillis
                }, y, m, d)
            datepickerdialog.show()
        }


        v.historyDate2_id.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            val datepickerdialog =
                DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->

                    var month = monthOfYear + 1
                    val d1 = "$dayOfMonth-$month-$year"
                    v.historyDate2_id.text = d1
                    calender2 = Calendar.getInstance()
                    calender2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender2.set(Calendar.MONTH, monthOfYear)
                    calender2.set(Calendar.YEAR, year)
                    dt2 = calender2.timeInMillis
                }, y, m, d)

            datepickerdialog.show()
        }
        return v
    }

    private fun searchAnalysis(dt1: Long, dt2: Long) {
        var databaseHelper = DatabaseHelper(requireContext())
        val cursorS = databaseHelper!!.searchChartData(dt1, dt2)
        val searchData = databaseHelper!!.searchChartDataload(cursorS)
        val bpadapter = BpAdapter(requireContext(), searchData, this@bpHistoryFragment)
        recycle!!.adapter = bpadapter
        recycle!!.layoutManager = LinearLayoutManager(requireContext())
    }

    fun deleteItem(id: Int) {
        var databaseHelper = DatabaseHelper(requireContext())
        databaseHelper!!.deleteBpData(id)
        dataShowList()
    }

    fun dataShowList() {
        var databaseHelper = DatabaseHelper(requireContext())
        val cursor = databaseHelper!!.DisplayBPData()
        val Rdata = databaseHelper!!.loadBpData(cursor)
        val bpadapter = BpAdapter(requireActivity(), Rdata, this@bpHistoryFragment)
        recycle!!.adapter = bpadapter
        recycle!!.layoutManager = LinearLayoutManager(requireContext())

    }


}