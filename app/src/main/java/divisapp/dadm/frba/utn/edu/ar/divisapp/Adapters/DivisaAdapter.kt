package divisapp.dadm.frba.utn.edu.ar.divisapp.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import divisapp.dadm.frba.utn.edu.ar.divisapp.Entities.Divisa
import divisapp.dadm.frba.utn.edu.ar.divisapp.R
import kotlinx.android.synthetic.main.fragment_divisa.view.*

class DivisaAdapter: RecyclerView.Adapter<DivisaAdapter.MyViewHolder>{
    private var divisas:ArrayList<Divisa>? = null

    constructor(divisas:ArrayList<Divisa>) : super(){
        this.divisas = divisas
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivisaAdapter.MyViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.fragment_divisa,parent,false)
        return MyViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return this.divisas!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var unaDivisa = this.getDivisa(position)
        holder.view.findViewById<TextView>(R.id.tvPais).text = unaDivisa.pais
        holder.view.findViewById<TextView>(R.id.tvDivisa).text = unaDivisa.moneda
        holder.view.findViewById<TextView>(R.id.tvValorDivisa).text = "$"+unaDivisa.valor.toString()
        holder.view.findViewById<ImageView>(R.id.iwBandera).setImageResource(unaDivisa.bandera!!)
    }

    private fun getDivisa(position: Int) : Divisa{
        return this.divisas!!.get(position)
    }
}

/*class DivisaAdapter: BaseAdapter {
    private var divisas:ArrayList<Divisa>? = null
    private var context:Context? = null

    constructor(context: Context, divisas:ArrayList<Divisa>) : super(){
        this.divisas = divisas
        this.context = context
    }

    private fun getDivisa(position: Int) : Divisa{
        return this.divisas!!.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var unaDivisa = this.getDivisa(position)
        var inflator = this.context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var vistaDeDivisa = inflator.inflate(R.layout.fragment_divisa, null)
        vistaDeDivisa.iwBandera.setImageResource(unaDivisa.bandera!!)
        vistaDeDivisa.tvPais.text = unaDivisa.pais
        vistaDeDivisa.tvDivisa.text = unaDivisa.moneda
        vistaDeDivisa.tvValorDivisa.text = "$"+unaDivisa.valor.toString()
        return vistaDeDivisa
    }

    override fun getItem(position: Int): Any {
        return this.divisas!!.get(position)
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
        return this.divisas!!.size
    }

}*/