package com.example.dicecup

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import model.History
import model.HistoryEntity
import java.time.LocalDateTime

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        onSwitchAction()

        btnClear.setOnClickListener{ onClickClear() }
        btnBack.setOnClickListener{ onClickBack() }
        switcherImages.setOnClickListener{ onSwitchAction() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("switcherValue",switcherImages.isChecked);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val switcherValue = savedInstanceState.getBoolean("switcherValue")

        if(!switcherValue){
            val adapter = HistoryAdapter(this,History.getAll())
            lvHistory.adapter = adapter
        }else{
            val adapter = HistoryAdapterWithImg(this,History.getAll())
            lvHistory.adapter = adapter
        }
    }

    private fun onSwitchAction() {
        if(!switcherImages.isChecked){
            val adapter = HistoryAdapter(this,History.getAll())
            lvHistory.adapter = adapter
            return
        }

        val adapter = HistoryAdapterWithImg(this,History.getAll())
        lvHistory.adapter = adapter
    }

    private fun onClickBack() {
        this.finish()
    }

    private fun onClickClear() {
        History.clear()
        lvHistory.adapter = null
    }

    internal class HistoryAdapter(context: Context, private val history:MutableList<HistoryEntity>) : ArrayAdapter<HistoryEntity>(context,0,history){

        private val colours = intArrayOf(
            Color.parseColor("#AAAAAA"),
            Color.parseColor("#CCCCCC")
        )

        override fun getView(position: Int, v: View?, parent: ViewGroup): View {
            var v1: View? = v
            if (v1 == null) {
                val mInflater = LayoutInflater.from(context)
                v1 = mInflater.inflate(R.layout.adapter_view_layout, null)

            }
            val resView: View = v1!!
            resView.setBackgroundColor(colours[position % colours.size])
            val h = history[position]
            val indexView = resView.findViewById<TextView>(R.id.indexView)
            val dateView = resView.findViewById<TextView>(R.id.dateView)
            val diceView = resView.findViewById<TextView>(R.id.diceView)
            indexView.text = h.index.toString()
            dateView.text = h.date.toString()
            diceView.text = h.result
            return resView
    }
}
    internal class HistoryAdapterWithImg(context: Context, private val history:MutableList<HistoryEntity>) : ArrayAdapter<HistoryEntity>(context,0,history){

        private val colours = intArrayOf(
            Color.parseColor("#AAAAAA"),
            Color.parseColor("#CCCCCC")
        )

        override fun getView(position: Int, v: View?, parent: ViewGroup): View {
            var v1: View? = v
            if (v1 == null) {
                val mInflater = LayoutInflater.from(context)
                v1 = mInflater.inflate(R.layout.adapter_view_with_images_layout, null)

            }
            val resView: View = v1!!
            resView.setBackgroundColor(colours[position % colours.size])
            val h = history[position]
            val indexView = resView.findViewById<TextView>(R.id.indexView)
            val dateView = resView.findViewById<TextView>(R.id.dateView)
            val diceLayout = resView.findViewById<LinearLayout>(R.id.layoutDices)
            indexView.text = h.index.toString()
            dateView.text = h.date.toString()

            val result = h.result.split(" - ").toTypedArray()
            diceLayout.removeAllViews()
            for(i in result){
                val imgView = ImageView(context)
                imgView.layoutParams = LinearLayout.LayoutParams(50,50);
                imgView.setImageResource(MainActivity().diceId[i.toInt()])
                diceLayout.addView(imgView)
            }

            return resView
        }
    }
}