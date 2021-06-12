package tw.edu.nptu.bbox

data class BottleColor(val top: String, val body: String,val waterTop: String, val waterBody: String)

object BottleColors{
    val yello = BottleColor("#fff7e0", "#fff2cc", "#f6da84", "#f1c232")
    val purple = BottleColor("#e8e4f1", "#d9d2e9", "#bbb0db", "#8e7cc3")
    val red = BottleColor("#f2e3ea", "#ead1dc", "#daafc6", "#c27ba0")
    val white = BottleColor("#e0e0e0", "#cccccc", "#f5f5f5", "#efefef")
    val blue = BottleColor("#dfe9fb", "#c9daf8", "#a7c5f3", "#6d9eeb")
}

data class BottleModel(val id: String, val name: String, val color: BottleColor, val percentLeft: Float, val msg: String)
