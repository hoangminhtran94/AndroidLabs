package com.cst2335.tran0450;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    private EditText emailView;
    private Button loginButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailView = (EditText) findViewById(R.id.email);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(click -> {
            preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            String savedEmail = preferences.getString("email","");
            SharedPreferences.Editor ed = preferences.edit();
            String inputEmail = emailView.getText().toString().trim();
            ed.putString("email",inputEmail).apply();

;
            if(inputEmail.isEmpty()) {
                emailView.setText(savedEmail);
            }
            else {
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                goToProfile.putExtra("Email", inputEmail);
                startActivity(goToProfile);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        emailView = (EditText) findViewById(R.id.email);
        String inputEmail = emailView.getText().toString();

        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String savedEmail = preferences.getString("email","");

        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("email",inputEmail).apply();

        if(inputEmail.isEmpty()) {
            emailView.setText(savedEmail);
        }
        else {
            emailView.setText(inputEmail);
        }


    }
}