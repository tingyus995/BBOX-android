package tw.edu.nptu.bbox.bottledetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BottleDetailViewModelFactory(val bottleId: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottleDetailViewModel::class.java)) {
            return BottleDetailViewModel(bottleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
