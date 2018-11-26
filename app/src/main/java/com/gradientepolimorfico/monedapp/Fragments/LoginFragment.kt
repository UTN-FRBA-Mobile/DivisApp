package com.gradientepolimorfico.monedapp.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.Profile
import com.facebook.ProfileTracker
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias


class LoginFragment : Fragment() {

    var btnFB: LoginButton? = null
    var btnMail: Button? = null
    var btnInvitado: Button? = null
    var profileTracker: ProfileTracker? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((this.context!! as MainActivity).callbackManager?.onActivityResult(requestCode, resultCode, data)!!) {
            return
        }
    }

    private fun inicializarBotones(vista: View) {
        this.btnFB = vista.findViewById(R.id.btnFB)
        this.btnFB?.setFragment(this)
        this.btnMail = vista.findViewById(R.id.btnMail)
        this.btnInvitado = vista.findViewById(R.id.btnGuest)

        this.btnFB?.setReadPermissions("public_profile")

        this.btnFB?.registerCallback((this.context!! as MainActivity).callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                var nombre: String? = null
                profileTracker = object : ProfileTracker() {
                    override fun onCurrentProfileChanged(oldProfile: ContactsContract.Profile, currentProfile: ContactsContract.Profile?) {
                        if (currentProfile != null) {
                            Preferencias.setUsername(context!!, currentProfile.name) // TODO: VER PORQUE NO CARGA EL NOMBRE
                        }
                    }
                }
                profileTracker?.stopTracking()
                Preferencias.setLogged(context!!, true)
                Preferencias.setLoginFrom(context!!, getString(R.string.usuario_de_facebook))
                Preferencias.setTokenFacebook(context!!, loginResult.accessToken.token)
                (context!! as MainActivity).irAPerfil()
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(activity, "Ocurrio un error: " + error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        this.btnFB?.setOnClickListener {

        }

        val addSaldoDialog = MailDialogFragment()
        this.btnMail?.setOnClickListener (View.OnClickListener { addSaldoDialog.show(this.childFragmentManager!!, "Divisas") })

        this.btnInvitado?.setOnClickListener {
            Preferencias.setLogged(this.context!!, true)
            Preferencias.setUsername(this.context!!, getString(R.string.str_invitado))
            Preferencias.setLoginFrom(this.context!!, getString(R.string.usuario_invitado))
            (this.context!! as MainActivity).irAPerfil()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var vista: View? = null
        if (Preferencias.isLogged(this.context!!)) {
            (this.context!! as MainActivity).irAPerfil()
        } else {
            vista = inflater.inflate(R.layout.fragment_loginoptions, container, false)
            inicializarBotones(vista)

        }
        return vista
    }


    private var listener: OnFragmentInteractionListener? = null

    //1
    override fun onAttach(context: Context) {
        super.onAttach(context)
/*        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    //2
    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val botonLogin = view.findViewById<Button>(R.id.botonLogin)
        botonLogin.setOnClickListener({ onLogin() })

        return view
    }
*/
    //3
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun onLogin() {
        val nombreDeUsuario = activity!!.findViewById<EditText>(R.id.nombreDeUsuario)
        val contrasenia = activity!!.findViewById<EditText>(R.id.contrasenia)

        listener?.onLogin(nombreDeUsuario.text.toString(), contrasenia.text.toString())
    }

    interface OnFragmentInteractionListener {
        fun onLogin(nombreDeUsuario: String, contrasenia: String)
    }
}