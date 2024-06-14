package com.example.silentscript.ui.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silentscript.databinding.FragmentLibraryBinding
import com.example.silentscript.ui.library.huruf.HurufActivity
import com.example.silentscript.ui.library.data.response.LibraryResponse
import com.example.silentscript.ui.library.kata.KataActivity

class LibraryFragment : Fragment() {

    private var adapter = LibraryAdapter(emptyList())
    private lateinit var viewModel: LibraryViewModel
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recylerViewVideo.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
        viewModel.fetchLibrary()
        viewModel.library.observe(viewLifecycleOwner) { library ->
            updateUI(library)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.isLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun updateUI(library: List<LibraryResponse>) {
        val listSlug = mapOf(
            "1" to "huruf",
            "2" to "salam",
            "3" to "frasa",
            "4" to "hubungan",
            "5" to "makanan",
            "6" to "kesehatan"
        )
        adapter = LibraryAdapter(library)
        binding.recylerViewVideo.adapter = adapter
        adapter.onItemClick = { libraryItem ->
            if (libraryItem.id == "1") {
                val intent = Intent(context, HurufActivity::class.java)
                Log.d("TAG", "updateUI: ${libraryItem.id}")
                intent.putExtra("USER_ID", listSlug["1"])
                intent.putExtra("judul", libraryItem.kategori)
                startActivity(intent)
            }else {
                val intent = Intent(context, KataActivity::class.java)
                Log.d("TAG", "updateUI: ${libraryItem.id}")
                intent.putExtra("key", libraryItem.id)
                intent.putExtra("slug", listSlug[libraryItem.id])
                intent.putExtra("judul", libraryItem.kategori)
                startActivity(intent)
            }
        }
        adapter.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}