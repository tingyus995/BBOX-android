package tw.edu.nptu.bbox

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import androidx.constraintlayout.widget.ConstraintLayout

class BottleView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var body: View
    private lateinit var waterTop: View
    private val interpolator = AccelerateDecelerateInterpolator()

    private var duration = 1500L

    init {
        View.inflate(context, R.layout.bottle_layout, this)
        body = findViewById(R.id.water_body)
        waterTop = findViewById(R.id.water_top)
    }

    fun setLevel(level: Float) {
        // scale water body
        val scaleAnimation = ScaleAnimation(
            1.0f, 1.0f,
            0.0f, level,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 1.0f
        )

        scaleAnimation.duration = duration
        scaleAnimation.fillAfter = true
        scaleAnimation.interpolator = interpolator
        body.startAnimation(scaleAnimation)

        // translate water top
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.9f,
            Animation.RELATIVE_TO_PARENT, (0.9f - level * 0.9f)
        )

        translateAnimation.duration = duration
        translateAnimation.fillAfter = true
        translateAnimation.interpolator = interpolator
        waterTop.startAnimation(translateAnimation)

    }
}