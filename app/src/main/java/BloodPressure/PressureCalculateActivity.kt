package BloodPressure


import BloodPressure.fragment.BlankFragment
import BloodPressure.fragment.BpAnalysisFragment
import BloodPressure.fragment.BpFragment
import BloodPressure.fragment.bpHistoryFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_pressure_calculate.*

class PressureCalculateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pressure_calculate)

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.menuScale_id -> {

                    val bpfragment = BpFragment()
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, bpfragment)
                    fragmentTransaction.commit()
                }
                R.id.menuBloodHistory_id -> {
                    val bpfragment = bpHistoryFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, bpfragment).commit()
                }

                R.id.menuAnalysis_id -> {
                    val bpfragment = BpAnalysisFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, bpfragment).commit()
                }
                R.id.menuLineChart_id -> {
                    val bpfragment = BlankFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, bpfragment).commit()
                }
            }
            true
        }
    }


}