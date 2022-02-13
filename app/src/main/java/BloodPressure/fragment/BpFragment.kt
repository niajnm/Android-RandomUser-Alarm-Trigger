package BloodPressure.fragment

import BloodPressure.BpAdapter
import Database.DatabaseHelper
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.randomuser.R
import kotlinx.android.synthetic.main.bp_extrimity_layout.*
import kotlinx.android.synthetic.main.fragment_bp.*
import kotlinx.android.synthetic.main.fragment_bp.view.*
import java.text.SimpleDateFormat
import java.util.*


class BpFragment : Fragment() {
    var extrimity = "Right Arm"
    var position = "Seated"
    var result = ""
    var val1 = 120
    var val2 = 80
    var cal_milisec: Long = 0
    var date = SimpleDateFormat("dd-MM-yyyy").format(Date())
    var name = "name"
    var currentTime = ""
    var pulse = 60
    var map = 0
    var colorCode = 0
    var chartVal = 1
    lateinit var calender: Calendar
    lateinit var calender2: Calendar
    var bpadapter: BpAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bp, container, false)
        currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        v.timetext_id.text = currentTime

        v.syslicPick_id.maxValue = 300
        v.syslicPick_id.minValue = 40
        v.syslicPick_id.value = 120

        v.diastolicPick_id.maxValue = 200
        v.diastolicPick_id.minValue = 40
        v.diastolicPick_id.value = 80

        v.pulsePick_id.maxValue = 200
        v.pulsePick_id.minValue = 10
        v.pulsePick_id.value = 60

        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        val test = cal.timeInMillis

        Log.d(TAG, "beforecalmili $test")
        cal.set(Calendar.DAY_OF_MONTH, d)
        cal.set(Calendar.MONTH, m)
        cal.set(Calendar.YEAR, y)
