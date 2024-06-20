package com.example.silentscript.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mystoryapp.data.preference.UserPreferences
import com.example.silentscript.LoginActivity
import com.example.silentscript.R
import com.example.silentscript.databinding.FragmentProfileBinding
import com.example.silentscript.ui.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var btnLogout: Button

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")
    private val GAME_REQUEST_CODE = 1


    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
        lifecycleScope.launch {
            val token = userPreferences.getToken().first()
            val uid = userPreferences.getUid().first() // Replace with actual uid
            if (token.isEmpty()) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                profileViewModel.getUserDetails(token, uid)
            }
        }

        profileViewModel.userResponse.observe(viewLifecycleOwner, Observer { userResponse ->
            // Update UI with userResponse
            binding.username.text = userResponse?.data?.username
            binding.email.text = userResponse?.data?.email
        })

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GAME_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Game finished, update user details
            val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
            lifecycleScope.launch {
                val token = userPreferences.getToken().first()
                val uid = userPreferences.getUid().first()
                if (token.isNotEmpty()) {
                    profileViewModel.getUserDetails(token, uid)
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogout = view.findViewById(R.id.logout)

        // Use the button or set up listeners here
        btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
            MainScope().launch {
                userPreferences.deleteUid()
                userPreferences.deleteToken()
            }
            startActivity(Intent(activity, LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}