package tw.edu.nptu.bbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottleView = findViewById<BottleView>(R.id.bottle_view)
        bottleView.setLevel(0.55f)


    }
}