package com.example.aula.data;


import com.example.aula.domain.Match;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesApi {
        // Camada de acesso a dados no API do gitpages
        @GET("matches.json")
        Call<List<Match>>getMatches();


    
}
