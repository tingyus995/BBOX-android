package tw.edu.nptu.bbox.box

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BoxViewModel : ViewModel() {

    private val db = Firebase.firestore
    private var docId: String? = null

    private val _opened = MutableLiveData<Boolean>()
    val opened: LiveData<Boolean>
    get() = _opened

    private val _temperature = MutableLiveData<Double>()
    val temperature: LiveData<Double>
    get() = _temperature

    private val _humidity = MutableLiveData<Double>()
    val humidity: LiveData<Double>
    get() = _humidity

    private val _animation = MutableLiveData<String>()
    val animation: LiveData<String>
    get() = _animation

    private val _initialAnimation = MutableLiveData<String>()
    val initialAnimation: LiveData<String>
    get() = _initialAnimation

    init {

        db.collection("container")
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val data = document.data
                        docId = document.id

                        if (data != null) {
                            val newTemperature = (data["temperature"] as Number).toDouble()
                            val newOpened = data["opened"] as Boolean
                            val newHumidity = (data["humidity"] as Number).toDouble()
                            val newAnimation = (data["animation"] as String)
                            val newInitialAnimation = (data["initial_animation"] as String)

                            if(newTemperature != temperature.value){
                                _temperature.value = newTemperature
                            }

                            if(newOpened != opened.value){
                                _opened.value = newOpened
                            }

                            if(newHumidity != humidity.value){
                                _humidity.value = newHumidity
                            }

                            if(newAnimation != animation.value){
                                _animation.value = newAnimation
                            }

                            if(newInitialAnimation != initialAnimation.value){
                                _initialAnimation.value = newInitialAnimation
                            }

                            Log.d("DEBUG", "${humidity.value}")
                        }

                        Log.d("DEBUG", "${document.id} => ${document.data}")

                    }
                }
            }
    }

    fun animationChanged(animation: String){
        Log.d("DEBUG", animation + "selected!")
        val id = docId
        if(id != null){
            db.collection("container").document(id).update(mapOf(
                "animation" to animation
            ))
        }
    }
    fun initialAnimationChanged(animation: String){
        Log.d("DEBUG", animation + "selected!")
        val id = docId
        if(id != null){
            db.collection("container").document(id).update(mapOf(
                "initial_animation" to animation
            ))
        }
    }
}