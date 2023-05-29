package com.emir.notdefteri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.emir.notdefteri.databinding.ActivityNotDefteriNotEkleBinding

class NotDefteriNotEkle : AppCompatActivity() {
    private lateinit var bnd: ActivityNotDefteriNotEkleBinding
    private lateinit var dtba: dtbac
    private var secildimi: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_defteri_not_ekle)

        bnd = ActivityNotDefteriNotEkleBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        dtba = dtbac(this)

        secildimi = intent.getBooleanExtra("SecildiMi", false)

        if(secildimi){
            bnd.secilennotusil.visibility = View.VISIBLE
            val guncelletext = "Güncelle"
            bnd.kaydetbutton.text = guncelletext
            val secilenbaslik = intent.getStringExtra("Baslik")
            val secilenicerik = intent.getStringExtra("İcerik")

            bnd.basliktextbox.text = Editable.Factory.getInstance().newEditable(secilenbaslik)
            bnd.mesajtextbox.text = Editable.Factory.getInstance().newEditable(secilenicerik)
        }
        else{
            val guncelletext = "Kaydet"
            bnd.kaydetbutton.text = guncelletext
            bnd.secilennotusil.visibility = View.INVISIBLE
        }
    }

    fun yeninotekleclick(v:View){
        kaydetvecik()
    }

    private fun kaydetvecik(){
        val baslikdeger = bnd.basliktextbox.text.toString().trim()
        val mesajdeger = bnd.mesajtextbox.text.toString().trim()

        if(baslikdeger.isEmpty() && mesajdeger.isEmpty()){
            Toast.makeText(this, "Hiçbir not girilmedi.", Toast.LENGTH_SHORT).show()
        }
        else{
            if(mesajdeger.isNotEmpty()){
                if (!secildimi){
                    val yeniEklenenNot = dtbac.EklenenNot(null, baslikdeger, mesajdeger)
                    dtba.ekle(yeniEklenenNot)
                    finish()
                }
                else{
                    val secilennotid = intent.getStringExtra("Id").toString()
                    val yeniEklenenNot = dtbac.EklenenNot(secilennotid, baslikdeger, mesajdeger)
                    dtba.guncelle(yeniEklenenNot)
                    finish()
                }
                Toast.makeText(this, "Kaydedildi", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "İçerik Yok", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun secilennotusilclick(v:View){
        if(secildimi){
            val secilennotid = intent.getStringExtra("Id").toString()
            dtba.sil(secilennotid)

            finish()
        }
    }
}