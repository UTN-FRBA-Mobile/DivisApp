package divisapp.dadm.frba.utn.edu.ar.divisapp.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import divisapp.dadm.frba.utn.edu.ar.divisapp.Fragments.LoginFragment
import divisapp.dadm.frba.utn.edu.ar.divisapp.Fragments.SplashFragment
import divisapp.dadm.frba.utn.edu.ar.divisapp.R


class SplashActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener {
    override fun onLogin(nombreDeUsuario: String, contrasenia: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.PARAMETRO_NOMBREDEUSUARIO, nombreDeUsuario)
        intent.putExtra(MainActivity.PARAMETRO_CONTRASENIA,contrasenia)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashFragment = SplashFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, splashFragment).commit()

        val background = object : Thread() {
            override fun run() {
                Thread.sleep((2 * 1000).toLong())

                val loginFragment = LoginFragment()

                supportFragmentManager.beginTransaction().remove(splashFragment).add(R.id.fragment_container, loginFragment).commit()
            }
        }
        background.start()
    }
}
