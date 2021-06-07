package tw.edu.nptu.bbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.*
import androidx.constraintlayout.widget.ConstraintLayout
import tw.edu.nptu.bbox.databinding.BottleLayoutBinding

class BottleView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding: BottleLayoutBinding =
        BottleLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private val interpolator = AccelerateDecelerateInterpolator()

    private var duration = 1500L

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
        binding.waterBody.startAnimation(scaleAnimation)


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
        binding.waterTop.startAnimation(translateAnimation)

    }
}