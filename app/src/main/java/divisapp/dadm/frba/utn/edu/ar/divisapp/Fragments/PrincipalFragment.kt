package divisapp.dadm.frba.utn.edu.ar.divisapp.Fragments

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import divisapp.dadm.frba.utn.edu.ar.divisapp.Entities.Usuario
import kotlinx.android.synthetic.main.fragment_divisa_precioactual.*
import kotlinx.android.synthetic.main.fragment_divisa_precioactual.view.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

class PrincipalFragment : Fragment() {
    //1
    var usuario = Usuario()

   /* fun inicializarTode(){ // a modo de ejemple
        usuario.nombreDeUsuario = "Steve"
        var configuracion = Configuracion()
        var divisaBase = Divisa()
        divisaBase.tipo = "Pesos"
        var divisaUno = Divisa()
        divisaUno.tipo = "dolar"
        var divisaDos = Divisa()
        divisaDos.tipo = "euro"
        var divisaTres = Divisa()
        divisaTres.tipo = "yen"
        configuracion.divisaBase = divisaBase;
        configuracion.divisas.add(divisaUno)
        configuracion.divisas.add(divisaDos)
        configuracion.divisas.add(divisaTres)
        usuario.configuracion = configuracion
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        inicializarTode()
        var vista = inflater.inflate(R.layout.fragment_divisa_precioactual, container, false)
        vista.refresh.setOnClickListener {
            val url = "https://cdn.rawgit.com/adevesa/stevetricks/master/ejemplo.json"
            MyAsyncTask().execute(url,usuario.configuracion?.divisas?.first()?.tipo) } //a modo de ejemple
        return vista
    }

    open fun getInfo(fecha: String, tipo: String?, precio: Double ){

        var divisa = Divisa()
        divisa.fecha = fecha
        divisa.tipo = tipo
        divisa.valor = precio.toFloat()

        tvDivisa.text = divisa.tipo
        tvPrecioActual.text = "ARS \$${divisa.valor}"
        tvDiaHora.text = fecha
    }

    inner class MyAsyncTask: AsyncTask<String, String, String>(){


        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: String?): String {
            try {
                var url = URL(p0[0])
                var tipo = p0[1]

                val urlConnect = url.openConnection() as HttpsURLConnection
                urlConnect.connectTimeout=7000

                var inString = ConvertStreamToString(urlConnect.inputStream)
                publishProgress(inString, tipo)
            }catch (ex:Exception){}

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var json= JSONObject(values[0])
                var tipo:String?= values[1]
                val moneda = json.getJSONObject(tipo)
                val valorActual = moneda.getJSONObject("0")
                var fecha:String = valorActual.getString("fecha")
                var fechaDate:Date = convertirADateTime(fecha)
                var fechaFormateada = convertirFechaLegible(fechaDate)
                val valor = valorActual.getDouble("valor")


                getInfo(fechaFormateada,tipo,valor)

            }catch (ex:Exception){}
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }

        fun ConvertStreamToString(inputStream: InputStream):String{

            val bufferReader= BufferedReader(InputStreamReader(inputStream))
            var line:String
            var AllString:String=""

            try {
                do{
                    line=bufferReader.readLine()
                    if(line!=null){
                        AllString+=line
                    }
                }while (line!=null)
                inputStream.close()
            }catch (ex:Exception){}

            return AllString
        }

        fun convertirADateTime(fecha:String):Date{
            val anio:Int     = fecha.substring(0,4).toInt()
            val mes:Int      = fecha.substring(4,6).toInt()
            val dia:Int      = fecha.substring(6,8).toInt()
            val hora:Int     = fecha.substring(8,10).toInt()
            val minutos:Int  = fecha.substring(10,12).toInt()
            val segundos:Int = fecha.substring(12,14).toInt()

            return Date(anio,mes,dia,hora,minutos,segundos)
        }

        fun convertirFechaLegible(fecha:Date): String{
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(fecha)
        }
    }*/
}