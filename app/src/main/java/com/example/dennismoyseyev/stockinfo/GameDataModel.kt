package com.example.dennismoyseyev.stockinfo


class GameDataModel {
    var tickers: ArrayList<String> = ArrayList()
    var stockNames: ArrayList<String> = ArrayList()
    var sectors: ArrayList<String> = ArrayList()


}
class Quotes( val quote: Map<String, Any>)

class stock(val smybol: String, val companyName: String, val peRatio: String, val latestPrice: String, val sector: String, val open: String, val close: String )


