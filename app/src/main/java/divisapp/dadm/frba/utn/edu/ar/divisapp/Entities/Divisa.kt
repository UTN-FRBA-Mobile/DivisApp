package divisapp.dadm.frba.utn.edu.ar.divisapp.Entities

class Divisa {
    var id:     Integer?    = null
    var tipo:   String?     = null
    var fecha:  String?     = null
    var valor:  Float?      = null
    var pais:   String?     = null
    var moneda: String?     = null
    var bandera:Int?        = null

    constructor(pais:String, moneda:String, bandera:Int, valor:Float){
        this.pais       = pais
        this.moneda     = moneda
        this.bandera    = bandera
        this.valor      = valor
    }
}