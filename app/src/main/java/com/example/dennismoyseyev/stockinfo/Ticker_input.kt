package com.example.dennismoyseyev.stockinfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.MainThread
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_ticker.*
import okhttp3.*
import java.io.IOException

var Model= GameDataModel()


class Ticker_input : AppCompatActivity() {

    lateinit var input_text: EditText
    private val tickerAdap = adap(this, Model.tickers, Model.stockNames, Model.sectors)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticker_input)
        input_text = findViewById(R.id.input_text)
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


    private fun bindButtons()
    {

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

                        runOnUiThread {
                            Toast.makeText(this@Ticker_input, "You Entered an Invalid symbol!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val gson = GsonBuilder().create()
                        val info = gson.fromJson(body, Quotes::class.java)
                        runOnUiThread {
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




