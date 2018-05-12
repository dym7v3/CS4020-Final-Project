package com.example.dennismoyseyev.stockinfo

object StockInfoSchema {
    const val NAME = "stock"
    object Cols {
        const val ID = "id"
        const val SYMBOL = "symbol"
        const val COMPANYNAME = "companyName"
        const val SECTOR = "sector"
        const val PERATIO = "peRatio"
        const val LATESTPRICE = "latestPrice"
        const val OPEN = "open"
        const val CLOSE = "close"
    }
}