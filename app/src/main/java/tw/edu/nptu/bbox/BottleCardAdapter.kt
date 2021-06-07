package tw.edu.nptu.bbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.edu.nptu.bbox.databinding.BottleCardBinding

class BottleCardAdapter: RecyclerView.Adapter<BottleCardAdapter.BottleCardViewHolder>() {
    var data = listOf<BottleModel>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottleCardViewHolder {
        return BottleCardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BottleCardViewHolder, position: Int) {
        val model = data[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class BottleCardViewHolder(val binding : BottleCardBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model: BottleModel){
            binding.bottleView.setColor(model.color)
            binding.bottleView.setLevel(model.percentLeft)
            binding.percentLeft.text = Math.round(model.percentLeft * 100).toString()
            binding.name.text = model.name
            binding.msg.text = model.msg

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

