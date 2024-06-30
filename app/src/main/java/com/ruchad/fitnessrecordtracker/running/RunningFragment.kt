package com.ruchad.fitnessrecordtracker.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.ruchad.fitnessrecordtracker.RUNNING
import com.ruchad.fitnessrecordtracker.databinding.FragmentRunningBinding
import com.ruchad.fitnessrecordtracker.editRecord.EditRec
import com.ruchad.fitnessrecordtracker.editRecord.INTENT_EXTRA_SCREEN_DATA

class RunningFragment : Fragment() {
    private lateinit var binding : FragmentRunningBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListener()
    }

    override fun onResume() {
        super.onResume()
        displayRecord()
    }

    private fun setUpClickListener(){
        binding.container5km.setOnClickListener{ launchRunningRecScreen("5km")}
        binding.container10km.setOnClickListener{ launchRunningRecScreen("10km")}
        binding.containerHalfMarathon.setOnClickListener{ launchRunningRecScreen("Half-Marathon")}
        binding.containerFullMarathon.setOnClickListener{ launchRunningRecScreen("Marathon")}
    }



    private fun displayRecord() {
        val runningPreferences = this.requireContext().getSharedPreferences(RUNNING, Context.MODE_PRIVATE)
        binding.textView5kmValue.text = runningPreferences.getString("5Km Record", null)
        binding.textView5kmDate.text = runningPreferences.getString("5Km Date", null)
        binding.textView10kmValue.text = runningPreferences.getString("10Km Record", null)
        binding.textView10kmDate.text = runningPreferences.getString("10Km Date", null)
        binding.textViewHalfMarathonValue.text = runningPreferences.getString("Half Marathon Record", null)
        binding.textViewHalfMarathonDate.text = runningPreferences.getString("Half Marathon Date", null)
        binding.textViewMarathonValue.text = runningPreferences.getString("Marathon Record", null)
        binding.textViewMarathonDate.text = runningPreferences.getString("Marathon Date", null)

    }




    private fun launchRunningRecScreen(distance : String) {

        val intent = Intent(context, EditRec::class.java)
        intent.putExtra(INTENT_EXTRA_SCREEN_DATA, EditRec.ScreenData(distance, RUNNING, "Time"))
        startActivity(intent)
    }


}