package com.example.fragmenttest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BlankFragment2 extends Fragment {

private Button btnoui;
private Button btnnon;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_blank2, container, false);
         btnoui=view.findViewById(R.id.btnoui);
         btnoui.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 getActivity().finish();
                 System.exit(0);
             }
         });
         btnnon=view.findViewById(R.id.btnnon);
         btnnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(homeActivity);
            }
        });

        return view;
    }
}
