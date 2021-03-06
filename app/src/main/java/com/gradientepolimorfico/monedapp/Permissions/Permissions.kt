package com.gradientepolimorfico.monedapp.Permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

object Permissions {
    val REQUEST_WRITE_EXTERNAL_STORAGE = 1

    val REQUEST_USE_GPS = 2

    fun hasPermissions(activity: Activity, permissionCode: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permissionCode) != PackageManager.PERMISSION_GRANTED
    }

    fun checkForPermissions(activity: Activity, permissionCode: String, reason: String, callback: Callback) {
        if (hasPermissions(activity, permissionCode)) {
            if (activity.shouldShowRequestPermissionRationale(permissionCode)) {
                showStoragePermissionExplanation(activity, permissionCode, reason)
            } else {
                dispatchStoragePermissionRequest(activity, permissionCode)
            }
        } else {
            callback.onSuccess()
        }
    }

    private fun dispatchStoragePermissionRequest(activity: Activity, permissionCode: String) {
        activity.requestPermissions(arrayOf(permissionCode), REQUEST_USE_GPS)
    }

    private fun showStoragePermissionExplanation(activity: Activity, permissionCode: String, reason: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Necesitamos tu permiso")
        builder.setCancelable(true)
        builder.setMessage(reason)
        builder.setPositiveButton("Aceptar") { dialogInterface, i -> dispatchStoragePermissionRequest(activity, permissionCode) }
        builder.show()
    }

    interface Callback {
        fun onSuccess()
    }
}
