package BloodPressure

import BloodPressure.fragment.bpHistoryFragment
import Database.DataModel
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuser.R
import java.util.ArrayList

class BpAdapter(var context: Context, var rdata: ArrayList<DataModel>,val fragment: bpHistoryFragment) :
    RecyclerView.Adapter<BpAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var viewName: TextView = itemView.findViewById(R.id.textView_title)
        var viewDate: TextView = itemView.findViewById(R.id.textView_date)
        var viewTime: TextView = itemView.findViewById(R.id.textView_Time)
        var viewSys: TextView = itemView.findViewById(R.id.textView_sys)
        var viewDias: TextView = itemView.findViewById(R.id.textView_Dias)
        var viewPulse: TextView = itemView.findViewById(R.id.textView_pulse)
        var viewResult: TextView = itemView.findViewById(R.id.textView_result)
        var viewMap: TextView = itemView.findViewById(R.id.textView_map)
        var viewPosition: TextView = itemView.findViewById(R.id.textView_pos)
        var viewExtrimity: TextView = itemView.findViewById(R.id.textView_extry)
        var cardBack: CardView = itemView.findViewById(R.id.card_bp_item)
        var deleteButton: ImageView = itemView.findViewById(R.id.bpDelete_button_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.bp_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userPosition = rdata[position]

//        val id = userPosition.bpId!!
        val name = userPosition.bpName
        val date = userPosition.bpDate
        val time = userPosition.bpTime
        val systolic = userPosition.bpSys
        val diastolic = userPosition.bpDias
        val pulse = userPosition.bpPulse
        val result = userPosition.bpResult
        val map = userPosition.bpMap
        var colorCode = userPosition.bpColor
        val chartVal = userPosition.bpChart
        val position = userPosition.bpPosition
        val extrimity = userPosition.bpExtrimity

        if (colorCode == 2) {
           // holder.cardBack.setBackgroundColor(Color.parseColor("#014C04"))
            //holder.viewResult.setBackgroundColor(Color.parseColor("#014C04"))
            holder.viewResult.setTextColor(Color.parseColor("#014C04"))

        } else if (colorCode == 4) {
           // holder.cardBack.setBackgroundColor(Color.parseColor("#FFFF9800"))
           // holder.viewResult.setBackgroundColor(Color.parseColor("#FFFF9800"))
            holder.viewResult.setTextColor(Color.parseColor("#FFFF9800"))
        } else if (colorCode == 6) {
          //  holder.cardBack.setBackgroundColor(Color.parseColor("#FF5722"))
           // holder.viewResult.setBackgroundColor(Color.parseColor("#FF5722"))
            holder.viewResult.setTextColor(Color.parseColor("#FF5722"))
        }else if (colorCode == 8) {
           // holder.cardBack.setBackgroundColor(Color.parseColor("#EA2C1E"))
            //holder.viewResult.setBackgroundColor(Color.parseColor("#EA2C1E"))
            holder.viewResult.setTextColor(Color.parseColor("#EA2C1E"))
        }else if (colorCode == 200) {
           // holder.cardBack.setBackgroundColor(Color.parseColor("#00BCD4"))
            //holder.viewResult.setBackgroundColor(Color.parseColor("#00BCD4"))
            holder.viewResult.setTextColor(Color.parseColor("#00BCD4"))
        }else if (colorCode == 10) {
           // holder.cardBack.setBackgroundColor(Color.parseColor("#830202"))
           // holder.viewResult.setBackgroundColor(Color.parseColor("#830202"))
            holder.viewResult.setTextColor(Color.parseColor("#830202"))
        }

        // holder.viewName.text = name
        holder.viewDate.text = date
        holder.viewTime.text = time
        holder.viewSys.text = systolic
        holder.viewDias.text = diastolic
        holder.viewPulse.text = pulse.toString()
        holder.viewMap.text = map.toString()
        holder.viewResult.text = result
        holder.viewPosition.text = position
        holder.viewExtrimity.text = extrimity

        //  holder.viewTime.text = time

        holder.itemView.setOnClickListener {
            //  ctx.passHistoryDetails(firstName,userGender,userMail,imgData,userlocation,userphone,userAge)

        }

        holder.deleteButton.setOnClickListener {
            val builder1 = AlertDialog.Builder(context)
            builder1.setMessage(Html.fromHtml("<font color='#3555d3'>Do you want to delete?</font>"))
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()

                    val id = userPosition.bpId!!
                        fragment.deleteItem(id)
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
    }

    override fun getItemCount(): Int {
        return rdata.size
    }
}