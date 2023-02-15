package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.pokeApi.RetrofitConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPoke;
    private List<PokemonModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerViewPoke = findViewById(R.id.recyclerViewPoke);
        Call<List<PokemonModel>> call = new RetrofitConfig().getPokeService().getAllPokemons();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<PokemonModel>> call, Response<List<PokemonModel>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    PokeAdapter adapter = new PokeAdapter(list);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPoke.setLayoutManager(layoutManager);
                    recyclerViewPoke.setHasFixedSize(true);
                    recyclerViewPoke.setAdapter(adapter);
                }else{
                    Toast.makeText(ListActivity.this, "Erro ao listar todos os pokémons " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ListActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<PokemonModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}