package com.jorgeafabro.vinotecapp.navitems

import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jorgeafabro.vinotecapp.R
import com.jorgeafabro.vinotecapp.adapters.VinoAdapter
import com.jorgeafabro.vinotecapp.databinding.FragmentParaTiBinding
import com.jorgeafabro.vinotecapp.fileutils.ObjectData.wine
import com.jorgeafabro.vinotecapp.fileutils.ObjectData.wine2
import com.jorgeafabro.vinotecapp.fileutils.WineData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//  Pantalla de Inicio

class ParaTi : Fragment() {

    var listData: MutableList<WineData> = ArrayList()
    lateinit var adapter: VinoAdapter
    private var _binding: FragmentParaTiBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()


    private var vinoA = ArrayList<WineData>()
    private val vino2: MutableList<MutableMap<String, Any>> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentParaTiBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inicializar el adaptador
        adapter = VinoAdapter(requireContext(), listData)

        vinos()
        return view

    }

    //  Hace que se muestre el listado de vinos
    private fun vinos() {
        lifecycleScope.launch {

            val db = FirebaseFirestore.getInstance()
            val collectionRef = db.collection("/vinos")


            collectionRef.get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        val documentId = document.id
                        val documentData = document.data

                        val data = document.data
                        val userMap: MutableMap<String, Any> = mutableMapOf()
                        userMap.putAll(data)
                        vino2.add(userMap)

                        println("vino2\n" + vino2)
                        println("vinoA\n" + vinoA)

                    }
                }
                .addOnFailureListener { exception ->
                }

            delay(500)
            ArrayListReader()
        }
    }

    //  Carga los datos desde Firebase
    private fun ArrayListReader() {
        for (usuario in vino2) {
            val id = usuario["id"] as String
            val vino = usuario["vino"] as String
            val tipo = usuario["tipo"] as String
            val pais = usuario["pais"] as String
            val region = usuario["region"] as String
            val uva = usuario["uva"] as String
            val url = usuario["url"] as String
            val precio = usuario["precio"] as String

            val vino1 = WineData(id, vino, tipo, pais, region, uva, url, precio)
            vinoA?.add(vino1)
        }

        val recycler = binding.rlParaTi

        adapter.setData(vinoA)

        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}