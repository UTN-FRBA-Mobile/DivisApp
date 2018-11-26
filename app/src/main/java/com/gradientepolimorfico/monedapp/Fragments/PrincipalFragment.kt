package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.R

@Deprecated("No se usa más")
class PrincipalFragment : Fragment() {

    private var divisaIndex: Int? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var vista = inflater.inflate(R.layout.fragment_divisa_precioactual, container, false)

        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.divisaIndex = arguments!!.getInt("DivisaIndex")
        var mainActivity = context as MainActivity
        var divisa = mainActivity.getDivisaByPosition(arguments!!.getInt("DivisaIndex"))

        mainActivity.findViewById<TextView>(R.id.tvDivisa).text = divisa.moneda
        mainActivity.findViewById<TextView>(R.id.tvPrecioActual).text = "ARS \$${divisa.valor}"
        mainActivity.findViewById<TextView>(R.id.tvDiaHora).text = divisa.fecha
    }

//    open fun getInfo(fecha: String, tipo: String?, precio: Double ){
//
//        var divisa = Divisa()
//        divisa.fecha = fecha
//        divisa.codigo = tipo
//        divisa.valor = precio.toFloat()
//
//        tvDivisa.text = divisa.codigo
//        tvPrecioActual.text = "ARS \$${divisa.valor}"
//        tvDiaHora.text = fecha
//    }
//
//    inner class MyAsyncTask: AsyncTask<String, String, String>(){
//
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//        override fun doInBackground(vararg p0: String?): String {
//            try {
//                var url = URL(p0[0])
//                var tipo = p0[1]
//
//                val urlConnect = url.openConnection() as HttpURLConnection
//                urlConnect.connectTimeout=7000
//
//                var inString = ConvertStreamToString(urlConnect.inputStream)
//                publishProgress(inString, tipo)
//            }catch (ex:Exception){
//                Log.e("ERROR","ERROR EN DOINBACKGROUND ${ex}")}
//
//            return " "
//        }
//
//        override fun onProgressUpdate(vararg values: String?) {
//            try {
//                var json= JSONObject(values[0])
//                var tipo:String?= values[1]
//                val moneda = json.getJSONObject(tipo)
//                val valorActual = moneda.getJSONObject("0")
//                var fecha:String = valorActual.getString("fecha")
//                var fechaDate:Date = convertirADateTime(fecha)
//                var fechaFormateada = convertirFechaLegible(fechaDate)
//                val valor = valorActual.getDouble("valor")
//
//
//                getInfo(fechaFormateada,tipo,valor)
//
//            }catch (ex:Exception){Log.e("ERROR","ERROR EN onProgressUpdate ${ex}")}
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//        }
//
//        fun ConvertStreamToString(inputStream: InputStream):String{
//
//            val bufferReader= BufferedReader(InputStreamReader(inputStream))
//            var line:String
//            var AllString:String=""
//
//            try {
//                do{
//                    line=bufferReader.readLine()
//                    if(line!=null){
//                        AllString+=line
//                    }
//                }while (line!=null)
//                inputStream.close()
//            }catch (ex:Exception){Log.e("ERROR","ERROR EN ConvertStreamToString ${ex}")}
//
//            return AllString
//        }
//
//        fun convertirADateTime(fecha:String):Date{
//            val anio:Int     = fecha.substring(0,4).toInt()
//            val mes:Int      = fecha.substring(4,6).toInt()
//            val dia:Int      = fecha.substring(6,8).toInt()
//            val hora:Int     = fecha.substring(8,10).toInt()
//            val minutos:Int  = fecha.substring(10,12).toInt()
//            val segundos:Int = fecha.substring(12,14).toInt()
//
//            return Date(anio-1900,mes-1,dia,hora,minutos,segundos)
//        }
//
//        fun convertirFechaLegible(fecha:Date): String{
//            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            return format.format(fecha)
//        }
//    }
}