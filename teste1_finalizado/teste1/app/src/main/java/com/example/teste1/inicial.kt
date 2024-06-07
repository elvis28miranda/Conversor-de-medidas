package com.example.teste1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teste1.Backend

class inicial : AppCompatActivity() {

    lateinit var  botaoJogar : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Função que direciona o botão inicial para a tela de conversões

        botaoJogar = findViewById(R.id.botaoTelaFinal)

        botaoJogar.setOnClickListener {

            val intent = Intent(applicationContext, Backend::class.java )

            startActivity(intent)

        }
    }
}