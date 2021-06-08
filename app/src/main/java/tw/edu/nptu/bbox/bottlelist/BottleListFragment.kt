package tw.edu.nptu.bbox.bottlelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottleListBinding.inflate(inflater, container, false)

        // Get ViewModel
        bottleListViewModel = ViewModelProvider(this).get(BottleListViewModel::class.java)

        val adapter = BottleCardAdapter(BottleCardClickListener {
            // Toast.makeText(context, "Clicked: " + it, Toast.LENGTH_LONG).show()
            bottleListViewModel.onBottleCardClicked(it)
        })

        bottleListViewModel.bottles.observe(viewLifecycleOwner, Observer { list -> adapter.submitList(list) })

        bottleListViewModel.navigateToBottleDetail.observe(viewLifecycleOwner, Observer { bottleId ->
            bottleId?.let {
                findNavController().navigate(BottleListFragmentDirections.actionBottleListToBottleDetailFragment(it))
                bottleListViewModel.doneNavigatingToBottleDetail()
            }
        })

        binding.cardRecycleView.adapter = adapter

        return binding.root
    }

}