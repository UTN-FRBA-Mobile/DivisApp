package com.gradientepolimorfico.monedapp.Services

import com.github.mikephil.charting.data.Entry
import java.util.*

class ExchangeRateResponse {
    var fromCode: String? = null
    var toCode: String? = null
    var exchangeRate: Float? = null
    var lastRefreshed: String? = null
    var data = ArrayList<Entry>()
}

//{
//    "Realtime Currency Exchange Rate": {
//        "1. From_Currency Code": "JPY",
//        "2. From_Currency Name": "Japanese Yen",
//        "3. To_Currency Code": "ARS",
//        "4. To_Currency Name": "Argentine Peso",
//        "5. Exchange Rate": "0.32313700",
//        "6. Last Refreshed": "2018-10-22 23:35:08",
//        "7. Time Zone": "UTC"
//    }
//}