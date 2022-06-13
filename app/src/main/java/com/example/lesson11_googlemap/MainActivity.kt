package com.example.lesson11_googlemap

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.os.persistableBundleOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(),OnMapReadyCallback{
    var currentlocation:Location?=null
    var fusedLocationProviderClient:FusedLocationProviderClient?=null
    private val REQUEST_CODE=101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     //initialize fused location
     fusedLocationProviderClient= LocationServices.
     getFusedLocationProviderClient(this)

      appUserMap()

    }
    private fun appUserMap() {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
       !=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
        (this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
    !=PackageManager.PERMISSION_GRANTED){
       ActivityCompat.requestPermissions(
           this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
       REQUEST_CODE
           )
       return
   }
   //initialize task location
   val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location-> if(location !=null){ currentlocation = location
        val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment?)
        supportMapFragment!!.getMapAsync(this)
        }
        }
        }

    override fun onMapReady(mMap:GoogleMap){
     val myUserLocation = LatLng(currentlocation!!.latitude,currentlocation!!.longitude) 
     val markerOptions = MarkerOptions().position(myUserLocation).title("$myUserLocation")
        
     //zoom map
     mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myUserLocation,15f))   
     
    //add the maker on the map
        mMap.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions, grantResults
        )
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    appUserMap()
                }
            }
        }
    }
    
    }





