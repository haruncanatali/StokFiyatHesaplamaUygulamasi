package com.example.stokfiyathesaplamauygulamasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.tfb.fbtoast.FBToast

class MainActivity : AppCompatActivity() {

    lateinit var urunAdiEdx : AutoCompleteTextView
    lateinit var adetSpinner : Spinner
    lateinit var fiyatSpinner: Spinner
    lateinit var ekleButton: Button
    lateinit var temizleButton: Button
    lateinit var hesaplaButton: Button
    lateinit var rootLayout: LinearLayout

    val urunler = arrayOf("Tornavida","Çekiç","Defter","Çivi","Tablo","Kalebodur")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FBToast.successToast(this,"Hoşgeldiniz",FBToast.LENGTH_SHORT);

        ElementTanimla()
        ButonlariEtkisizKil()
        AdapterTanimla()

        EkleBtnExecute()
        TemizleBtnExecute()
        HesaplaBtnExecute()
    }

    fun ElementTanimla(){
        urunAdiEdx = findViewById(R.id.urunAdiEdx)
        adetSpinner = findViewById(R.id.adetSpin)
        fiyatSpinner = findViewById(R.id.fiyatSpin)
        ekleButton = findViewById(R.id.ekleBtn)
        temizleButton = findViewById(R.id.temizleBtn)
        hesaplaButton = findViewById(R.id.hesaplaBtn)
        rootLayout = findViewById(R.id.rootLayout)
    }

    fun AdapterTanimla(){
        var adapter = ArrayAdapter<String>(this,
            com.tfb.fbtoast.R.layout.support_simple_spinner_dropdown_item,this.urunler)
        urunAdiEdx.setAdapter(adapter)
    }

    fun ButonlariEtkisizKil(){
        hesaplaButton.visibility = View.INVISIBLE
        temizleButton.visibility = View.INVISIBLE
    }

    fun ButonEtkisizligiKaldir(){
        hesaplaButton.visibility = View.VISIBLE
        temizleButton.visibility = View.VISIBLE
    }

    fun EkleBtnExecute(){
        ekleButton.setOnClickListener {
            if(urunAdiEdx.text.isNullOrEmpty()){
                FBToast.errorToast(this,"Lütfen ürün adı giriniz.",FBToast.LENGTH_SHORT);
            }
            else{
                var infilator = LayoutInflater.from(this)
                var newLayout = infilator.inflate(R.layout.activity_new_item,null)

                newLayout.findViewById<TextView>(R.id.urunAdiTxt).text = urunAdiEdx.text.toString()
                newLayout.findViewById<TextView>(R.id.adetTxt).text = adetSpinner.selectedItem.toString()
                newLayout.findViewById<TextView>(R.id.fiyatTxt).text = fiyatSpinner.selectedItem.toString()
                newLayout.findViewById<TextView>(R.id.toplamTxt).text =
                    ((fiyatSpinner.selectedItem.toString().split(' ')[0].toInt()) * (adetSpinner.selectedItem.toString().split(' ')[0].toInt())).toString() + " TL"

                rootLayout.addView(newLayout)

                adetSpinner.setSelection(0)
                fiyatSpinner.setSelection(0)
                urunAdiEdx.setText("")

                newLayout.findViewById<Button>(R.id.urunuKaldirBtn).setOnClickListener {
                    rootLayout.removeView(newLayout)
                    if(rootLayout.childCount == 0){
                        ButonlariEtkisizKil()
                    }
                }

                if(rootLayout.childCount > 0){
                    ButonEtkisizligiKaldir()
                }
            }
        }
    }

    fun TemizleBtnExecute(){
        temizleButton.setOnClickListener {
            if(rootLayout.childCount > 0){
                rootLayout.removeAllViews()
                ButonlariEtkisizKil()
            }
        }
    }

    fun HesaplaBtnExecute(){
        hesaplaButton.setOnClickListener {
            if(rootLayout.childCount > 0)
            {
                var toplamFiyat = 0

                for(i in 0..rootLayout.childCount-1){
                    toplamFiyat += rootLayout.getChildAt(i).findViewById<TextView>(R.id.toplamTxt).text
                        .toString().split(' ')[0].toInt()
                }

                FBToast.infoToast(this,"Toplam Fiyat :"+toplamFiyat+" TL",FBToast.LENGTH_LONG)
            }
        }
    }
}