package com.ruchad.fitnessrecordtracker.editRecord



import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.edit
import com.ruchad.fitnessrecordtracker.databinding.ActivityEditRecBinding
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"

class EditRec : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecBinding
    private val screenData : ScreenData by lazy{
        intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA) as ScreenData
    }

    private val recordPreferences by lazy {getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
        displayRecord()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUi() {
        title = "${screenData.record} Record"
        binding.editTextRec.hint = screenData.recordFieldHint
        binding.buttonSave.setOnClickListener {
            saveRecord()
            finish()
        }
        binding.buttonDel.setOnClickListener {
            clearRecord()
            finish()
        }
    }


    private fun displayRecord() {

        binding.editTextRec.setText(recordPreferences.getString("$screenData.record record", null))
        binding.editTextDate.setText(recordPreferences.getString("$screenData.record date", null))

    }

    override fun onResume() {
        super.onResume()
        displayRecord()
    }

    private fun saveRecord() {
        val record = binding.editTextRec.text.toString()
        val date = binding.editTextDate.text.toString()

        recordPreferences.edit{
            putString("${this@EditRec.screenData.record} record", record)
            putString("${this@EditRec.screenData.record} date", date)
        }
    }

    private fun clearRecord() {
        recordPreferences.edit {
            remove("$screenData.record record")
            remove("$screenData.record date")
        }
    }

    data class ScreenData(
        val record : String ,
        val sharedPreferencesName : String,
        val recordFieldHint : String,
    ) : Serializable

}