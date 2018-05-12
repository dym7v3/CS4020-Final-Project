package com.example.dennismoyseyev.stockinfo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.MainThread
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_ticker_input.*
import kotlinx.android.synthetic.main.fragment_ticker.*
import okhttp3.*
import java.io.IOException

var Model= GameDataModel()

class Ticker_input : AppCompatActivity() {

    private val tickerAdap = adap(this, Model.tickers, Model.stockNames, Model.sectors)
    private val db = DataBaseHandler(context = this)

   //The onCreate function that will bind the button and set up the view.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticker_input)
        listOfTickers.adapter = tickerAdap
        bindButtons()
    }

    //This will be the event handler that will look at the text and process it.
    override fun onResume() {
        super.onResume()
        input_text.setOnEditorActionListener({ _, action, _ ->
            val handled = false
            if (action == EditorInfo.IME_ACTION_DONE)
            {
                getJson(input_text.text.toString())
            }
            handled
        })
    }


    //When the button is pressed it will take it to the next screen and it will start a new activity that will allow for the user to see more
    //information about the stocks that they entered in on the previous screen.
    private fun bindButtons()
    {
        Get_More_Info.setOnClickListener {
            if(Model.tickers.isEmpty())
            {
                Toast.makeText(this, "Enter some tickers before going to the next screen.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(this, Display_Info::class.java)
                startActivity(intent)
            }
        }
    }


    private fun getJson(symbol: String)
    {
        if( !Model.tickers.contains(symbol.toUpperCase()))
        {
            val url = "https://api.iextrading.com/1.0/stock/$symbol/batch?types=quote"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val body = response?.body()?.string()
                    println(body)
                    if (body.equals("Unknown symbol")) {
                        runOnUiThread { Toast.makeText(this@Ticker_input, "You Entered an Invalid symbol!", Toast.LENGTH_SHORT).show() }
                    }
                    else
                    {
                        val gson = GsonBuilder().create()
                        val info = gson.fromJson(body, Quotes::class.java)
                        val myStock = stock(info.quote["symbol"].toString(),info.quote["companyName"].toString(), info.quote["peRatio"].toString(), info.quote["latestPrice"].toString(), info.quote["sector"].toString(), info.quote["oepn"].toString(), info.quote["close"].toString())

                        runOnUiThread {
                            db.insertData(myStock,this@Ticker_input)
                            Model.stockNames.add(info.quote["companyName"].toString())
                            Model.tickers.add(symbol.toUpperCase())
                            Model.sectors.add(info.quote["sector"].toString())
                            tickerAdap.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println("The API request failed to make the call!")
                }
            })
        }
        else
        {
            Toast.makeText(this, "Already have the Symbol: ${symbol.toUpperCase()} in the list. ", Toast.LENGTH_SHORT).show()
        }
    }
}




