package divisapp.dadm.frba.utn.edu.ar.divisapp.Services;
import java.util.List;
import divisapp.dadm.frba.utn.edu.ar.divisapp.Entities.Divisa;

public class MonedasServiceResponse {
    private List<Divisa> divisas;

    public List<Divisa> monedas(){
        return this.divisas;
    }
}
