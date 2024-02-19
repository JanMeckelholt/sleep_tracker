package de.janmeckelholt.sleep_tracker.sleeptracker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import de.janmeckelholt.sleep_tracker.R
import de.janmeckelholt.sleep_tracker.database.SleepDatabase
import de.janmeckelholt.sleep_tracker.databinding.FragmentSleepTrackerBinding
import timber.log.Timber

class SleepTrackerFragment : Fragment() {

    private lateinit var viewModel: SleepTrackerViewModel
    private lateinit var binding: FragmentSleepTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.sleepTrackerViewModel = viewModel

        val layoutManager = GridLayoutManager(activity, 3)
        binding.sleepList.layoutManager = layoutManager

        val adapter = SleepNightAdapter(
            SleepNightListener { nightId ->
                viewModel.onSleepNightClicked(nightId)
            })

        viewModel.navigateToSleepDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.i("clicked $it")
                val bundle = Bundle()
                bundle.putLong("sleepNightKey", it)
                this.findNavController()
                    .navigate(R.id.action_sleep_tracker_fragment_to_sleepDetailFragment, bundle)
                viewModel.doneNavigating()
            }
        })

        binding.sleepList.adapter = adapter
        viewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                val bundle = Bundle()
                bundle.putLong("sleepNightKey", it.nightId)
                this.findNavController()
                    .navigate(R.id.action_sleepTrackerFragment_to_sleepQualityFragment, bundle)
                viewModel.doneNavigating()
            }
        })
        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackBar()
            }
        })

        return binding.root
    }

}