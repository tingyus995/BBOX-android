package tw.edu.nptu.bbox.bottledetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import tw.edu.nptu.bbox.R
import tw.edu.nptu.bbox.databinding.FragmentBottleDetailBinding

class BottleDetailFragment : Fragment() {

    private lateinit var binding: FragmentBottleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottleDetailBinding.inflate(inflater, container, false)

        val args by navArgs<BottleDetailFragmentArgs>()
        binding.textView.text = args.bottleId.toString()

        return binding.root
    }
}