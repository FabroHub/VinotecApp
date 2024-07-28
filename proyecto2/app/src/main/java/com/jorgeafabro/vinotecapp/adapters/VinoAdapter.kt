package com.jorgeafabro.vinotecapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.jorgeafabro.vinotecapp.R
import com.jorgeafabro.vinotecapp.databinding.VinoModelBinding
import com.jorgeafabro.vinotecapp.fileutils.WineData
import com.bumptech.glide.request.target.Target
import android.graphics.drawable.Drawable
import android.widget.Button
import com.bumptech.glide.load.DataSource

//  Adapter de las listas.

class VinoAdapter(val context: Context, private var listWine: List<WineData>) :
    RecyclerView.Adapter<VinoAdapter.ViewHolder>()/*, View.OnClickListener */{

    private val originalList: List<WineData> = listWine.toMutableList()
    private var filteredList: List<WineData> = originalList

    override fun getItemCount(): Int {
        return filteredList.size
    }

    //Pone los datos en los huecos correspondientes al igual que la imagen
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]
        holder.vino1.text = item.vino
        holder.uva1.text = item.uva
        holder.pais1.text = "(" + item.pais + ")"
        holder.tipo1.text = item.tipo
        holder.region1.text = item.region
        holder.precio1.text = item.precio + "€"

        Glide.with(holder.itemView.context)
            .load(item.url)
            .error(R.drawable.baseline_add_24)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(holder.img1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = VinoModelBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    //  Parte que une conla funcion onBindViewHolder
    class ViewHolder(val binding: VinoModelBinding) : RecyclerView.ViewHolder(binding.root) {
        val vino1: TextView = binding.tvNombreVino
        val pais1: TextView = binding.tvPais
        val uva1: TextView = binding.tvUva
        val img1: ImageView = binding.imgVino
        val tipo1: TextView = binding.tvTipo
        val region1: TextView = binding.tvRegion
        val precio1: Button = binding.btnCompra
    }

    fun setData(listData: List<WineData>) {
        filteredList = listData
        notifyDataSetChanged()
    }
}
