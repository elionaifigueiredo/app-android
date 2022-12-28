package com.example.aula.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.aula.R
import com.example.aula.databinding.ActivityDetailBinding
import com.example.aula.databinding.ActivityMainBinding
import com.example.aula.domain.Match

class DetailActivity : AppCompatActivity() {

    // chama o encapsulamento
    object  Extras{
        const val MATCH = "EXTRA_MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Componente que exibe a seta de voltar


        loadMatchFromExtras()

    }

    private fun loadMatchFromExtras() {

                intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let {
                // pega a imagem através do it ( iteração) e atribui ao imageView
                Glide.with(this).load(it.place.image).into(binding.ivPlace)
                //Nome atualizado na barra
                supportActionBar?.title = it.place.name
                // pela o textView e adiciona a descrição
                binding.tvDescription.text = it.description
                // adiciona a image
                Glide.with(this).load(it.homeTeam.image).into(binding.ivHomeTeam)
                // adiciona o nome
                binding.tvHomeTeamName.text = it.homeTeam.nome
                // adicona a força
                binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
                if(it.homeTeam.score != null){
                    binding.tvHomeTeamScore.text = it.homeTeam.score.toString()

                // adiciona a image
                Glide.with(this).load(it.awayTeam.image).into(binding.ivAwayTeam)
                binding.tvAwayTeamName.text = it.awayTeam.nome
                binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
                }
                if(it.awayTeam.score != null){
                    binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
                }
        }
    }
}