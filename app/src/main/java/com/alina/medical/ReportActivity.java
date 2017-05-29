package com.alina.medical;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    Spinner spinner;
    ArrayList<String> idList,spinnerlist;
    ArrayList<Appointment> arrayList;
    String data;
    RecyclerView mRecyclerView;
    ReportAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        spinner= (Spinner) findViewById(R.id.spinner);
        idList=new ArrayList<>();
        spinnerlist=new ArrayList<>();

        data=getIntent().getStringExtra("week");
        spinnerlist.add("Выберите Врача");
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент

        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                if (position!=0){
                    Log.e("TAG","GGG");
                   new ParseTask2().execute();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        new ParseTask().execute();

        mRecyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        arrayList=new ArrayList<>();
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("https://med-schedule.herokuapp.com/api/v1/post/?format=json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            Log.e("TAG",json);
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);

                    spinnerlist.add(menu.getString("name")+" "+menu.getString("surname")+" - "+menu.getJSONObject("job").getString("job"));
                    idList.add(menu.getString("id"));

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReportActivity.this, android.R.layout.simple_spinner_item, spinnerlist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

        }
    }

    public class ParseTask2 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("TAGG",idList.size()+" "+spinner.getSelectedItemPosition());
                URL url = new URL("https://med-schedule.herokuapp.com/api/v1/search/?format=json&&doctor="+idList.get(spinner.getSelectedItemPosition()-1)+"&&data="+data+"&&ison=yes");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            Log.e("TAG_REPORT",json+"uyuyu");
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Appointment appointment=new Appointment();

                    appointment.setName(menu.getString("name")+" "+menu.getString("surname"));
                    appointment.setPhone(menu.getString("number"));
                    appointment.setTime(menu.getString("time"));
                    appointment.setId(menu.getString("id"));

                    arrayList.add(appointment);


                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");


            adapter=new ReportAdapter(arrayList);
            mRecyclerView.setAdapter(adapter);

        }
    }

}
