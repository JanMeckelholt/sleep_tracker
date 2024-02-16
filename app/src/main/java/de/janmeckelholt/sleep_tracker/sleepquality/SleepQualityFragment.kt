package de.janmeckelholt.sleep_tracker.sleepquality

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.janmeckelholt.sleep_tracker.R
import de.janmeckelholt.sleep_tracker.databinding.FragmentSleepQualityBinding

class SleepQualityFragment : Fragment() {

    private lateinit var viewModel: SleepQualityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SleepQualityViewModel::class.java)
        // TODO: Use the ViewModel
    }

}