package com.emir.notdefteri

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emir.notdefteri.databinding.HerSatirBinding

class RcyAd (private val activity: NotDefteri, private val bilgi: MutableList<dtbac.EklenenNot>):
    RecyclerView.Adapter <RcyAd.RecyHolder>() {
    class RecyHolder(val bnd: HerSatirBinding):
        RecyclerView.ViewHolder(bnd.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyHolder {
        val binding = HerSatirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyHolder, position: Int) {
        if (bilgi[position].top.isEmpty()){
            val text = "Başlık Yok"
            holder.bnd.rViewTV.text = text
        }
        else{
            holder.bnd.rViewTV.text = bilgi[position].top
        }

        val sn = if (bilgi[position].veri.length <= 80) {
            bilgi[position].veri
        } else {
            bilgi[position].veri.substring(0, 80) + "..."
        }

        holder.bnd.rViewTVsn.text = sn
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NotDefteriNotEkle::class.java)
            intent.putExtra("Id", bilgi[position].id)
            intent.putExtra("Baslik", bilgi[position].top)
            intent.putExtra("İcerik", bilgi[position].veri)
            intent.putExtra("SecildiMi", true)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener{
            holder.bnd.rmbr.visibility = View.VISIBLE
            true
        }

        holder.bnd.rmbr.setOnClickListener {
            val dtba = dtbac(holder.itemView.context)
            dtba.sil(bilgi[position].id.toString())
            bilgi.removeAt(position)
            if(bilgi.size == 0){
                activity.bnd.RecyclerView.visibility = View.INVISIBLE
                val text = "Hiçbir not yok."
                activity.bnd.notlartext.text = text
            }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, bilgi.size)
        }
    }

    override fun getItemCount(): Int {
        return bilgi.size
    }
}