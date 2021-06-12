package tw.edu.nptu.bbox.bottledetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import tw.edu.nptu.bbox.R
import tw.edu.nptu.bbox.databinding.FragmentBottleDetailBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class BottleDetailFragment : Fragment() {

    private lateinit var binding: FragmentBottleDetailBinding
    private lateinit var viewModel: BottleDetailViewModel
    private lateinit var viewModelFactory: BottleDetailViewModelFactory
    private val axisFormatter = DateTimeAxisFormatter(0L)

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

        viewModel.history.observe(viewLifecycleOwner, Observer { entries ->
            val dataset = LineDataSet(entries, "data")
            dataset.circleRadius = 10f
            dataset.circleHoleRadius = 7f
            dataset.circleColors = listOf(context?.let { getColor(it, R.color.purple_200) })
            dataset.lineWidth = 3f
            //dataset.isHighlightEnabled = false
            dataset.color = context?.let { getColor(it, R.color.purple_200) }!!
            val linedata = LineData(dataset)
            binding.chart.data = linedata
            binding.chart.notifyDataSetChanged()
            binding.chart.invalidate()

            Log.d("DEBUG",entries.toString())
            highlightAndSelectLast(entries)

        })

        binding.chart.xAxis.valueFormatter = axisFormatter

        viewModel.baseTime.observe(viewLifecycleOwner, Observer { baseTime ->
            axisFormatter.baseTime = baseTime
            binding.chart.invalidate()
        })

        val marker = SelectedMarker(context!!)
        binding.chart.marker = marker
        binding.chart.axisLeft.axisMaximum = 1.0f
        binding.chart.axisLeft.axisMinimum = 0.0f

        binding.chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null) {
                    applyBottleInfo(e)
                }
            }

            override fun onNothingSelected() {
                val history = viewModel.history.value
                if(history != null){
                    highlightAndSelectLast(history)
                }
            }
        })

        return binding.root
    }

    fun applyBottleInfo(e: Entry){
        val percentLeft = e.y
        val timeDelta = e.x
        val date = axisFormatter.getDate(timeDelta)

        binding.bottleView.setLevel(percentLeft)
        binding.percentLeft.text = (percentLeft * 100).roundToInt().toString()
        binding.timeText.text =  SimpleDateFormat("HH:mm").format(date)
        binding.dateText.text = SimpleDateFormat("MM/dd").format(date)
    }

    fun highlightAndSelectLast(entries: List<Entry>){
        if(entries.isNotEmpty()){
            val last = entries.last()
            applyBottleInfo(last)
            val highlight = Highlight(last.x, last.y, 0)
            highlight.dataIndex = entries.size - 1
            binding.chart.highlightValue(highlight)
        }
    }
}