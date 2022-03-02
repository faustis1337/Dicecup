package model

import android.os.Build
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

object History {
    private var diceHistory = mutableListOf<HistoryEntity>()

    fun getAll() = diceHistory

    fun clear() = diceHistory.clear()

    fun add(diceResult:ArrayList<Int>){
        diceHistory.add(HistoryEntity(diceHistory.size+1,LocalDateTime.now(),resultAsString(diceResult)))
    }

    private fun resultAsString(resultArray: ArrayList<Int>): String {
        var stringResult = "";

        for(i in resultArray.indices){
            if(i < resultArray.lastIndex){
                stringResult += "${resultArray[i]} - "
            }
            if(i == resultArray.lastIndex){
                stringResult += "${resultArray[i]}"
            }
        }

        return stringResult;
    }

    //private fun resultAsArrayOfInt(result: String) : ArrayList<Int>{}
}