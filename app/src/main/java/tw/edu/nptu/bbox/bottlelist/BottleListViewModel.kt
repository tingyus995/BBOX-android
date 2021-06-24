package tw.edu.nptu.bbox.bottlelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tw.edu.nptu.bbox.BottleColors
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

                        val records = data?.get("history") as List<*>
                        val percent_left = ((records.last() as Map<*, *>)["percent_left"] as Number).toFloat()

                        val msg = StringBuffer()

                        if(percent_left <= 0.3){
                            msg.append("Please add more juice.")
                        }else{
                            msg.append("Until 4:30 p.m.")
                        }

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
                            percent_left,
                            msg.toString()
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