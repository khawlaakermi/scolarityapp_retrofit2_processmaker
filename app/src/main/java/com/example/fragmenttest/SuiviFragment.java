package com.example.fragmenttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fragmenttest.API.ProcessAPI;
import com.example.fragmenttest.Model.Case;
import com.example.fragmenttest.Model.PostObject;
import com.example.fragmenttest.Model.Process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SuiviFragment extends Fragment {

    public static final String BASE_URL = "http://process.isiforge.tn/";
    private static Retrofit retrofit = null;
    ProgressBar progress;
    Boolean isScrolled=false;
    int totalitems, currentitems , scrolleditems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suivi, container, false);
        MainActivity activity = (MainActivity) getActivity();
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView2);
        LinearLayoutManager manager= new LinearLayoutManager(getContext());
        progress=view.findViewById(R.id.progressBar1);
        progress.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(manager);
        //Retrieve token wherever necessary
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String token   = preferences.getString("TOKEN",null);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ProcessAPI processAPI = retrofit.create(ProcessAPI.class);
        Call<List<Process>> call=processAPI.particpated("Bearer "+token);
        call.enqueue(new Callback<List<Process>>() {
            @Override
            public void onResponse(Call<List<Process>> call, Response<List<Process>> response) {
                List<Process> cases = response.body();
                RecyclerAdaptersSuivi recyclerAdapter = new RecyclerAdaptersSuivi(getActivity(), cases);
                recyclerView.setAdapter(recyclerAdapter);
                progress.setVisibility(View.GONE);


            }
            @Override
            public void onFailure(Call<List<Process>> call, Throwable t) {

            }
        });

        return view;
    }



    }



