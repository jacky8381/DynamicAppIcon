package com.example.dynamicappicon.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ChangeAppIconWorker (context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        return try {
            Log.d("AppIcon in worker", ChangeAppIconService.appIcon.toString())
            val aliasName = ChangeAppIconService.appIcon
            ChangeAppIconService.enableDisableActivityAlais(
                context = applicationContext,
                aliasName = aliasName
            )
            Result.success()
        }catch (e : Exception){
            Log.d("Exception", e.stackTraceToString())
            Result.success()
        }
    }
}