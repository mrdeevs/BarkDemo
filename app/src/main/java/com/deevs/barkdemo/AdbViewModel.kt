package com.deevs.barkdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader

class AdbViewModel : ViewModel() {

    companion object {
        const val COMMAND_LIST_PACKAGES = "pm list packages -e"
        const val COMMAND_DISABLE_YOUTUBE = "pm disable com.google.android.youtube/com.google.android.apps.youtube.app.watchwhile.WatchWhileActivity"
        const val COMMAND_DISABLE_CAMERA = "pm disable com.google.android.GoogleCamera/com.android.camera.CameraLauncher"
    }

    fun runTest() {
        runAdbCommand(COMMAND_DISABLE_CAMERA)
    }

    @Throws(Exception::class)
    private fun runAdbCommand(command : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val process = Runtime.getRuntime().exec(command)
                process.waitFor()

                val results = process.inputStream.bufferedReader().use(BufferedReader::readText)
                Log.e(AdbViewModel::class.java.simpleName, "results : $results")

            } catch (e: Exception) {
                Log.e(AdbViewModel::class.java.simpleName, "${e.message}")
            }
        }
    }
}