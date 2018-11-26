package com.gradientepolimorfico.monedapp.Services

import com.github.mikephil.charting.data.Entry
import com.google.gson.*
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.SimpleDateFormat


object RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://www.alphavantage.co/"
    private var indexAPI = 0
    private val keysArray = arrayOf("QHMCIG8LM0A0UB40")

    fun iterateAPIKeys(): String {
        val result = keysArray[indexAPI]
        if (indexAPI == keysArray.size - 1) {
            indexAPI = 0
        } else {
            indexAPI += 1
        }
        return result
    }

    val gson = GsonBuilder()
            .registerTypeAdapter(ExchangeRateResponse::class.java, TimeSeriesAdapter())
            .create()

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit
        }

    class TimeSeriesAdapter : JsonDeserializer<ExchangeRateResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ExchangeRateResponse {
            val exchangeRate = ExchangeRateResponse()
            val jsonObject = json.asJsonObject
            val metaData = jsonObject["Meta Data"]?.asJsonObject
            if (metaData != null) {
                exchangeRate.fromCode = metaData["2. From Symbol"].toString()
                exchangeRate.toCode = metaData["3. To Symbol"].toString()
                exchangeRate.lastRefreshed = metaData["5. Last Refreshed"].toString()
            }
            val timeSeries = jsonObject["Time Series FX (Daily)"]
            val gson = Gson()
            val rawData = gson.fromJson(timeSeries, Any::class.java) as LinkedTreeMap<String, LinkedTreeMap<String, String>>
            val format = SimpleDateFormat("yyyy-MM-dd")
            rawData.forEach { (key, value) -> exchangeRate.data.add(Entry((format.parse(key).time / (60000 * 60 * 24)).toFloat(), value["4. close"]!!.toFloat())) }
            exchangeRate.exchangeRate = exchangeRate.data.first().y

            return exchangeRate
        }

    }

}
