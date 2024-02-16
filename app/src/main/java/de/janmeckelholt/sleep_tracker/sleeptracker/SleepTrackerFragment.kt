package de.janmeckelholt.sleep_tracker.sleeptracker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.janmeckelholt.sleep_tracker.R
import de.janmeckelholt.sleep_tracker.databinding.FragmentSleepQualityBinding
import de.janmeckelholt.sleep_tracker.databinding.FragmentSleepTrackerBinding

class SleepTrackerFragment : Fragment() {

    private lateinit var viewModel: SleepTrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepTrackerBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SleepTrackerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}