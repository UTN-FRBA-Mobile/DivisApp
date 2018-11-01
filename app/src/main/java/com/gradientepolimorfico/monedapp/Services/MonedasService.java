package com.gradientepolimorfico.monedapp.Services;


import com.gradientepolimorfico.monedapp.Entities.Divisa;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MonedasService {
    @GET("/query?function=CURRENCY_EXCHANGE_RATE&apikey=QHMCIG8LM0A0UB40")
    Call<ExchangeRateResponse> getExchangeInfo(@Query("from_currency") String currency,
                                 @Query("to_currency") String baseCurrency);

    @GET("/query?function=FX_DAILY&apikey=QHMCIG8LM0A0UB40")
    Call<ExchangeRateResponse> getTimeSeries(@Query("from_symbol") String currency,
                                               @Query("to_symbol") String baseCurrency);
}
