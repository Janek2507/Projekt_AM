package com.example.projekt_am

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.projekt_am.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostId = resources.getIdentifier("nav_host_fragment_content_main", "id", packageName)
        if (navHostId != 0) {
            val navController = findNavController(navHostId)
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuId = resources.getIdentifier("menu_main", "menu", packageName)
        if (menuId != 0) {
            menuInflater.inflate(menuId, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val settingsId = resources.getIdentifier("action_settings", "id", packageName)
        return when (item.itemId) {
            settingsId -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostId = resources.getIdentifier("nav_host_fragment_content_main", "id", packageName)
        if (navHostId != 0) {
            val navController = findNavController(navHostId)
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
        return super.onSupportNavigateUp()
    }
}
