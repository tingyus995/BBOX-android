package tw.edu.nptu.bbox.bottledetail

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class PercentAxisFormatter: ValueFormatter(){
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return (value * 100).roundToInt().toString() + "%"
    }

}