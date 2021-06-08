package tw.edu.nptu.bbox.bottlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.edu.nptu.bbox.BottleColors
import tw.edu.nptu.bbox.BottleModel

class BottleListViewModel() : ViewModel(){

    private val _bottles = MutableLiveData<List<BottleModel>>()
    val bottles: LiveData<List<BottleModel>>
    get() = _bottles

    private val _navigateToBottleDetail = MutableLiveData<Int?>()
    val navigateToBottleDetail: LiveData<Int?>
    get() = _navigateToBottleDetail


    init {
        _bottles.value = listOf(
            BottleModel(0,"Blueberry", BottleColors.blue, 0.35f, "Until 3:50 p.m."),
            BottleModel(1,"Apple Juice", BottleColors.red, 0.55f, "Until 4:50 p.m."),
            BottleModel(2,"Milk", BottleColors.white, 0.82f, "Until 7:30 p.m."),
            BottleModel(3,"Orange juice", BottleColors.yello, 0.12f, "Please add more juice.")
        )
    }

    fun onBottleCardClicked(id: Int){
        _navigateToBottleDetail.value = id
    }

    fun doneNavigatingToBottleDetail(){
        _navigateToBottleDetail.value = null
    }



}