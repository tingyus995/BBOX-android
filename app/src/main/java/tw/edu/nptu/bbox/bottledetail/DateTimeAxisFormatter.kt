package tw.edu.nptu.bbox.bottledetail

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class DateTimeAxisFormatter(var baseTime: Long) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val date = getDate(value)
        return SimpleDateFormat("MM/dd HH:mm").format(date)
    }

    fun getDate(value: Float): Date{
        val actualSeconds = (baseTime + value).toLong()
        return Date(actualSeconds * 1000)
    }
}