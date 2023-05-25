package com.application.comeato.Utilities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.application.comeato.Interfaces.CurrentLocationListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.databinding.LayoutCustomtoastBinding
import com.comeato.Utilities.MyApp
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.OnCompleteListener
import java.util.Locale


class UtilsFunction {

    companion object {

        fun setAnimation(context: Context?, viewToAnimate: View, animationId: Int) {
            val animation = AnimationUtils.loadAnimation(context, animationId)
            viewToAnimate.startAnimation(animation)
        }

        fun setPasswordToggleVisibility(
            password: Boolean,
            imgPasswordToggle: ImageView,
            editField: EditText
        ): Boolean {
            var passwordVisible = password
            if (!passwordVisible) {
                passwordVisible = true
                imgPasswordToggle.setImageDrawable(
                    ContextCompat.getDrawable(
                        MyApp.MySingleton.getAppContext(),
                        R.drawable.ic_hide_eye
                    )
                )
                editField.inputType = InputType.TYPE_CLASS_TEXT
                editField.setSelection(editField.text.toString().length)

            } else {
                passwordVisible = false
                imgPasswordToggle.setImageDrawable(
                    ContextCompat.getDrawable(
                        MyApp.MySingleton.getAppContext(),
                        R.drawable.ic_eye
                    )
                )
                editField.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editField.setSelection(editField.text.toString().length)

            }
            return passwordVisible
        }

        fun isInternetConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // For devices running Android Q and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val capabilities =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
            // For devices running below Android Q
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }

        fun showToast(context: Context, message: String) {
            val toastBinding = LayoutCustomtoastBinding.inflate(LayoutInflater.from(context))
            toastBinding.cvToast.setCardBackgroundColor(Color.GRAY)
            val mToast = Toast(context)
            mToast.view = toastBinding.root
            toastBinding.message = message
            mToast.setGravity(Gravity.BOTTOM, 0, 50)
            mToast.duration = Toast.LENGTH_LONG
            mToast.show()
        }


        fun showError(context: Context, message: String)
        {
              showToast(context,message)
              vibration(context)
        }

        fun vibration(context: Context)
        {
            var vibrator: Vibrator
            var vibratorManager: VibratorManager
            if (Build.VERSION.SDK_INT>=31) {
                vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibrator = vibratorManager.defaultVibrator
            }
            else {
                vibrator =  context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE))
            }else{
                //deprecated in API 26
                vibrator.vibrate(500);
            }
        }


        fun  contactDialer(contact:String)
        {
            val dialPhone = Intent(Intent.ACTION_DIAL)
            dialPhone.data = Uri.parse("tel:"+contact)
            dialPhone.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyApp.MySingleton.getAppContext().startActivity(dialPhone)
        }

        fun openMapByAddress(address:String,context: Context)
        {
            try {
                val map = "http://maps.google.co.in/maps?q=${address}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
              context.startActivity(intent)
            }catch (ex: ActivityNotFoundException)
            {
                showToast(MyApp.MySingleton.getAppContext(),"There are no Map client installed on your device.")
            }
        }


        fun  sendEmail(email:String,context:Context)
        {
            try {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                intent.putExtra(Intent.EXTRA_SUBJECT, "App feedback")
               context.startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                showToast(context,"There are no email client installed on your device.")
            }
        }


        fun shareText(text: String,context: Context)
        {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(shareIntent,"Share with:"))
        }



        lateinit var locationListener: CurrentLocationListener
        fun getLastLocation(context: Context, locListener: CurrentLocationListener)
        {

            locationListener = locListener
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isLocationEnabled(context))
                {
                    fusedLocationClient.lastLocation
                        .addOnCompleteListener(OnCompleteListener<Location?> { task ->
                            val location = task.result

                            if (location == null)
                            {

                                val locationRequest =
                                    LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5)
                                        .setWaitForAccurateLocation(false)
                                        .setMinUpdateIntervalMillis(0)
                                        .setMaxUpdateDelayMillis(5)
                                        .build()

                                if (isLocationPermissionGranted(context))

                                    fusedLocationClient.requestLocationUpdates(
                                        locationRequest,
                                        mLocationCallback,
                                        Looper.myLooper()
                                    )
                            } else
                            {
                                getCityNameFromLocation(location.latitude, location.longitude)
                            }
                        })
                } else {
                    showToast(context, "Please turn on" + " your location...")
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    context.startActivity(intent)
                }
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    101
                )
            }
        }

        public fun isLocationPermissionGranted(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        private fun isLocationEnabled(context: Context): Boolean {
            val locationManager =
                context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

        private val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val mLastLocation = locationResult.lastLocation
                getCityNameFromLocation(mLastLocation?.latitude!!, mLastLocation?.longitude!!)
            }
        }

        private fun getCityNameFromLocation(latitude: Double, longitude: Double) {
            val local = Locale("en_us", "United States")
            val geocoder = Geocoder(MyApp.MySingleton.getAppContext(), local)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            {
                geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>)
                    {
                        val city = addresses?.first()?.locality!!
                        val state=addresses?.get(0)?.adminArea!!
                        MyLocalMemory.putString(MyApp.MySingleton.CURRENT_CITY, city)
                        MyLocalMemory.putString(MyApp.MySingleton.CURRENT_STATE,state)
                        locationListener.onGetCurrentLocation("$city,$state")

                    }
                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        errorMessage?.let { locationListener.onError(it) }

                    }
                })
            } else {
                val address = geocoder.getFromLocation(latitude, longitude, 1)
                val city = address?.first()?.locality!!
                val state=address?.get(0)?.adminArea!!
                MyLocalMemory.putString(MyApp.MySingleton.CURRENT_CITY,city)
                MyLocalMemory.putString(MyApp.MySingleton.CURRENT_STATE,state)
                locationListener.onGetCurrentLocation("$city,$state")

            }
        }
    }

}