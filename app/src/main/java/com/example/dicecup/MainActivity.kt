package com.example.dicecup

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import model.History
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private val mRandomGenerator = Random()
    var lastResult = ArrayList<Int>()

    val diceId = intArrayOf(0, R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main)
        }else if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main)
        }

        btnRoll.setOnClickListener{ onClickRoll() }
        btnHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList("diceResult",lastResult);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastResult = savedInstanceState.getIntegerArrayList("diceResult") as ArrayList<Int>
        displayDices()
    }

    private fun onClickRoll() {
        val sliderValue = sliderDices.value.toInt();
        lastResult.clear();
        repeat(sliderValue){
            val rolledNumber = mRandomGenerator.nextInt(6) + 1
            lastResult.add(rolledNumber)
        }
        displayDices()
        History.add(lastResult)
    }

    private fun displayDices(){
        layoutDices.removeAllViews()
        for(i in lastResult){
            val imgView = ImageView(this)
            imgView.layoutParams = LinearLayout.LayoutParams(100,100);
            imgView.setImageResource(diceId[i])
            layoutDices.addView(imgView)
        }
    }
}