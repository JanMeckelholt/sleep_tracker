package de.janmeckelholt.sleep_tracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import de.janmeckelholt.sleep_tracker.database.SleepNight

@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?){
    item?.let {
        text = convertDurationToFormatted(it.startTimeMilli, it.endTimeMilli, context.resources)
    }
}

@BindingAdapter("sleepQualityText")
fun TextView.setSleepQualityText(item: SleepNight?){
    item?.let {
        text = convertNumericQualityToString(it.sleepQuality, context.resources)
    }
}

@BindingAdapter("sleepQualityImage")
fun ImageView.setSleepQualityImage(item: SleepNight?){
    item?.let {
        setImageResource(when (it.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
}