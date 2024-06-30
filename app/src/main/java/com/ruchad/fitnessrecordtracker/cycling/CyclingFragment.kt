package com.ruchad.fitnessrecordtracker.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ruchad.fitnessrecordtracker.CYCLING
import com.ruchad.fitnessrecordtracker.databinding.FragmentCyclingBinding
import com.ruchad.fitnessrecordtracker.editRecord.EditRec
import com.ruchad.fitnessrecordtracker.editRecord.INTENT_EXTRA_SCREEN_DATA


class CyclingFragment : Fragment() {

    private lateinit var binding : FragmentCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCyclingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val cyclingPreferences = this.requireContext().getSharedPreferences(CYCLING, Context.MODE_PRIVATE)

        binding.textViewLongestRideValue.text = cyclingPreferences.getString("Longest Ride Record", null)
        binding.textViewLongestRideDate.text = cyclingPreferences.getString("Longest Ride Date", null)
        binding.textViewBiggestClimbValue.text = cyclingPreferences.getString("Biggest Climb Record", null)
        binding.textViewBiggestClimbDate.text = cyclingPreferences.getString("Biggest Climb Date", null)
        binding.textViewHalfBestAvgSpeedValue.text = cyclingPreferences.getString("Average Speed Record", null)
        binding.textViewAvgSpeedDate.text = cyclingPreferences.getString("Average Speed Date", null)
    }

    private fun setUpListener() {
        binding.containerLongestRide.setOnClickListener{launchCyclingScreen("Longest Ride", "Distance")}
        binding.containerBestAvgSpeed.setOnClickListener{launchCyclingScreen("Biggest Climb", "Average Speed")}
        binding.containerBiggestClimb.setOnClickListener{launchCyclingScreen("Best Average Speed", "Height")}
    }

    private fun launchCyclingScreen(record:String, recordFieldHint : String) {
        val intent = Intent(context, EditRec::class.java)
        intent.putExtra(INTENT_EXTRA_SCREEN_DATA, EditRec.ScreenData(record, CYCLING, recordFieldHint))
        startActivity(intent)

    }
}