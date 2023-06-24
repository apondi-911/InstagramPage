package com.example.instapage


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), InstStoriesAdapter.OnItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
//        val navController = Navigation.findNavController(this, androidx.navigation.fragment.R.id.nav_host_fragment_container)
//        val navController = Navigation.findNavController(this, androidx.navigation.fragment.R.id.nav_host_fragment_container)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

    override fun onItemImageClicked(position: Int) {
        Toast.makeText(this, "Story", Toast.LENGTH_SHORT).show()
        // Handle the item image click event here
    }
}




