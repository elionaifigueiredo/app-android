package com.example.aula.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aula.R;
import com.example.aula.data.MatchesApi;
import com.example.aula.databinding.ActivityMainBinding;
import com.example.aula.domain.Match;
import com.example.aula.ui.adapter.MatchesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesApi matchesApi;
    private MatchesAdapter machesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHttpCliente();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();


    }

    private void setupHttpCliente() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://elionaifigueiredo.github.io/simulator-api/") //Url Base
                .addConverterFactory(GsonConverterFactory.create()) //serialização de desiserialização de json
                .build(); // criação

        matchesApi = retrofit.create(MatchesApi.class); //chama o recurso da interface
    }

    private void setupMatchesRefresh() {
        // atualização da lista
        binding.srlMatches.setOnRefreshListener(this::findMatchesFromApi);

        //
    }


    private void setupFloatingActionButton() {

        binding.fbSimulator.setOnClickListener(view -> {
            view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // TODO IMPLEMENTAR O ALGORITMO DE ANIMAÇÃO

                    Random random = new Random();

                    for (int i = 0; i < machesAdapter.getItemCount(); i++) {
                            //pega a lista da partida através da lista do adapter
                            Match match = machesAdapter.getMatches().get(i);
                            // adiciona para o time da casa o valor do score pelo random, indo do 0 a max de estrela
                            match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getStars() + 1));
                            // time visitante, ou seja time de fora
                            match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getStars() + 1));
                            // avisando o adapter que os dados atualizou
                            // com isso o recicleView irá atualizar a lista do usuário
                            machesAdapter.notifyItemChanged(i);

                            // apresentar o score na tela pelo adapter


                    }
                }
            });
        });

    }



    private void setupMatchesList() {

        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));

        findMatchesFromApi();


    }



    private void showErrorMessage() {

        Snackbar.make(binding.fbSimulator, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }


    private void findMatchesFromApi() {
        // fica visível o load
        binding.srlMatches.setRefreshing(true);

        // CallBack pra consumo da api via http
        matchesApi.getMatches().enqueue(new Callback<List<Match>>() {
            @Override // callback de response
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                //quando de certo a recuperação dos dados da api faz alguma coisa
                if(response.isSuccessful()){
                    List<Match> matches = response.body();
                    machesAdapter = new MatchesAdapter(matches);
                    binding.rvMatches.setAdapter(machesAdapter);
                }else {
                    showErrorMessage(); // messagem de erro para o usuario
                }
                // faz com que o load não seja exibido
                    binding.srlMatches.setRefreshing(false);
            }

            @Override //call back de falhas
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErrorMessage();
                // faz com que o load não seja exibido
                binding.srlMatches.setRefreshing(false);
            }
        });
    }

}