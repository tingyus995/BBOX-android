package tw.edu.nptu.bbox.bottlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.edu.nptu.bbox.BottleColors
import tw.edu.nptu.bbox.BottleDataUtils
import tw.edu.nptu.bbox.BottleModel

class BottleListViewModel() : ViewModel(){

    private val _bottles = MutableLiveData<List<BottleModel>>()
    val bottles: LiveData<List<BottleModel>>
    get() = _bottles

    private val _navigateToBottleDetail = MutableLiveData<Int?>()
    val navigateToBottleDetail: LiveData<Int?>
    get() = _navigateToBottleDetail


    init {
        _bottles.value = BottleDataUtils.getAllBottles()
    }

    fun onBottleCardClicked(id: Int){
        _navigateToBottleDetail.value = id
    }

    fun doneNavigatingToBottleDetail(){
        _navigateToBottleDetail.value = null
    }
}