package com.jorgeafabro.vinotecapp.navitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jorgeafabro.vinotecapp.R
import com.jorgeafabro.vinotecapp.adapters.VinoAdapter
import com.jorgeafabro.vinotecapp.databinding.FragmentBuscadorBinding
import com.jorgeafabro.vinotecapp.fileutils.WineData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class Buscador : Fragment() {

    private var _binding: FragmentBuscadorBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: VinoAdapter

    private val db = FirebaseFirestore.getInstance()
    private val vinoA = ArrayList<WineData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuscadorBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = view.findViewById(R.id.rlBuscador)
        searchView = view.findViewById(R.id.searchView)
        adapter = VinoAdapter(requireContext(), vinoA)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        loadDataFromFirestore()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //  Carga los datos desde la base de datos de Firebase
    private fun loadDataFromFirestore() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val querySnapshot = db.collection("vinos").get().await()
                val wineList = ArrayList<WineData>()

                for (document in querySnapshot.documents) {
                    val id = document.getString("id") ?: ""
                    val vino = document.getString("vino") ?: ""
                    val tipo = document.getString("tipo") ?: ""
                    val pais = document.getString("pais") ?: ""
                    val region = document.getString("region") ?: ""
                    val uva = document.getString("uva") ?: ""
                    val url = document.getString("url") ?: ""
                    val precio = document.getString("precio") ?: ""

                    val wineData = WineData(id, vino, tipo, pais, region, uva, url, precio)
                    wineList.add(wineData)
                }

                vinoA.clear()
                vinoA.addAll(wineList)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar desde Firestore", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //  Hace funcionar el Buscador (SearchView)
    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<WineData>()
            for (wineData in vinoA) {
                if (query?.lowercase(Locale.ROOT)
                        ?.let { wineData.vino?.lowercase(Locale.ROOT)?.contains(it) } == true) {
                    filteredList.add(wineData)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "Sin información", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setData(filteredList)
            }
        }
    }
}


