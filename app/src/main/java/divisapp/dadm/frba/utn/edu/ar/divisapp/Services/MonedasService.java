package divisapp.dadm.frba.utn.edu.ar.divisapp.Services;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MonedasService {
    @GET("dolartoday/data.json")
    Observable <MonedasServiceResponse> cotizacionesDelDia();
}
