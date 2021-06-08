package tw.edu.nptu.bbox.bottlelist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import tw.edu.nptu.bbox.BottleColors
import tw.edu.nptu.bbox.BottleModel
import tw.edu.nptu.bbox.databinding.FragmentBottleListBinding

class BottleListFragment : Fragment() {

    private lateinit var binding: FragmentBottleListBinding
    private lateinit var bottleListViewModel: BottleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding = FragmentBottleListBinding.inflate(inflater, container, false)

        // Get ViewModel
        bottleListViewModel = ViewModelProvider(this).get(BottleListViewModel::class.java)

        val adapter = BottleCardAdapter(BottleCardClickListener { binding, bottleId ->
            // Toast.makeText(context, "Clicked: " + it, Toast.LENGTH_LONG).show()

            bottleListViewModel.onBottleCardClicked(bottleId)
            Log.d("DEBUG", "bottle id: $bottleId")
            val extras = FragmentNavigatorExtras(
                binding.bottleView to "bottle_view_$bottleId",
                binding.name to "bottle_name_$bottleId",
                binding.percentLeft to "bottle_percent_$bottleId",
                binding.percentLeftSymbol to "bottle_percent_left_$bottleId"
            )
            findNavController().navigate(BottleListFragmentDirections.actionBottleListToBottleDetailFragment(bottleId), extras)
        })

        bottleListViewModel.bottles.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        })

        binding.cardRecycleView.adapter = adapter

        return binding.root
    }

}