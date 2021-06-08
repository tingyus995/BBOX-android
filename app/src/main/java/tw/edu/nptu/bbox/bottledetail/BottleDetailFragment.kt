package tw.edu.nptu.bbox.bottledetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import tw.edu.nptu.bbox.R
import tw.edu.nptu.bbox.databinding.FragmentBottleDetailBinding

class BottleDetailFragment : Fragment() {

    private lateinit var binding: FragmentBottleDetailBinding
    private lateinit var viewModel: BottleDetailViewModel
    private lateinit var viewModelFactory: BottleDetailViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottleDetailBinding.inflate(inflater, container, false)

        val args by navArgs<BottleDetailFragmentArgs>()
        viewModelFactory = BottleDetailViewModelFactory(args.bottleId)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(BottleDetailViewModel::class.java)

        viewModel.bottle.observe(viewLifecycleOwner, Observer { model ->
            binding.bottle = model

            binding.bottleView.setColor(model.color)
            binding.bottleView.setLevel(model.percentLeft, false)
            binding.executePendingBindings()
            startPostponedEnterTransition()
        })



        return binding.root
    }
}