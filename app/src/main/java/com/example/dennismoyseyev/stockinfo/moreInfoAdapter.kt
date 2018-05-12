package com.example.dennismoyseyev.stockinfo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

class information(context: Context, private var STOCKS: ArrayList<stock>) : BaseAdapter()  {

    var mContext= context
    //The size of the laps list.
    override fun getCount(): Int {
        return STOCKS.size
    }

    //Gets the id of at the position.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //Adds the content form the arraylist to the view.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val mRow: View?
        val vh: CustomViewHolder

        //Checks if the view is null. If it is then it will save the current row as the
        // convert-view.
        //I used the view holder to be able to get better scrolling.
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(mContext)
            mRow = layoutInflater.inflate(R.layout.row_with_more_data, parent, false)
            vh = CustomViewHolder(mRow)
            mRow.tag = vh
        } else {
            mRow= convertView
            vh = mRow.tag as CustomViewHolder
        }

        vh.mClose.text = STOCKS[position].close
        vh.mOpen.text = STOCKS[position].open
        vh.mCompanyName.text = STOCKS[position].companyName
        vh.mPrice.text = STOCKS[position].latestPrice
        vh.mPeRatio.text =STOCKS[position].peRatio


        val url = "https://api.iextrading.com/1.0/stock/${STOCKS[position].smybol}/logo"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println("THIS IS THE BODY!!!!!"+body)
                if (body.equals("Unknown symbol")) {

                }
                else
                {

                    Picasso.get().load(body).into(vh.image)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("The API request failed to make the call!")
            }
        })


        return mRow
    }
    //Gets the item at the current position that was give the integer.
    override fun getItem(position: Int): Any {
        return STOCKS[position]
    }







}
//Makes the View Holder. Which I read makes better for scrolling.
class CustomViewHolder(view: View?) {
    val mOpen: TextView = view?.findViewById(R.id.mOpen) as TextView
    val mCompanyName: TextView = view?.findViewById(R.id.mCompanyName) as TextView
    val mClose: TextView = view?.findViewById(R.id.mClose) as TextView
    val mPrice: TextView = view?.findViewById(R.id.mPrice) as TextView
    val mPeRatio: TextView = view?.findViewById(R.id.mPeRatio) as TextView
    val image: ImageView = view?.findViewById(R.id.Logo) as ImageView
}


