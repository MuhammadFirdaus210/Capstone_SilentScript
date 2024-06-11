package com.example.silentscript.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.R
import com.example.silentscript.databinding.FragmentNotificationsBinding
import com.example.silentscript.ui.home.Abjad
import com.example.silentscript.ui.home.DetailAbjadActivity
import com.example.silentscript.ui.home.ListAbjadAdapter

class NotificationsFragment : Fragment() {

    private lateinit var recyclerview_video: RecyclerView
    private var _binding: FragmentNotificationsBinding? = null
    private val list = ArrayList<Video>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        recyclerview_video = binding.recylerViewVideo
        recyclerview_video.setHasFixedSize(true)

        list.addAll(getListVideo())
        showRecyclerList()

        return root
    }

    private fun showRecyclerList() {
        recyclerview_video.layoutManager = LinearLayoutManager(context)
        val listAbjadAdapter = VideoAdapter(list)
        recyclerview_video.adapter = listAbjadAdapter

        listAbjadAdapter.setOnItemClickCallback(object : VideoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Video) {
                showSelectedHero(data)
            }
        })
    }
    private fun getListVideo(): ArrayList<Video>{
        val dataName = resources.getStringArray(R.array.data_video)
        val listVideo = ArrayList<Video>()
        for (i in dataName.indices){
            val video = Video(
                dataName[i]
            )
            listVideo.add(video)
        }
        return listVideo
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showSelectedHero(data: Video) {
        val intent = Intent(requireContext(), DetailActivityVideo::class.java)
        intent.putExtra("video_data", data)
        startActivity(intent)
    }
}