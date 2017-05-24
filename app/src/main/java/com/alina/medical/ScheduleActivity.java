package com.alina.medical;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ScheduleActivity extends AppCompatActivity {
    Spinner spinner;
    int data;
    ArrayList<String> idList,spinnerlist;
    ArrayList<Appointment> arrayList;

    RecyclerView mRecyclerView;
    ReportAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        idList=new ArrayList<>();


        data=getIntent().getIntExtra("week",1);


        new ParseTask2().execute();

        mRecyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        arrayList=new ArrayList<>();
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

    }



    public class ParseTask2 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("https://med-schedule.herokuapp.com/api/v1/schedule/?format=json&&week_day="+data);

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
                    Appointment appointment=new Appointment();
                    JSONObject doctor=menu.getJSONObject("doctor");
                    JSONObject job=doctor.getJSONObject("job");
                    String s=menu.getString("time_from")+" - "+menu.getString("time_to");
                    appointment.setName(doctor.getString("name")+" "+doctor.getString("surname")+"   "+s);
                    appointment.setPhone(job.getString("job"));

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
