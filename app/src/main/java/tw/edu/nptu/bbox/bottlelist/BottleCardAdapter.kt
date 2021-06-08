package tw.edu.nptu.bbox.bottlelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tw.edu.nptu.bbox.BottleModel
import tw.edu.nptu.bbox.databinding.BottleCardBinding

class BottleCardAdapter(val clickListener: BottleCardClickListener): ListAdapter<BottleModel, BottleCardAdapter.BottleCardViewHolder>(
    BottleModelDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottleCardViewHolder {
        return BottleCardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BottleCardViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model, clickListener)
    }

    class BottleCardViewHolder(val binding : BottleCardBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model: BottleModel, clickListener: BottleCardClickListener){
            binding.bottle = model
            binding.bottleView.setColor(model.color)
            binding.bottleView.setLevel(model.percentLeft)
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): BottleCardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BottleCardBinding.inflate(layoutInflater, parent, false)
                return BottleCardViewHolder(binding)
            }
        }
    }
}

class BottleModelDiffCallback: DiffUtil.ItemCallback<BottleModel>(){
    override fun areItemsTheSame(oldItem: BottleModel, newItem: BottleModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BottleModel, newItem: BottleModel): Boolean {
        return oldItem == newItem
    }
}

class BottleCardClickListener(val clickListener: (bottleId: Int) -> Unit){
    fun onClick(model: BottleModel) = clickListener(model.id)
}

@BindingAdapter("bottlePercent")
fun setBottlePercentLeft(view: TextView, num: Float){
    view.setText(Math.round(num * 100).toString())
}