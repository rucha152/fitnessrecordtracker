package com.ruchad.fitnessrecordtracker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.ruchad.fitnessrecordtracker.cycling.CyclingFragment
import com.ruchad.fitnessrecordtracker.databinding.ActivityMainBinding
import com.ruchad.fitnessrecordtracker.running.RunningFragment

const val RUNNING = "Running"
const val CYCLING = "Cycling"
const val ALL = "All"

class MainActivity : AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNav.setOnItemSelectedListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClickHandled = when (item.itemId) {
            R.id.reset_running -> {
                showConfirmationDialog(RUNNING)
                true
            }

            R.id.reset_cycling -> {
                showConfirmationDialog(CYCLING)
                true
            }

            R.id.reset_all_records -> {
                showConfirmationDialog(ALL)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)

            }
        }


        return menuClickHandled
    }

    private fun showConfirmationDialog(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Reset $selection records")
            .setMessage("Are you sure you want to continue? ")
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                when (selection) {
                    "Cycling" -> getSharedPreferences(RUNNING, MODE_PRIVATE).edit { clear() }
                    "Running" -> getSharedPreferences(CYCLING, MODE_PRIVATE).edit { clear() }
                    "All" -> {
                        getSharedPreferences(RUNNING, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CYCLING, MODE_PRIVATE).edit { clear() }
                    }
                }
                when (binding.bottomNav.selectedItemId) {
                    R.id.nav_running -> onRunningClicked()
                    R.id.nav_cycling -> onCyclingClicked()
                }
                val snackbar = Snackbar.make(
                    binding.frameContent,
                    "Records Cleared Successfully!",
                    Snackbar.LENGTH_LONG
                )
                snackbar.anchorView = binding.bottomNav
                snackbar.show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun onRunningClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment())
        }
        return true
    }

    private fun onCyclingClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment())
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_cycling -> {
                onCyclingClicked()

            }

            R.id.nav_running -> {
                onRunningClicked()
            }

            else -> {
                false
            }
        }
    }
}
