package com.gradientepolimorfico.monedapp.Services;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MonedasService {

    @GET("/query?function=CURRENCY_EXCHANGE_RATE")
    Call<ExchangeRateResponse> getExchangeInfo(@Query("from_currency") String currency,
                                               @Query("to_currency") String baseCurrency,
                                               @Query("apikey") String apiKey);

    @GET("/query?function=FX_DAILY")
    Call<ExchangeRateResponse> getTimeSeries(@Query("from_symbol") String currency,
                                             @Query("to_symbol") String baseCurrency,
                                             @Query("apikey") String apiKey);
}
