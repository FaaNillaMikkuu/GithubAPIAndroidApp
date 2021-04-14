package com.suhaili.GitHubApp.broadcastreceiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.suhaili.GitHubApp.R
import com.suhaili.GitHubApp.view.MainActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RepeatingAlarm : BroadcastReceiver() {

    companion object {
        private const val ID_REPEAT = 101
        private const val TIME = "HH:mm"
    }

    override fun onReceive(context: Context, intent: Intent) {
        ShowNotification(context)
    }

    fun repeatingAlarm(ctx: Context) {
        val clock = "09:00"
        if (!isDateInvalid(clock, TIME)) {
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(ctx, RepeatingAlarm::class.java)
            val time = clock.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val timerepeat = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]))
                set(Calendar.MINUTE, Integer.parseInt(time[1]))
                set(Calendar.SECOND, 0)
            }
            val pending = PendingIntent.getBroadcast(ctx, ID_REPEAT, intent, 0)
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    timerepeat.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pending
            )
            Toast.makeText(ctx, "Reminder Is On", Toast.LENGTH_SHORT).show()
        } else {
            return
        }
    }

    fun cancelAlarm(ctx: Context) {
        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(ctx, RepeatingAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(ctx, ID_REPEAT, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(ctx, "Reminder Is Off", Toast.LENGTH_SHORT).show()
    }

    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            e.printStackTrace()
            true
        }
    }

    private fun ShowNotification(ctx: Context) {
        val CHANNEL_ID = "channel_100"
        val CHANNEL_NAME = "reminder_git"
        val intent = Intent(ctx, MainActivity::class.java)
        val pending = PendingIntent.getActivity(ctx, 0, intent, 0)

        val NotifManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val NotifBuilder = NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setContentIntent(pending)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.resources, R.drawable.ic_stat_name))
                .setContentTitle(ctx.resources.getString(R.string.notiftitle))
                .setContentText(ctx.resources.getString(R.string.notifmessage))
                .setSubText(time())
                .setSound(ringtone)
                .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_NAME
            NotifBuilder.setChannelId(CHANNEL_ID)
            NotifManager.createNotificationChannel(channel)
        }
        val notif = NotifBuilder.build()
        NotifManager.notify(ID_REPEAT, notif)

    }

    private fun time(): String {
        val dataTime = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val current = dataTime.format(Date())
        return "$current"
    }
}