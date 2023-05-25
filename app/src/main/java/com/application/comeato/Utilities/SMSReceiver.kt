package com.application.comeato.Utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import java.util.regex.Pattern

class SMSReceiver: BroadcastReceiver()
{
    private val SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED"

    override fun onReceive(p0: Context?, intent: Intent?) {
       if(intent?.action==SMS_RECEIVED_ACTION)
       {
           val bundle = intent?.extras
           if(bundle != null)
           {
               val pdus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                   bundle.getParcelableArrayList("pdus",Any::class.java) as Array<Any>?
               } else {
                   @Suppress("DEPRECATION")
                   bundle.get("pdus") as  Array<Any>?
               }
               pdus?.let {
                   for (pdu in pdus)
                   {
                       val pduByteArray = pdu as ByteArray

                       val smsMessage: SmsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                          SmsMessage.createFromPdu(pdu, bundle.getString("format"))
                       } else {
                           @Suppress("DEPRECATION")
                           SmsMessage.createFromPdu(pduByteArray)
                       }
                       val sender = smsMessage.displayOriginatingAddress
                       val messageBody = smsMessage.messageBody

                       val pattern = Pattern.compile("\\b\\d{6}\\b")
                       val matcher = pattern.matcher(messageBody)
                       if (matcher.find()) {
                           val otp = matcher.group()

                           Log.d("OTP-->", "onReceive: "+otp)

                       }

                   }
               }
           }
           }
       }

}