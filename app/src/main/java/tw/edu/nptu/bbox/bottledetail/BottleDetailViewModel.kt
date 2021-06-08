package tw.edu.nptu.bbox.bottledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.edu.nptu.bbox.BottleDataUtils
import tw.edu.nptu.bbox.BottleModel

class BottleDetailViewModel(val bottleId: Int): ViewModel() {

    private val _bottle = MutableLiveData<BottleModel>()
    val bottle: LiveData<BottleModel>
    get() = _bottle

    init {
        _bottle.value = BottleDataUtils.getBottle(bottleId)
    }

}