package tw.edu.nptu.bbox.bottlelist

import android.graphics.Color
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
        val warningText = Color.parseColor("#eb3b5a")
        val normalText = Color.parseColor("#757575")

        fun bind(model: BottleModel, clickListener: BottleCardClickListener){
            binding.bottle = model
            if(model.percentLeft <= 0.3){
                binding.percentLeftSymbol.setTextColor(warningText)
                binding.percentLeft.setTextColor(warningText)
            }else{
                binding.percentLeftSymbol.setTextColor(normalText)
                binding.percentLeft.setTextColor(normalText)
            }
            binding.bottleView.setColor(model.color)
            binding.bottleView.setLevel(model.percentLeft)
            binding.clickListener = clickListener
            binding.mainLayout.setOnClickListener {
                clickListener.onClick(binding, model)
            }
            binding.executePendingBindings()
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

class BottleCardClickListener(val clickListener: (binding: BottleCardBinding, bottleId: String) -> Unit){
    fun onClick(binding: BottleCardBinding, model: BottleModel) = clickListener(binding, model.id)
}

@BindingAdapter("bottlePercent")
fun setBottlePercentLeft(view: TextView, num: Float){
    view.setText(Math.round(num * 100).toString())
}