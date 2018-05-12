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
          cv.put(StockInfoSchema.Cols.SYMBOL,mStock.peRatio)

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

}

//
//class DataBaseHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,1 )
//{
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//
//    }
//
//    override fun onCreate(db: SQLiteDatabase?) {
//
//        val createTable = "CREATE TABLE" + TABLE_NAME + " (" +
//                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
//                COL_SYMBOL+ "VARCHAR(256),"+
//                COL_COMPANY_NAME+ "VARCHAR(256),"+
//                COl_SECTOR+ "VARCHAR(256),"+
//                COL_PERATIO+ "VARCHAR(256),"+
//                COL_LATEST_PRICE+ "VARCHAR(256),"+
//                COL_OPEN+ "VARCHAR(256),"+
//                COL_CLOSE+ "VARCHAR(256))"
//
//        db?.execSQL(createTable)
//
//    }
//
//    fun insertData(stockInfo: StockInfo)
//    {
//        val db = this.writableDatabase
//        var cv = ContentValues()
//
//        cv.put(COL_SYMBOL, stockInfo.symbol)
//        cv.put(COL_CLOSE, stockInfo.close)
//        cv.put(COL_COMPANY_NAME, stockInfo.companyName)
//        cv.put(COl_SECTOR, stockInfo.sector)
//        cv.put(COL_OPEN, stockInfo.open)
//        cv.put(COL_LATEST_PRICE,stockInfo.latestPrice)
//        cv.put(COL_PERATIO,stockInfo.peRatio)
//        val result = db.insert(TABLE_NAME,null,cv)
//        if(result == (-1).toLong())
//        {
//            Toast.makeText(context, "The insertData Function failed to insert data into the database.",Toast.LENGTH_SHORT).show()
//        }
//        else
//        {
//            Toast.makeText(context, "The Data was successfully added to the database!",Toast.LENGTH_SHORT).show()
//        }
//    }
//}

