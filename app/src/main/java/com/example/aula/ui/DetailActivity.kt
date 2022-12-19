package com.example.aula.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aula.R
import com.example.aula.databinding.ActivityDetailBinding
import com.example.aula.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Componente que exibe a seta de voltar




    }
}