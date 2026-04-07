package com.example.projekt_am

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun getResId(resName: String, resType: String): Int {
        return resources.getIdentifier(resName, resType, packageName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutId = getResId("activity_main", "layout")
        if (layoutId != 0) {
            setContentView(layoutId)
        }

        val toolbarId = getResId("toolbar", "id")
        if (toolbarId != 0) {
            val toolbar = findViewById<Toolbar>(toolbarId)
            setSupportActionBar(toolbar)
        }

        val navHostId = getResId("nav_host_fragment_content_main", "id")
        if (navHostId != 0) {
            val navController = findNavController(navHostId)
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuId = getResId("menu_main", "menu")
        if (menuId != 0) {
            menuInflater.inflate(menuId, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val settingsId = getResId("action_settings", "id")
        return when (item.itemId) {
            settingsId -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostId = getResId("nav_host_fragment_content_main", "id")
        if (navHostId != 0) {
            val navController = findNavController(navHostId)
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
        return super.onSupportNavigateUp()
    }
}
