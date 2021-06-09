package tw.edu.nptu.bbox.bottledetail

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import tw.edu.nptu.bbox.R

class Marker: IMarker {
    override fun getOffset(): MPPointF {
        TODO("Not yet implemented")
    }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        TODO("Not yet implemented")
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
        TODO("Not yet implemented")
    }

}

class SelectedMarker(context: Context)
    : MarkerView(context, R.layout.selected_marker), IMarker{

    private var circle_width = 0
    private var circle_height = 0


    init {
        val container = findViewById<View>(R.id.circle_outer)
        circle_width = container.width
        circle_height = container.height


    }

    override fun getOffset(): MPPointF {
        return MPPointF((-circle_width / 2).toFloat(), (- circle_height / 2).toFloat())
    }
}