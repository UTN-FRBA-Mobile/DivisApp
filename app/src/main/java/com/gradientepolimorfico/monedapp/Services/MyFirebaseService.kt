package com.gradientepolimorfico.monedapp.Services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import android.util.Log
import com.google.gson.JsonObject
import com.gradientepolimorfico.monedapp.R
import org.json.JSONObject

class MyFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage!!.data.size > 0) {
            if(remoteMessage!!.data.containsKey("notification")){
                var moreData = remoteMessage!!.data.get("moredata")
                this.crearNotificacionEspecial(remoteMessage,moreData)
            }
        }

        if (remoteMessage.notification != null) {

            val body = remoteMessage.notification!!.body

            if(Preferencias.notificacionesEstanActivas(this)){
                if(body!!.contains("AVISO!")) {
                    if (Preferencias.notificacionesImportantesEstanActivas(this)){
                        this.mostrarNotificacion(body,null,remoteMessage.notification!!.title)
                    }
                } else {
                    this.mostrarNotificacion(body, null, remoteMessage.notification!!.title)
                }
            }
        }
    }

    private fun crearNotificacionEspecial(remoteMessage: RemoteMessage?,codigoDivisa : String?){
        var notificacion = remoteMessage!!.data.get("notification")
        var json = JSONObject(notificacion)
        this.mostrarNotificacion(json.get("body").toString(),codigoDivisa,json.getString("title"))
    }

    private fun mostrarNotificacion(messageBody: String?, codigoDivisa : String?, titulo : String?){
        val pendingIntent = this.pendingIntent(codigoDivisa)
        val builder = this.getNotificationBuilder(messageBody, pendingIntent,titulo)
        val manager = this.notificationManager()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val notificationId = 0
        manager.notify(notificationId, builder.build())
    }

    private fun getNotificationBuilder(messageBody: String?, pendingIntent: PendingIntent, title : String?) : NotificationCompat.Builder{
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(this.sonidoDeNotificacion())
                .setVibrate(this.vibracion())
                .setContentIntent(pendingIntent)
        if(title!= null){
            notificationBuilder.setContentTitle(title)
        }
        else{
            notificationBuilder.setContentTitle("DivisApp!")
        }
        return notificationBuilder
    }

    private fun sonidoDeNotificacion() : Uri{
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }

    private fun vibracion() : LongArray {
        return longArrayOf(1000, 1000, 1000, 1000, 1000)
    }

    private fun notificationManager() : NotificationManager{
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun pendingIntent(codigoDivisa: String?) : PendingIntent{
        val intent = Intent(this, MainActivity::class.java)
        if(codigoDivisa!= null){
            intent.putExtra("codigoDivisa",codigoDivisa)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val requestCode = 0
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                PendingIntent.FLAG_ONE_SHOT)
        return pendingIntent
    }

    companion object {
        private val CHANNEL_ID = "CHANNEL_1"
    }
}