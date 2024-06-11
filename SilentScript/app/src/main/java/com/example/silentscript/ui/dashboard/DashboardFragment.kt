package com.example.silentscript.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.GameAdapter
import com.example.silentscript.ui.home.Abjad
import com.example.silentscript.R
import com.example.silentscript.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var recylerView_game: RecyclerView
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recylerView_game = binding.recylerViewGame

        val Abjad = getListAbjad()
        recylerView_game.layoutManager = GridLayoutManager(requireContext(), 2)

        // Buat adapter RecyclerView dan pasang ke RecyclerView
        val adapter = GameAdapter(Abjad)
        recylerView_game.adapter = adapter

        return root
    }

    private fun getListAbjad(): ArrayList<Abjad> {
        val dataName = resources.getStringArray(R.array.data_abjad)
        val listFood = ArrayList<Abjad>()
        for (i in dataName.indices) {
            val food = Abjad(
                dataName[i]
            )
            listFood.add(food)
        }
        return listFood
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}