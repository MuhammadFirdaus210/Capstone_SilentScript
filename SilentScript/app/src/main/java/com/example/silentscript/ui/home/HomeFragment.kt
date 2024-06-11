package com.example.silentscript.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.silentscript.GameActivity
import com.example.silentscript.R
import com.example.silentscript.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var username: TextView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        username = root.findViewById(R.id.username)
        firebaseAuth.currentUser?.let {
            username.text = it.displayName
        }

        onclick()
        return root
    }

    private fun onclick() {
        binding.imgAbjad.setOnClickListener {
            val intent = Intent(activity, AbjadActivity::class.java)
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
}