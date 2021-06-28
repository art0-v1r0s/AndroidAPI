package com.example.pa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.roacult.backdrop.BackdropLayout;

public class MainActivity extends AppCompatActivity {

    BackdropLayout backdropLayout;
    Button btnJeton, btnAssos, btnProjet;

    TextView txtResult;

    View back_layout, front_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backdropLayout = (BackdropLayout) findViewById(R.id.container);

        back_layout = backdropLayout.getChildAt(0);
        front_layout = backdropLayout.getChildAt(1);

        btnJeton = (Button) back_layout.findViewById(R.id.jeton);
        btnAssos = (Button) back_layout.findViewById(R.id.association);
        btnProjet = (Button) back_layout.findViewById(R.id.projet);

        txtResult = (TextView) front_layout.findViewById(R.id.txt_result);

        String numAssoc = getIntent().getStringExtra("numAssoc");
        Toast.makeText(MainActivity.this,numAssoc,Toast.LENGTH_LONG);

        btnProjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btnProjet.animate().rotation(360).setDuration(300);
                txtResult.setText("projet");

                Intent mainToProjet = new Intent(MainActivity.this, Projet.class);
                startActivity(mainToProjet);
            }
        });
        btnAssos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btnAssos.animate().rotation(360).setDuration(300);

                txtResult.setText("Assosciation");

                Intent mainToProjet = new Intent(MainActivity.this, Association.class);
                startActivity(mainToProjet);

            }
        });

        btnJeton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btnJeton.animate().rotation(360).setDuration(300);

                txtResult.setText("jeton");

                Intent mainToProjet = new Intent(MainActivity.this, Jeton.class);
                startActivity(mainToProjet);
            }
        });
    }

}