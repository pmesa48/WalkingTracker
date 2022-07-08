package com.pmesa48.pablomesa_challenge.framework.common

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.pmesa48.pablomesa_challenge.framework.common.LocationPermissionManager.PermissionState.*

class LocationPermissionManager {

    companion object {
        val INCLUDED_PERMISSION = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    }

    enum class PermissionState {
        GRANTED, SHOW_RATIONALE, ASK_PERMISSION
    }

    fun hasLocationPermission(activity: Activity): PermissionState {
        return when {
            arePermissionGranted(activity) -> GRANTED
            shouldShowRationale(activity) -> SHOW_RATIONALE
            else -> ASK_PERMISSION
        }
    }

    private fun shouldShowRationale(activity: Activity) =
        INCLUDED_PERMISSION.find { shouldShowRequestPermissionRationale(activity, it) } != null

    private fun arePermissionGranted(activity: Activity) =
        INCLUDED_PERMISSION.all {
            ContextCompat.checkSelfPermission(activity, it) == PERMISSION_GRANTED
        }

    fun requestPermission(locationPermissionRequest: ActivityResultLauncher<Array<String>>) {
        locationPermissionRequest.launch(INCLUDED_PERMISSION)
    }

}