package com.example.controlededespesas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Autoria extends AppCompatActivity {

    public static void sobre(AppCompatActivity activity){
        Intent intent = new Intent(activity, Autoria.class);

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);
    }
}