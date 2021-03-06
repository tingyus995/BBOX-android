package tw.edu.nptu.bbox.bottledetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.navArgument
import com.github.mikephil.charting.data.Entry
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tw.edu.nptu.bbox.BottleColors
import tw.edu.nptu.bbox.BottleDataUtils
import tw.edu.nptu.bbox.BottleModel

class BottleDetailViewModel(val bottleId: String) : ViewModel() {

    val db = Firebase.firestore

    private val _bottle = MutableLiveData<BottleModel>()
    val bottle: LiveData<BottleModel>
        get() = _bottle

    private val _history = MutableLiveData<List<Entry>>()
    val history: LiveData<List<Entry>>
        get() = _history

    private val _baseTime = MutableLiveData<Long>()
    val baseTime: LiveData<Long>
        get() = _baseTime

    //private var first = true

    init {

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
                    // history
                    val entries = mutableListOf<Entry>()
                    var base = 0L
                    var first = true
                    var records = data["history"] as List<*>
                    for (record in records) {
                        val data = record as Map<*, *>
                        Log.d("DEBUG", "" + data)
                        val time = data?.get("time") as Timestamp
                        val seconds = time.seconds
                        val percent_left = (data.get("percent_left") as Number).toFloat()

                        if (first) {
                            entries.add(Entry(0f, percent_left))
                            base = seconds
                            first = false

                        } else {
                            entries.add(Entry((seconds - base).toFloat(), percent_left))
                        }

                    }
                    _baseTime.value = base
                    _history.value = entries
                    Log.d("DEBUG", "${document.id} => ${document.data}")
                }
            }
    }

    fun updateBottleName(name: String){
        db.collection("bottles").document(bottleId).update(mapOf(
            "name" to name
        ))
    }
}