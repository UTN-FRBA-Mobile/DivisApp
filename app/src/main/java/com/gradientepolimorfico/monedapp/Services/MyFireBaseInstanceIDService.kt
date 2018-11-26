package com.gradientepolimorfico.monedapp.Services

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class MyFireBaseInstanceIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        Preferencias.setFirebaseToken(this, FirebaseInstanceId.getInstance().token!!)
        Log.i("REFRESH", "TOKEN PREFERENCES " + Preferencias.getFirebaseToken(this))
    }
}