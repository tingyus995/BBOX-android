package tw.edu.nptu.bbox.bottledetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tw.edu.nptu.bbox.BottleColors
import tw.edu.nptu.bbox.BottleDataUtils
import tw.edu.nptu.bbox.BottleModel

class BottleDetailViewModel(val bottleId: String) : ViewModel() {

    private val _bottle = MutableLiveData<BottleModel>()
    val bottle: LiveData<BottleModel>
        get() = _bottle

    init {
        val db = Firebase.firestore

        db.collection("bottles").document(bottleId)
            .addSnapshotListener { document, e ->
                val data = document?.data
                if (document != null) {
                    _bottle.value = BottleModel(
                        document.id,
                        data?.get("name") as String,
                        when (data["color"] as String) {
                            "red" -> BottleColors.red
                            "yello" -> BottleColors.yello
                            "blue" -> BottleColors.blue
                            "white" -> BottleColors.white
                            "purple" -> BottleColors.purple
                            else -> BottleColors.white
                        },
                        (data["percent_left"] as Number).toFloat(),
                        data["msg"] as String
                    )
                    Log.d("DEBUG", "${document.id} => ${document.data}")
                }
            }

    }


}