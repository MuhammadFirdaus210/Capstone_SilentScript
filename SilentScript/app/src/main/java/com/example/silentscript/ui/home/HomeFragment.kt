package com.example.silentscript.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mystoryapp.data.preference.UserPreferences
import com.example.silentscript.LoginActivity
import com.example.silentscript.databinding.FragmentHomeBinding
import com.example.silentscript.ui.game.GameActivity
import com.example.silentscript.ui.library.huruf.HurufActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
        lifecycleScope.launch {
            val token = userPreferences.getToken().first()
            val uid = userPreferences.getUid().first() // Replace with actual uid
            if (token.isEmpty()) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                homeViewModel.getUserDetails(token, uid)
            }
        }

        homeViewModel.userResponse.observe(viewLifecycleOwner, Observer { userResponse ->
            // Update UI with userResponse
            binding.username.text = userResponse?.data?.username
            binding.badge.text = userResponse?.badge
            binding.score.text = userResponse?.data?.points.toString()
        })

        showToken()
        onclick()
        return root
    }
    private fun onclick() {
        binding.imgAbjad.setOnClickListener {
            val intent = Intent(activity, HurufActivity::class.java)
            startActivity(intent)
        }
        binding.play.setOnClickListener {
            val intent = Intent(activity, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToken() {
        val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
        lifecycleScope.launch {
            val token = userPreferences.getToken().first()
            Toast.makeText(activity, "Token: $token", Toast.LENGTH_LONG).show()
        }
    }
}