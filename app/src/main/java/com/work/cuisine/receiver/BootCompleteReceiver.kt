package com.work.cuisine.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.work.cuisine.presentation.activity.MainActivity

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}