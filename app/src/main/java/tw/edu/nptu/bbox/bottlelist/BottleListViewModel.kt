package tw.edu.nptu.bbox.bottlelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tw.edu.nptu.bbox.BottleColor
import tw.edu.nptu.bbox.BottleColors
import tw.edu.nptu.bbox.BottleDataUtils
import tw.edu.nptu.bbox.BottleModel

class BottleListViewModel() : ViewModel(){

    private val _bottles = MutableLiveData<List<BottleModel>>()
    val bottles: LiveData<List<BottleModel>>
    get() = _bottles

    private val _navigateToBottleDetail = MutableLiveData<String?>()
    val navigateToBottleDetail: LiveData<String?>
    get() = _navigateToBottleDetail


    init {

        val db = Firebase.firestore

        db.collection("bottles")
            .addSnapshotListener{snapshot, e ->
                val newBottles = mutableListOf<BottleModel>()
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val data = document.data
                        newBottles.add(BottleModel(
                            document.id,
                            data?.get("name") as String,
                            when(data["color"] as String){
                                "red" -> BottleColors.red
                                "yello" -> BottleColors.yello
                                "blue" -> BottleColors.blue
                                "white" -> BottleColors.white
                                "purple" -> BottleColors.purple
                                else -> BottleColors.white
                            },
                            (data["percent_left"] as Number).toFloat(),
                            data["msg"] as String
                        ))
                        Log.d("DEBUG", "${document.id} => ${document.data}")

                    }
                }
                _bottles.value = newBottles
            }

    }

    fun onBottleCardClicked(id: String){
        _navigateToBottleDetail.value = id
    }

    fun doneNavigatingToBottleDetail(){
        _navigateToBottleDetail.value = null
    }
}