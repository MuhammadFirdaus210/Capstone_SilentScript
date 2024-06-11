package com.example.silentscript.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.silentscript.LoginActivity
import com.example.silentscript.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find views using the inflated view
        btnLogout = view.findViewById(R.id.logout)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use the button or set up listeners here
        btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}