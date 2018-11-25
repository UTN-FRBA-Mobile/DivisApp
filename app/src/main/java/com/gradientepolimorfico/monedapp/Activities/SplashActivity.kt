package com.gradientepolimorfico.monedapp.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gradientepolimorfico.monedapp.Fragments.LoginFragment
import com.gradientepolimorfico.monedapp.Fragments.SplashFragment
import com.gradientepolimorfico.monedapp.R

class SplashActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener
{
    override fun onLogin(nombreDeUsuario: String, contrasenia: String) {
        val intent = Intent(this, MainActivity::class.java)
        //intent.putExtra(MainActivity.PARAMETRO_NOMBREDEUSUARIO, nombreDeUsuario)
        //intent.putExtra(MainActivity.PARAMETRO_CONTRASENIA,contrasenia)
        startActivity(intent)
        finish()
    }

    fun mostrarMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.activity_splash)

        val splashFragment = SplashFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, splashFragment).commit()

        val background = object : Thread() {
            override fun run() {
                Thread.sleep((2 * 1000).toLong())
                supportFragmentManager.beginTransaction().remove(splashFragment).commit()

                val loginFragment = LoginFragment()
                supportFragmentManager.beginTransaction().remove(splashFragment).add(R.id.fragment_container, loginFragment).commit()
            }
        }
        background.start()*/
        this.mostrarMain()
    }
}
