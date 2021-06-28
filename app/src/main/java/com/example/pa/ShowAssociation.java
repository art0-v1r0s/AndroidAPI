package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowAssociation extends AppCompatActivity {

    private FloatingActionButton floatingHome;
    private ListView lvAssos;
    private ListView lvProject;
    Link showAsos = new Link("http://192.168.1.210/API/v2/API/api/association/show.php?id_association=");
    Link showProjetByAssos = new Link("http://192.168.1.210/API/v2/API/api/project/show.php?id_association=");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_association);

        floatingHome = findViewById(R.id.floatingHome);
        lvProject = (ListView) findViewById(R.id.listViewProject);
        lvAssos = (ListView) findViewById(R.id.listViewAssociation);

        SharedPreferences associationNum = getSharedPreferences("data",MODE_PRIVATE);

        String id_association = associationNum.getString("id_association",null);


        String urlAsso = showAsos.getLink()+id_association;
        String urlProject = showProjetByAssos.getLink()+id_association;

        getJSONAsso(urlAsso);
        getJSONProject(urlProject);

        //Toast.makeText(ShowAssociation.this, urlProject, Toast.LENGTH_SHORT).show();

        floatingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainToHome = new Intent(ShowAssociation.this, MainActivity.class);



                startActivity(mainToHome);

            }
        });

    }
    private void getJSONProject(final String urlWebService) {

        class GetJSONProject extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(Association.this, s, Toast.LENGTH_LONG).show();
                try {
                    loadIntoListViewP(s);
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
        GetJSONProject getJSONProject = new GetJSONProject();
        getJSONProject.execute();
    }

    private void loadIntoListViewP(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] project = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            project[i] = obj.getString("name") +" : \n \n"+ obj.getString("description")+"\n";

        }

        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, project){
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
        lvProject.setAdapter(arrayAdapter);
    }

    private void getJSONAsso(final String urlWebService) {

        class GetJSONAsso extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(Association.this, s, Toast.LENGTH_LONG).show();
                try {
                    loadIntoListViewA(s);
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
        GetJSONAsso getJSONAsso = new GetJSONAsso();
        getJSONAsso.execute();
    }

    private void loadIntoListViewA(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] project = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            project[i] = obj.getString("name") +" :\n \n"+ obj.getString("description");

        }

        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, project){
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
        lvAssos.setAdapter(arrayAdapter);
    }


}