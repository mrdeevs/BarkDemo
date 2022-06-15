package com.deevs.barkdemo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class DisableCameraActivity : AppCompatActivity() {

    private val model : AdbViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_disable)

        model.runTest()
    }
}