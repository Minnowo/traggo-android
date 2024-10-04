package com.github.traggo_android;

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.github.traggo_android.fragment.SettingsFragment
import com.github.traggo_android.fragment.WebViewFragment
import com.github.traggo_android.service.TraggoRunnable
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawer_layout:DrawerLayout
    lateinit var nav_view:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(TraggoRunnable(applicationContext)).start()

        toolbar = findViewById(R.id.toolbar)
        drawer_layout=findViewById(R.id.drawer_layout)
        nav_view=findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayScreen(R.id.nav_home)
    }

        override fun onBackPressed() {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.main, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_settings -> return true
                else -> return super.onOptionsItemSelected(item)
            }
        }

        fun displayScreen(id: Int){

            when (id){
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

            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }

    }
