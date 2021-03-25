package com.example.vertageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText tv_login;
    Button button;
    String valLogin;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_login = findViewById(R.id.tV_login);

        button   = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valLogin = String.valueOf(tv_login.getText());
                Intent intent = new Intent(MainActivity.this, MapScreenActivity.class);
                intent.putExtra("login", valLogin);
                startActivity(intent);
            }
        });
    }
}