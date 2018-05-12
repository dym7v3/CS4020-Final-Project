package com.example.dennismoyseyev.stockinfo

import android.content.ContentValues
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import org.jetbrains.anko.db.*
const val TABLE_NAME = "STOCKS"

class DataBaseHandler(context: Context): ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(StockInfoSchema.NAME, true,
                StockInfoSchema.Cols.ID to INTEGER + PRIMARY_KEY,
                StockInfoSchema.Cols.SECTOR to TEXT,
                StockInfoSchema.Cols.COMPANYNAME to TEXT,
                StockInfoSchema.Cols.OPEN to TEXT,
                StockInfoSchema.Cols.CLOSE to TEXT,
                StockInfoSchema.Cols.PERATIO to TEXT,
                StockInfoSchema.Cols.LATESTPRICE to TEXT,
                StockInfoSchema.Cols.SYMBOL to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val DB_NAME = "Stocks.db"
        const val DB_VERSION = 1
    }

    fun insertData(mStock: stock, context:Context) {
        val db = this.writableDatabase
        val cv = ContentValues()

          cv.put(StockInfoSchema.Cols.CLOSE, mStock.close)
          cv.put(StockInfoSchema.Cols.COMPANYNAME, mStock.companyName)
          cv.put(StockInfoSchema.Cols.LATESTPRICE,mStock.latestPrice)
          cv.put(StockInfoSchema.Cols.SECTOR, mStock.sector)
          cv.put(StockInfoSchema.Cols.OPEN, mStock.open)
          cv.put(StockInfoSchema.Cols.PERATIO, mStock.peRatio)
          cv.put(StockInfoSchema.Cols.SYMBOL,mStock.smybol)

        val result = db.insert(StockInfoSchema.NAME,null,cv)
          if(result == (-1).toLong())
          {
            Toast.makeText(context, "The insertData Function failed to insert data into the database.",Toast.LENGTH_SHORT).show()
          }
          else
          {
            Toast.makeText(context, "The Data was successfully added to the database!",Toast.LENGTH_SHORT).show()
          }
      }
    fun readAllData(context:Context, mStocks: ArrayList<stock>) {
        val db = this.readableDatabase
        val query = "Select * from "+StockInfoSchema.NAME
        val result = db.rawQuery(query,null)


        if(result.moveToFirst())
        {
            do {
                val mStock = stock()
                mStock.latestPrice = result.getString(result.getColumnIndex(StockInfoSchema.Cols.LATESTPRICE))
                mStock.smybol = result.getString(result.getColumnIndex(StockInfoSchema.Cols.SYMBOL))
                mStock.sector = result.getString(result.getColumnIndex(StockInfoSchema.Cols.SECTOR))
                mStock.open = result.getString(result.getColumnIndex(StockInfoSchema.Cols.OPEN))
                mStock.close = result.getString(result.getColumnIndex(StockInfoSchema.Cols.CLOSE))
                mStock.companyName= result.getString(result.getColumnIndex(StockInfoSchema.Cols.COMPANYNAME))
                mStock.peRatio = result.getString(result.getColumnIndex(StockInfoSchema.Cols.PERATIO))
                mStocks.add(mStock)

            } while(result.moveToNext())
        }
        result.close()
        db.close()

    }

}

