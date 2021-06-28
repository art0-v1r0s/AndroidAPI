package com.example.pa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Projet extends AppCompatActivity {

    Link showProj = new Link("http://192.168.1.210/API/v2/API/api/project/show.php");

    public FloatingActionButton floatingHome;
    private Button btnValidateNum;
    private EditText edtNumProjet;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projet);

        SharedPreferences associationNum = getSharedPreferences("data",MODE_PRIVATE);

        floatingHome = findViewById(R.id.floatingHome);
        btnValidateNum = findViewById(R.id.btnValidateNum);
        edtNumProjet = findViewById(R.id.edtNumProjet);
        listView =(ListView) findViewById(R.id.listView);

        String url = showProj.getLink();
        getJSON(url);

        floatingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainToHome = new Intent(Projet.this, MainActivity.class);
                startActivity(mainToHome);
            }
        });

        btnValidateNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recupNumAssos = edtNumProjet.getText().toString();
                if (recupNumAssos.length()!=0) {

                    SharedPreferences.Editor edit = associationNum.edit();
                    edit.putString("id_association",recupNumAssos);
                    edit.apply();

                    Intent mainToAssociation = new Intent(Projet.this, ShowAssociation.class);

                    startActivity(mainToAssociation);
                }else{
                    edtNumProjet.setError("Veillez remplir");
                }
            }
        });
    }
    

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(Association.this, s, Toast.LENGTH_LONG).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] projet = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            projet[i] = "Association " + obj.getString("association") +" : "+obj.getString("name") +"\n \n" + obj.getString("description")+"\n";

        }

        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, projet){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.BLACK);

                // Generate ListView Item using TextView
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);
    }

}

