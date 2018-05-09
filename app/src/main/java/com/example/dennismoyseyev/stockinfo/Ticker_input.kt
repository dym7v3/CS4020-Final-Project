package com.example.dennismoyseyev.stockinfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_ticker.*

var gameModel= GameDataModel()

class Ticker_input : AppCompatActivity() {

    lateinit var input_text: EditText
    private val tickerAdap = adap(this, gameModel.tickers, gameModel.stockNames)

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
                gameModel.processInput(input_text.text.toString())
                tickerAdap.notifyDataSetChanged()
            }
            handled
        })
    }


    private fun bindButtons()
    {

    }

}
