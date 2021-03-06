package tw.edu.nptu.bbox.box

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.nptu.bbox.R
import tw.edu.nptu.bbox.databinding.BoxFragmentBinding
import tw.edu.nptu.bbox.databinding.FragmentBottleListBinding
import kotlin.math.roundToInt

class BoxFragment : Fragment() {

    private lateinit var binding: BoxFragmentBinding
    private lateinit var boxViewModel: BoxViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = BoxFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        // Get ViewModel
        boxViewModel = ViewModelProvider(this).get(BoxViewModel::class.java)
        // bind BoxViewModel to view
        binding.viewModel = boxViewModel

        // animation drop down menu
        val animationItems = listOf("Clockwise", "Counter-clockwise", "Breath", "Firefly", "Chase")
        val animationItemsAdapter = ArrayAdapter(requireContext(), R.layout.list_item, animationItems)

        (binding.animationMenu.editText as AutoCompleteTextView).apply {
            setAdapter(animationItemsAdapter)
            setOnItemClickListener { parent, view, position, id ->
                boxViewModel.animationChanged(animationItems[position])
            }
        }

        boxViewModel.animation.observe(viewLifecycleOwner, Observer { animation ->
            (binding.animationMenu.editText as AutoCompleteTextView).setText(animation, false)
        })

        // Initial animation drop down menu
        val initialAnimationItems = listOf("Thunder", "Fade")
        val initialAnimationItemsAdapter = ArrayAdapter(requireContext(), R.layout.list_item, initialAnimationItems)

        (binding.initialAnimationMenu.editText as AutoCompleteTextView).apply {
            setAdapter(initialAnimationItemsAdapter)
            setOnItemClickListener { parent, view, position, id ->
                boxViewModel.initialAnimationChanged(initialAnimationItems[position])
            }
        }

        boxViewModel.initialAnimation.observe(viewLifecycleOwner, Observer { animation ->
            (binding.initialAnimationMenu.editText as AutoCompleteTextView).setText(animation, false)
        })

        boxViewModel.opened.observe(viewLifecycleOwner, Observer { opened ->
            if(opened){
                openDoor()
            }else{
                closeDoor()
            }
        })



        return binding.root
    }

    private fun openDoor(){
        binding.boxView.contentDescription = "The door of the box is opened."
        binding.boxView.setImageDrawable(context?.let { getDrawable(it, R.drawable.box_close_to_open) })
        (binding.boxView.drawable as AnimatedVectorDrawable).start()
    }

    private fun closeDoor(){
        binding.boxView.contentDescription = "The door of the box is closed."
        binding.boxView.setImageDrawable(context?.let { getDrawable(it, R.drawable.box_open_to_close) })
        (binding.boxView.drawable as AnimatedVectorDrawable).start()
    }
}

@BindingAdapter("percent")
fun setPercent(view: TextView, num: Double){
    view.text = (num * 100).roundToInt().toString()
}