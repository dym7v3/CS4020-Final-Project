package com.example.dennismoyseyev.stockinfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class adap(context: Context, private var tickers: ArrayList<String>, private var stockNames: ArrayList<String>) : BaseAdapter()  {

    var mContext= context
    //The size of the laps list.
    override fun getCount(): Int {
        return tickers.size
    }

    //Gets the id of at the position.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //Adds the content form the arraylist to the view.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val mRow: View?
        val vh: ViewHolder

        //Checks if the view is null. If it is then it will save the current row as the
        // convert-view.
        //I used the view holder to be able to get better scrolling.
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(mContext)
            mRow = layoutInflater.inflate(R.layout.row, parent, false)
            vh = ViewHolder(mRow)
            mRow.tag = vh
        } else {
            mRow= convertView
            vh = mRow.tag as ViewHolder
        }
        vh.symbol.text = tickers[position]
        vh.name.text =  stockNames[position]
        return mRow
    }
    //Gets the item at the current position that was give the integer.
    override fun getItem(position: Int): Any {
        return tickers[position]
    }
}
//Makes the View Holder. Which I read makes better for scrolling.
private class ViewHolder(view: View?) {
    val symbol: TextView = view?.findViewById(R.id.Symbol) as TextView
    val name: TextView = view?.findViewById(R.id.Name) as TextView
}