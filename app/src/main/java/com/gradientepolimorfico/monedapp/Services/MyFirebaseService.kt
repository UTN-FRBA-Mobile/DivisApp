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
import com.gradientepolimorfico.monedapp.R

class MyFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        //Log.d("I","MyFireBase - "+"MENSAJE RECIBIDO")
        if (remoteMessage!!.data.size > 0) {
            //To Do
        }

        if (remoteMessage.notification != null) {
            if(Preferencias.notificacionesEstanActivas(this)){
                this.mostrarNotificacion(remoteMessage.notification!!.body)
            }
        }
    }

    private fun mostrarNotificacion(messageBody: String?){
        val pendingIntent = this.pendingIntent()
        val builder = this.getNotificationBuilder(messageBody, pendingIntent)
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

    private fun getNotificationBuilder(messageBody: String?, pendingIntent: PendingIntent ) : NotificationCompat.Builder{
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("DivisApp!")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(this.sonidoDeNotificacion())
                .setVibrate(this.vibracion())
                .setContentIntent(pendingIntent)
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

    private fun pendingIntent() : PendingIntent{
        val intent = Intent(this, MainActivity::class.java)
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