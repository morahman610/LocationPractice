package com.group.ramp.locationpractice

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Build.VERSION.SDK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var locationManager: LocationManager
    private var gpsCapable = false
    private var networkCapable = false
    private lateinit var gpsLocation : Location
    private lateinit var networkLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkPermission(permissions)) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                requestPermissions(permissions, 0)
            }
        }
    }

    private fun enableView() {

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsCapable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        networkCapable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(gpsCapable || networkCapable) {

            if(gpsCapable) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            gpsLocation = location
                        }
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderEnabled(p0: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderDisabled(p0: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

                var localGPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if(localGPSLocation != null) {
                    gpsLocation = localGPSLocation
                }
            }

            if(networkCapable) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            gpsLocation = location
                        }
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderEnabled(p0: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderDisabled(p0: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

                var localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(localNetworkLocation != null) {
                    gpsLocation = localNetworkLocation
                }
            }

            if (gpsLocation != null && networkLocation != null) {

            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun checkPermission(permissionArray: Array<String>) : Boolean {
        var permissionSuccess = true
        for(i in permissionArray.indices) {
            if(checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                permissionSuccess = false
        }
        return permissionSuccess
    }
}