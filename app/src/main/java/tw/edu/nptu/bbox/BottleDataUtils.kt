package tw.edu.nptu.bbox

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object BottleDataUtils{


    // Create a new user with a first and last name
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )


    private var bottles: List<BottleModel> = listOf(
        BottleModel("0","Blueberry", BottleColors.blue, 0.35f, "Until 3:50 p.m."),
        BottleModel("1","Apple Juice", BottleColors.red, 0.55f, "Until 4:50 p.m."),
        BottleModel("2","Milk", BottleColors.white, 0.82f, "Until 7:30 p.m."),
        BottleModel("3","Orange juice", BottleColors.yello, 0.12f, "Please add more juice.")
    )

    fun getAllBottles(): List<BottleModel>{
        return bottles
    }

    fun getBottle(id: Int): BottleModel{
        return bottles[id]
    }
}