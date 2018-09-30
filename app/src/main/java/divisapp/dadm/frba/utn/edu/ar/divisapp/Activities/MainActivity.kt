package divisapp.dadm.frba.utn.edu.ar.divisapp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import divisapp.dadm.frba.utn.edu.ar.divisapp.Fragments.DivisasFragment
import divisapp.dadm.frba.utn.edu.ar.divisapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.irAPrincipal()
    }

    private fun irAPrincipal(){
        val divisasFragment = DivisasFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, divisasFragment).commit()
    }

    fun recuperarParametrosDelIntent(parametros: ArrayList<String>){
        parametros.add(intent.getStringExtra(PARAMETRO_NOMBREDEUSUARIO))
        parametros.add(intent.getStringExtra(PARAMETRO_CONTRASENIA))
    }

    companion object {
        const val PARAMETRO_NOMBREDEUSUARIO = "NOMBREDEUSUARIO"
        const val PARAMETRO_CONTRASENIA     = "CONTRASENIA"
    }
}
