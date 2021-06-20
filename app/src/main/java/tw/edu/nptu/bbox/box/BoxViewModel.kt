package tw.edu.nptu.bbox.box

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BoxViewModel : ViewModel() {
    private val _opened = MutableLiveData<Boolean>()
    val opened: LiveData<Boolean>
    get() = _opened

    private val _temperature = MutableLiveData<Double>()
    val temperature: LiveData<Double>
    get() = _temperature

    private val _humidity = MutableLiveData<Double>()
    val humidity: LiveData<Double>
    get() = _humidity

    init {
        val db = Firebase.firestore

        db.collection("container")
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val data = document.data

                        if (data != null) {
                            val newTemperature = (data["temperature"] as Number).toDouble()
                            val newOpened = data["opened"] as Boolean
                            val newHumidity = (data["humidity"] as Number).toDouble()

                            if(newTemperature != temperature.value){
                                _temperature.value = newTemperature
                            }

                            if(newOpened != opened.value){
                                _opened.value = newOpened
                            }

                            if(newHumidity != humidity.value){
                                _humidity.value = newHumidity
                            }

                            Log.d("DEBUG", "${humidity.value}")
                        }

                        Log.d("DEBUG", "${document.id} => ${document.data}")

                    }
                }
            }
    }
}