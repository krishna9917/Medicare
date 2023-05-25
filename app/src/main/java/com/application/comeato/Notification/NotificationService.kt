package com.application.comeato.Notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.application.comeato.Activity.Login.LoginActivity
import com.application.comeato.Activity.MembershipPlan.MembershipPlanActivity
import com.application.comeato.Activity.MyDeals.MyDealsActivity
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.comeato.Activity.Home.HomeActivity
import com.comeato.Utilities.MyApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationService: FirebaseMessagingService()
{
    var channel_id = "notification_channel"
    private val now = Date()

    override fun onNewToken(token: String) {}

    private var intent: Intent? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        // notification_typ 1 for One user
        // notification_typ 2 for all user
        if (remoteMessage.notification != null && MyLocalMemory.getBoolean(MyApp.MySingleton.IS_LOGIN) && remoteMessage.data["notification_type"] == "1" && remoteMessage.data["user_id"] =="1") {
            setNotificationOperation(remoteMessage)
        } else if (remoteMessage.data["notification_type"] == "2") {
            setNotificationOperation(remoteMessage)
        }
    }

    private fun setNotificationOperation(remoteMessage: RemoteMessage) {
        var notifyCount: Int = MyLocalMemory.getInteger(MyApp.MySingleton.NOTIFICATION_COUNT)
        notifyCount += 1
        MyLocalMemory.putInteger(MyApp.MySingleton.NOTIFICATION_COUNT, notifyCount)

        // Broad cast in App
        val broadCastIntent = Intent(getString(R.string.notificationBroadcaster))
        broadCastIntent.putExtra(getString(R.string.notificationCount), notifyCount)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadCastIntent)
        if (MyLocalMemory.getBoolean(MyApp.MySingleton.IS_LOGIN)) {
            if (remoteMessage.data["notification_type"] == "2")
            {
                intent = Intent(this, HomeActivity::class.java)

            } else
            {
                when (remoteMessage.data[getString(R.string.intent_type)]!!.toInt()) {
                    0 -> {
                        intent = Intent(this, MyDealsActivity::class.java)
                    }

                    1 -> {
                        intent = Intent(this, MembershipPlanActivity::class.java)

                    }

                    else -> intent = Intent(this, HomeActivity::class.java)
                }
            }
        } else {
            intent = Intent(this, LoginActivity::class.java)
        }
        channel_id = SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt().toString()
        showNotification(
            remoteMessage.notification!!.title, remoteMessage.notification!!
                .body, remoteMessage.notification!!.imageUrl
        )
    }



    fun showNotification(title: String?, message: String?, imageUrl: Uri?) {
        createChannel(message)
        val builder = NotificationCompat.Builder(applicationContext, channel_id)
        builder.setSmallIcon(R.drawable.logo)
        builder.setContentTitle(title)
        builder.setContentText(message)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setOnlyAlertOnce(true)
        if (imageUrl != null) {
            if (imageUrl.toString().length > 10 && imageUrl.toString().startsWith("http")) {
                val bitmap = getBitmapFromUrl(imageUrl)
                builder.setLargeIcon(bitmap)
                builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            }
        }
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setAutoCancel(true)
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                this,
                SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        builder.setContentIntent(pendingIntent)
        val managerCompat = NotificationManagerCompat.from(
            applicationContext
        )
        val NOTIFICATION_ID = SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                managerCompat.notify(NOTIFICATION_ID, builder.build())
            }
        } else {
            managerCompat.notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun getBitmapFromUrl(imageUrl: Uri): Bitmap? {
        return try {
            val url = URL(imageUrl.toString())
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }


    private fun createChannel(message: String?) {
        val channel_name = getString(R.string.app_name) + SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = message
            val manager =
                applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}