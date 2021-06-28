package com.example.pa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Jeton extends AppCompatActivity {

    public FloatingActionButton floatingHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeton);

        floatingHome = findViewById(R.id.floatingHome);


        floatingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainToHome = new Intent(Jeton.this, MainActivity.class);
                startActivity(mainToHome);
            }
        });
    }
}
