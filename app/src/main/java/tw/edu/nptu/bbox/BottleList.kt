package tw.edu.nptu.bbox

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tw.edu.nptu.bbox.databinding.FragmentBottleListBinding

class BottleList : Fragment() {

    private lateinit var binding: FragmentBottleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottleListBinding.inflate(layoutInflater, container, false)
        val adapter = BottleCardAdapter()

        binding.cardRecycleView.adapter = adapter

        adapter.data = listOf(
            BottleModel("Blueberry", BottleColors.blue, 0.35f, "Until 3:50 p.m."),
            BottleModel("Apple Juice", BottleColors.red, 0.55f, "Until 4:50 p.m."),
            BottleModel("Milk", BottleColors.white, 0.82f, "Until 7:30 p.m."),
            BottleModel("Orange juice", BottleColors.yello, 0.12f, "Please add more juice.")
        )
        return binding.root
    }

}