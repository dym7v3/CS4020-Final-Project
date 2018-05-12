package com.example.dennismoyseyev.stockinfo


class GameDataModel {
    var tickers: ArrayList<String> = ArrayList()
    var stockNames: ArrayList<String> = ArrayList()
    var sectors: ArrayList<String> = ArrayList()
    var stocks: ArrayList<stock> = ArrayList()


}
class Quotes( val quote: Map<String, Any>)

class stock
{
    var smybol: String = ""
    var companyName: String = ""
    var peRatio: String = ""
    var latestPrice: String = ""
    var sector: String = ""
    var open: String = ""
    var close: String = ""

    constructor(smybol: String, companyName: String, peRatio: String, latestPrice: String,  sector: String, open: String,  close: String)
    {
        this.close=close
        this.companyName=companyName
        this.open = open
        this.peRatio = peRatio
        this.sector = sector
        this.smybol = smybol
        this.latestPrice =latestPrice

    }
    constructor()
    {

    }
}



