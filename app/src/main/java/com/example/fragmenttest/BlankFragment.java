package com.example.fragmenttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmenttest.API.ProcessAPI;
import com.example.fragmenttest.Model.Case;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BlankFragment extends Fragment {

    public static final String BASE_URL = "http://process.isiforge.tn/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //Retrieve token wherever necessary
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
        String token   = preferences.getString("TOKEN",null);
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ProcessAPI processAPI = retrofit.create(ProcessAPI.class);
        Call<List<Case>> call = processAPI.cases("Bearer " + token);
        call.enqueue(new Callback<List<Case>>() {
            @Override
            public void onResponse(Call<List<Case>> call, Response<List<Case>> response) {
                List<Case> cases = response.body();
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity(), cases, new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Case item) {
                        //Toast.makeText(getContext(), item.getPro_title(), Toast.LENGTH_LONG).show();
                        Call<ResponseBody> call=processAPI.getifdynaform("Bearer " + token,item.getPro_uid(),item.getTas_uid());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {

                                    String resp = response.body().string();
                                    JSONArray object =new JSONArray(resp);
                                    JSONObject item1=object.getJSONObject(0);
                                    String id_dyn=item1.getString("step_uid_obj");
                                    recyclerView.setVisibility(View.GONE);

                                    FormFragment newFragment = new FormFragment();
                                    Bundle args = new Bundle();
                                    args.putString("id_dyn", id_dyn);
                                    args.putString("tas_uid",item.getTas_uid());
                                    args.putString("pro_uid",item.getPro_uid());

                                    newFragment.setArguments(args);
                                    FragmentTransaction f = getFragmentManager().beginTransaction();
                                    f.replace(R.id.pp, newFragment)
                                            .addToBackStack(null)
                                            .commit();
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }



                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });
                recyclerView.setAdapter(recyclerAdapter);


            }

            @Override
            public void onFailure(Call<List<Case>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
        return view;
    }


}
