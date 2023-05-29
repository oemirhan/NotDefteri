package com.emir.notdefteri

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.emir.notdefteri.databinding.ActivityMainBinding

class NotDefteri : AppCompatActivity() {
    lateinit var bnd: ActivityMainBinding
    private lateinit var dtba: dtbac
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        demo()
        bnd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        dtba = dtbac(this)
        sayfayiyukle()
    }

    override fun onResume() {
        super.onResume()
        sayfayiyukle()
    }

    private fun sayfayiyukle(){
        val icerik = dtba.icerigial()

        if(icerik.isEmpty()){
            bnd.RecyclerView.visibility = View.INVISIBLE
            val text = "Hiçbir not yok."
            bnd.notlartext.text = text
        }
        else{
            bnd.RecyclerView.visibility = View.VISIBLE
            val text = "Notlar"
            bnd.notlartext.text = text
            val mylandAdapter = RcyAd(this, icerik)
            bnd.RecyclerView.layoutManager = LinearLayoutManager(this)
            bnd.RecyclerView.adapter = mylandAdapter
        }
    }

    fun fActButonClick(v: View){
        val sAI = Intent(this, NotDefteriNotEkle::class.java)
        sAI.putExtra("SecildiMi", 0)
        startActivity(sAI)
    }

    private fun demo(){
        val text1 = "Bu Emirhan Ömeroğlu tarafından hazırlanan\nbir Not Defteri demosudur.\n" +
                    "Orijinalde, Emirhan'ın geliştirdiği başka bir projenin parçasıdır."
        val text2 = "Demo, tam versiyonda sahip olduğu birçok özellikten mahrumdur.\n" +
                    "Tam versiyonun geliştirme süreci henüz tamamlanmadığı ve yayınlanamayacağı için\n" +
                    "bu örnek yayınlanmak üzere hazırlanmıştır."

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Demo").setMessage(text1).setPositiveButton("Devam Et") { _, _ ->
            val builder2 = AlertDialog.Builder(this)
            builder2.setTitle("Demo")
            builder2.setMessage(text2)
            builder2.setPositiveButton(android.R.string.yes) { _, _ ->}.show()
        }.setNeutralButton("LinkedIn") { _, _ ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/emirhanomeroglu/"))
            startActivity(intent)
        }.show()
    }
}