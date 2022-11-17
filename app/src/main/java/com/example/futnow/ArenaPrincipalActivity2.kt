package com.example.futnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futnow.databinding.ActivityLoginBinding

class ArenaPrincipalActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cadastrase.setOnClickListener {
            val navegarTelaVerAgenda = Intent(this, AgendarQuadraActivity2::class.java)
            startActivity(navegarTelaVerAgenda)
        }
    }
}