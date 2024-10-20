package com.github.traggo

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.github.traggo.fragment.SettingsFragment
import com.github.traggo.fragment.WebViewFragment
import com.google.android.material.navigation.NavigationView

class MainActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)

        val toggle =
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close,
            )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        displayScreen(R.id.nav_home)
    }

    @Deprecated("")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean =
//        when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }

    fun displayScreen(id: Int) {
        when (id) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.relativelayout, WebViewFragment()).commit()
            }

            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.relativelayout, SettingsFragment()).commit()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        displayScreen(item.itemId)

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
