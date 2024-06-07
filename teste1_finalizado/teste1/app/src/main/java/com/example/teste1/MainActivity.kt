package com.example.teste1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Responsável pela exibição da animação em mp4

        val video = findViewById<VideoView>(R.id.videoView)
        val uri: Uri = Uri.parse(
            "android.resource://" + packageName + "/raw/newanimation"
        )
        video.setVideoURI(uri)
        video.requestFocus()
        video.resume()
        video.start()

        // Responsável pelo tempo de duração da animação inicial

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,inicial::class.java)
            startActivity(intent)
            finish()
        },8000)
    }
}