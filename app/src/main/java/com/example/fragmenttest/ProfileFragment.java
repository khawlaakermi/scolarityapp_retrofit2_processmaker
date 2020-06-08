package com.example.fragmenttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fragmenttest.API.ProcessAPI;
import com.example.fragmenttest.Model.Case;
import com.example.fragmenttest.Model.ItemFormulaire;
import com.example.fragmenttest.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ProfileFragment extends Fragment {

    public static final String BASE_URL = "http://process.isiforge.tn/";
    private static Retrofit retrofit = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Retrieve token wherever necessary
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String token   = preferences.getString("TOKEN",null);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout parentLayout = (LinearLayout) view.findViewById(R.id.container_user);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ProcessAPI processAPI = retrofit.create(ProcessAPI.class);
        Call<User> call = processAPI.getuser("Bearer "+token);
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call , Response<User>response) {
                User user = response.body();
                //begin case
                LinearLayout l=new LinearLayout(container.getContext());
                l.setOrientation(LinearLayout.HORIZONTAL);
                TextView title = new TextView(container.getContext());
                title.setText("First Name : " );
                title.setGravity(2);
                title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                l.addView(title);
                TextView titlee = new TextView(container.getContext());
                titlee.setText(user.getFirstname());
                titlee.setGravity(2);
                l.setPadding(10,10,10,10);
                l.addView(titlee);
                parentLayout.addView(l);
                //end case
                 l=new LinearLayout(container.getContext());
                l.setOrientation(LinearLayout.HORIZONTAL);
                title = new TextView(container.getContext());
                title.setText("Last Name : ");
                title.setGravity(2);
                title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                l.addView(title);
                titlee = new TextView(container.getContext());
                titlee.setText(user.getLastname());
                titlee.setGravity(2);
                l.setPadding(10,10,10,10);
                l.addView(titlee);
                parentLayout.addView(l);
                //
                l=new LinearLayout(container.getContext());
                l.setOrientation(LinearLayout.HORIZONTAL);
                title = new TextView(container.getContext());
                title.setText("User name : ");
                title.setGravity(2);
                title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                l.addView(title);
                titlee = new TextView(container.getContext());
                titlee.setText(user.getUsername());
                titlee.setGravity(2);
                l.setPadding(10,10,10,10);
                l.addView(titlee);

                parentLayout.addView(l);
                //
                l=new LinearLayout(container.getContext());
                l.setOrientation(LinearLayout.HORIZONTAL);
                title = new TextView(container.getContext());
                title.setText("Email : ");
                title.setGravity(2);
                title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                l.addView(title);
                titlee = new TextView(container.getContext());
                titlee.setText(user.getMail());
                titlee.setGravity(2);
                l.addView(titlee);
                l.setPadding(10,10,10,10);
                parentLayout.addView(l);
                //
                l=new LinearLayout(container.getContext());
                l.setOrientation(LinearLayout.HORIZONTAL);
                title = new TextView(container.getContext());
                title.setText("Birthday : ");
                title.setGravity(2);
                title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                l.addView(title);
                titlee = new TextView(container.getContext());
                titlee.setText(user.getBirthday());
                titlee.setGravity(2);
                l.addView(titlee);
                l.setPadding(10,10,10,10);
                parentLayout.addView(l);
                //
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
        return view;

    }
}
