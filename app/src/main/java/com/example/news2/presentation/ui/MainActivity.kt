package com.example.news2.presentation.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news2.R
import com.example.news2.databinding.ActivityMainBinding
import com.example.news2.presentation.tools.SnackBar
import com.example.news2.util.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navHostFragment =supportFragmentManager.findFragmentById((R.id.fragmentContainerView)) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.botomNavigation.setupWithNavController(navController)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        ConnectivityReceiver.connectivityReceiverListener = null
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if(!isConnected) {
            SnackBar(binding.root, "You are offline")
        } else {
            SnackBar(binding.root, "You are online")
        }
    }
}