//        cal.set(Calendar.HOUR,0)
//        cal.set(Calendar.MINUTE,0)
//        cal.set(Calendar.SECOND,0)
        cal_milisec = cal.timeInMillis

        Log.d(TAG, "calmili $cal_milisec")

        v.currentDate_id.text = date

        v.currentDate_id.setOnClickListener {

            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
           //  bpResult_id.text= "$y-$m-$d"

            val datepickerdialog =
                DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->

                    var month = monthOfYear + 1
                    currentDate_id.text = "$dayOfMonth-$month-$year"

                    //date="$dayOfMonth-$month-$year"
                    date="$dayOfMonth-$month-$year"

                    calender = Calendar.getInstance()
                    calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender.set(Calendar.MONTH,monthOfYear)
                    calender.set(Calendar.YEAR,year)
                    val dt1 = calender.timeInMillis
                    cal_milisec=dt1

                }, y, m, d)

            datepickerdialog.show()
        }

        v.timetext_id.setOnClickListener {
            showTimepicker()
        }
        v.syslicPick_id.setOnValueChangedListener { numberPicker, i, i2 ->
            val1 = i2
            // v.bpResult_id.text= "$i $i2"
        }
        v.diastolicPick_id.setOnValueChangedListener { numberPicker, i, i2 ->
            val2 = i2
            //    v.bpResult_id.text= "$i $i2"
        }
        v.pulsePick_id.setOnValueChangedListener { numberPicker, i, i2 ->
            pulse = i2
        }

        v.resultBtn_id.setOnClickListener {
            result(val1, val2)
            v.bpResult_id.text = result
            v.addRecordBtn.isVisible = true
        }
        v.addRecordBtn.setOnClickListener {
            var databaseHelper = DatabaseHelper(requireContext())

            result(val1, val2)

            v.bpResult_id.text = result
            databaseHelper.bpInsertData(
                name,
                date,
                currentTime,
                val1.toString(),
                val2.toString(),
                pulse,
                result,
                map,
                colorCode,
                chartVal,
                position,
                extrimity,
                cal_milisec
            )
            v.addRecordBtn.isVisible = false
            Toast.makeText(requireContext(), "Record Added", Toast.LENGTH_SHORT).show()
        }

        v.textid.setOnClickListener {

        }
        v.extrimyty_id.setOnClickListener {
            val builder1 = AlertDialog.Builder(requireContext())
            val layoutInflater = LayoutInflater.from(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.bp_extrimity_layout, null)
            builder1.setView(dialogView)
            builder1.setCancelable(true)
            val alert11: AlertDialog = builder1.create()
            alert11.show()

            alert11.ex_confirmBtn_id.setOnClickListener {

                if (alert11.radioBtn_1.isChecked) {
                    extrimity = "Right Arm"
                }
                if (alert11.radioBtn_2.isChecked) {
                    extrimity = "Left Arm"
                }
                if (alert11.radioBtn_3.isChecked) {
                    extrimity = "Right Wrist"
                }
                if (alert11.radioBtn_4.isChecked) {
                    extrimity = "Left Wrist"
                }
                if (alert11.radioBtn_5.isChecked) {
                    extrimity = "Right Ankle"
                }
                if (alert11.radioBtn_6.isChecked) {
                    extrimity = "Left Ankle"
                }
                v.extrimyty_id.text = extrimity
                alert11.dismiss()
            }

        }

        v.position_id.setOnClickListener {
            val builder1 = AlertDialog.Builder(requireContext())
            val layoutInflater = LayoutInflater.from(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.bp_position_picker, null)
            builder1.setView(dialogView)
            builder1.setCancelable(true)
            val alert11: AlertDialog = builder1.create()
            alert11.show()

            alert11.ex_confirmBtn_id.setOnClickListener {

                if (alert11.radioBtn_1.isChecked) {
                    position = "Seated"
                }
                if (alert11.radioBtn_2.isChecked) {
                    position = "Lying"
                }
                if (alert11.radioBtn_3.isChecked) {
                    position = "Semi-recumbent"
                }
                if (alert11.radioBtn_4.isChecked) {
                    position = "Standing"
                }

                v.position_id.text = position
                alert11.dismiss()
            }
        }
        return v
    }

    fun result(val1: Int, val2: Int) {

        map = (val1 + 2 * val2) / 3
        bpMap_id.text = map.toString()

        if (val1 in 70..89 && val2 in 40..59) {
            result = "Low"
            colorCode = 200
        }
        else if (val1 in 90..120 && val2 in 60..80) {
            result = "Normal"
            colorCode = 2
        }

        else if ( val1 in 121..129 || val2  in 70..80  ) {
            result = "Elevated"
            colorCode = 4
        }

        else if (val1 in 130..139 || val2 in 81..89) {
            result = "Hypertension(Stage 1)"
            colorCode = 6
        }

       else if (val1 in 140..179 || val2 in 90..119) {
            result = "Hypertension(Stage 2)"
            colorCode = 8
        }

        else if (val1 >= 180 || val2 >= 120) {
            result = "Hypertension(Stage 3)"
            colorCode = 10
        }
    }

    private fun showTimepicker() {
        val hourOfDay = 0
        val minute = 0
        val is24HourView = false
        var readableTime_2: String

        val _timePickerDialog = TimePickerDialog(
            requireContext(), android.R.style.Theme_Holo_Panel,
            { timePicker, i, i1 -> //*Return values
                if (i == 12 && i1 > 0) {

                    readableTime_2 = String.format("%02d", i) + ":" + String.format(
                        "%02d",
                        i1
                    ) + " PM"
                    timetext_id.setText(readableTime_2)


                } else if (i == 0) {
                    readableTime_2 =
                        String.format("%02d", i + 12) + ":" + String.format(
                            "%02d",
                            i1
                        ) + " AM"
                    timetext_id.setText(readableTime_2)
                } else if (i > 12) {
                    readableTime_2 =
                        String.format("%02d", i - 12) + ":" + String.format(
                            "%02d",
                            i1
                        ) + " PM"
                    timetext_id.setText(readableTime_2)
                } else {
                    readableTime_2 = String.format("%02d", i) + ":" + String.format(
                        "%02d",
                        i1
                    ) + " AM"
                    timetext_id.setText(readableTime_2)
                }
                currentTime = readableTime_2
                Toast.makeText(requireContext(), "i=$i i1=$i1", Toast.LENGTH_SHORT).show()
            }, hourOfDay, minute, is24HourView
        )
        _timePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _timePickerDialog.setTitle("Select a Time")
        _timePickerDialog.show()


    }


}