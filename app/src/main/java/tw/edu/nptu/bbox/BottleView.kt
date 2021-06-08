package tw.edu.nptu.bbox

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
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

    fun setColor(bottleColor: BottleColor){
        binding.bottleTop.backgroundTintList = ColorStateList.valueOf(Color.parseColor(bottleColor.top))
        binding.bottleBody.backgroundTintList = ColorStateList.valueOf(Color.parseColor(bottleColor.body))
        binding.bottom.backgroundTintList = ColorStateList.valueOf(Color.parseColor(bottleColor.waterBody))
        binding.waterTop.backgroundTintList = ColorStateList.valueOf(Color.parseColor(bottleColor.waterTop))
        binding.waterBody.backgroundTintList = ColorStateList.valueOf(Color.parseColor(bottleColor.waterBody))
    }

    fun setLevel(level: Float, animate: Boolean = true) {
        val ani_duration = if(animate) duration else 0
        // scale water body
        val scaleAnimation = ScaleAnimation(
            1.0f, 1.0f,
            0.0f, level,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 1.0f
        )

        scaleAnimation.duration = ani_duration
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

        translateAnimation.duration = ani_duration
        translateAnimation.fillAfter = true
        translateAnimation.interpolator = interpolator
        binding.waterTop.startAnimation(translateAnimation)
    }
}