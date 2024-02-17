package de.janmeckelholt.sleep_tracker.sleepquality

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.janmeckelholt.sleep_tracker.R
import de.janmeckelholt.sleep_tracker.database.SleepDatabase
import de.janmeckelholt.sleep_tracker.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {

    private lateinit var viewModel: SleepQualityViewModel
    private val args: SleepQualityFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(dataSource, args.sleepNightKey)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SleepQualityViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(R.id.action_sleepQualityFragment_to_sleepTrackerFragment)
                viewModel.doneNavigating()
            }
        })
        return binding.root
    }


}