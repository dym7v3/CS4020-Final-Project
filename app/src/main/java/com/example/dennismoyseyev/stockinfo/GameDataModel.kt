package com.example.dennismoyseyev.stockinfo

import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class GameDataModel
{
    var tickers: ArrayList<String> = ArrayList()
    var stockNames: ArrayList<String> = ArrayList()
    var stockName="Unknown Company"
    var stockSector = "Unknown Sector"





    internal fun processInput(ticker: String)
    {

        getJson(ticker)
        tickers.add(ticker.toUpperCase())
        stockNames.add(stockName)


    }

    private fun getJson(symbol: String)
    {
        val url = "https://api.iextrading.com/1.0/stock/$symbol/batch?types=quote"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
               val body = response?.body()?.string()
               if(body.equals("Unknown Symbol"))
               {

               }
               else
               {
                   val gson = GsonBuilder().create()
                   val info = gson.fromJson(body, quotes::class.java)
                   stockName = info.quote.getValue("companyName").toString()
                   println(stockName)
               }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("The API request failed to make the call!")
            }
        })

    }

}
class quotes( val quote: Map<String, Any>)
