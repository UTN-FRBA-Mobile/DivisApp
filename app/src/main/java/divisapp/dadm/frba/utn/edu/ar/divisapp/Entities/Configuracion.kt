package divisapp.dadm.frba.utn.edu.ar.divisapp.Entities

class Configuracion{
    var id: Int? = null
    var usuario: Usuario? = null
    var divisaBase: Divisa? = null
    var porcentajeON: Boolean = false
    var intervalo: Long? = null
    var divisas: ArrayList<Divisa> = ArrayList()

}