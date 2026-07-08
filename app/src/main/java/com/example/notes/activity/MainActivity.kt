package com.example.notes.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.notes.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

                val navController = navHostFragment.navController

                if (navController.currentDestination?.id !=
                    R.id.notesListFragment
                ) {

                    navController.navigateUp()

                } else {

                    MaterialAlertDialogBuilder(this@MainActivity)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Yes") { _, _ ->
                            finish()
                        }
                        .show()
                }

            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}