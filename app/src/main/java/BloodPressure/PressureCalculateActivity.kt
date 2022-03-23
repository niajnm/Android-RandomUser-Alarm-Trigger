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
                    val fragment = bpHistoryFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }

                R.id.menuAnalysis_id -> {
                    val fragment = BpAnalysisFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
                R.id.menuLineChart_id -> {
                    val fragment = BlankFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
            }
            true
        }
    }


}