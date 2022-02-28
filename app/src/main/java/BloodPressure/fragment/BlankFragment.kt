package BloodPressure.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import com.example.randomuser.R
import io.github.farshidroohi.ChartEntity
import io.github.farshidroohi.LineChart


class BlankFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val v= inflater.inflate(R.layout.fragment_blank, container, false)
        val firstChartEntity = ChartEntity(Color.RED, firstEntity)
        val secondChartEntity = ChartEntity(Color.YELLOW, secondtEntity)

        val list = ArrayList<ChartEntity>().apply {
            add(firstChartEntity)
            add(secondChartEntity)
        }

        val lineChart = v.findViewById<LineChart>(R.id.lineChart)
        lineChart.setLegend(legendArr)
        lineChart.setList(list)

        return v
    }
    private val firstEntity = floatArrayOf(113000f, 183000f, 188000f, 695000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000f)
    private val secondtEntity = floatArrayOf(0f, 245000f, 1011000f, 1000f, 0f, 0f, 47000f, 20000f, 12000f, 124400f, 160000f)
    private val legendArr = listOf("05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31")

}