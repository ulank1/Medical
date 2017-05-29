package com.alina.medical;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {
    Spinner spinner,spinnerDoctor,spinnerTime;
    ArrayList<String> spinnerlist,doctorList,idJobList,idDoctorList,timeFromList,timeToList,timeSpinnerList,idDoc;
    int weekDay;
    String date;
    LinearLayout line1;
    EditText editName,editSurname,editPhone;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        spinnerlist=new ArrayList<>();
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        line1=(LinearLayout) findViewById(R.id.line1);
        spinnerlist.add("Выберите направление");
        doctorList=new ArrayList<>();
        doctorList.add("Выберите Врача");
        idJobList=new ArrayList<>();
        idDoctorList=new ArrayList<>();
        timeToList=new ArrayList<>();
        timeFromList=new ArrayList<>();
        timeSpinnerList=new ArrayList<>();
        idDoc=new ArrayList<>();
        weekDay=getIntent().getIntExtra("week",1);
        date=getIntent().getStringExtra("date");
        editName=(EditText) findViewById(R.id.edit_name);
        editSurname=(EditText) findViewById(R.id.edit_surname);
        editPhone=(EditText) findViewById(R.id.edit_phone);
        spinner= (Spinner) findViewById(R.id.spinner);

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
                    progressBar.setVisibility(View.VISIBLE);
                    spinnerDoctor.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spinnerTime= (Spinner) findViewById(R.id.spinner_time);

        // заголовок
        spinnerTime.setPrompt("Title");
        // выделяем элемент

        // устанавливаем обработчик нажатия
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerDoctor= (Spinner) findViewById(R.id.spinner_doctor);

        // заголовок
        spinnerDoctor.setPrompt("Title");
        // выделяем элемент

        // устанавливаем обработчик нажатия
        spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                if (position!=0) {
                    timeSet(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        new ParseTask().execute();
    }

    public void onClick(View view) {
        boolean bool=true;
        JSONObject obj = new JSONObject();
        try {
            int pos = spinner.getSelectedItemPosition();
            if (editName.getText().toString().equals("")){
                editName.setError("Заполните поле");
                bool=false;
            }
            if (editSurname.getText().toString().equals("")){
                editSurname.setError("Заполните поле");
                bool=false;
            }
            if (editPhone.getText().toString().equals("")){
                editPhone.setError("Заполните поле");
                bool=false;
            }
            if (pos==0){
                Toast.makeText(this,"Выберите Категорию",Toast.LENGTH_SHORT).show();
                bool=false;
            }
            obj.put("doctor", idDoctorList.get(spinnerDoctor.getSelectedItemPosition()-1));
            obj.put("time", timeSpinnerList.get(spinnerTime.getSelectedItemPosition()));
            obj.put("data", date);
            obj.put("name",editName.getText().toString());
            obj.put("surname",editSurname.getText().toString());
            obj.put("number",editPhone.getText().toString());
            obj.put("ison","yes");

            Log.e("TAG", "asd");
            if (bool){
                new SendJsonDataToServer().execute(String.valueOf(obj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }


    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("https://med-schedule.herokuapp.com/api/v1/job/?format=json");

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

                    idJobList.add(menu.getString("id"));
                    spinnerlist.add(menu.getString("job"));


                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppointmentActivity.this, android.R.layout.simple_spinner_item, spinnerlist);
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
                Log.e("TAGG",idJobList.size()+" "+spinner.getSelectedItemPosition());
                URL url = new URL("https://med-schedule.herokuapp.com/api/v1/schedule/?format=json&&doctor__job="+idJobList.get(spinner.getSelectedItemPosition()-1)+"&&week_day="+weekDay);

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
            doctorList.clear();
            doctorList.add("Выберите Врача");
            idDoctorList.clear();
            Log.e("TAG",json);
            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    timeFromList.add(menu.getString("time_from"));
                    timeToList.add(menu.getString("time_to"));
                    String s=menu.getString("time_from")+" - "+menu.getString("time_to");
                    JSONObject doctor=menu.getJSONObject("doctor");
                    doctorList.add(doctor.getString("name")+" "+doctor.getString("surname")+" "+s);
                    idDoctorList.add(doctor.getString("resource_uri"));
                    idDoc.add(doctor.getString("id"));

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");
            spinnerDoctor.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            line1.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppointmentActivity.this, android.R.layout.simple_spinner_item, doctorList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDoctor.setAdapter(adapter);



        }
    }
        public void timeSet(int position){
            timeSpinnerList.clear();
            String timeFrom=timeFromList.get(position-1);
            String timeTo=timeToList.get(position-1);
            int a,b;
            if (timeFrom.length()==4){
               a=Integer.parseInt(timeFrom.substring(0,1));
                Log.e("TAGJGO",a+"");
            }else {
                a=Integer.parseInt(timeFrom.substring(0,2));
                Log.e("TAGJGO",a+"");
            }



            if (timeTo.length()==4){
                b=Integer.parseInt(timeTo.substring(0,1));
                Log.e("TAGJGO",b+"");
            }else {
                b=Integer.parseInt(timeTo.substring(0,2));
                Log.e("TAGJGO",b+"");
            }

            for (int i=a;i<b;i++){
                for (int j=0;j<4;j++){
                    if (j==0){
                        timeSpinnerList.add(i+":00-"+i+":15");
                    }
                    if (j==1){
                        timeSpinnerList.add(i+":15-"+i+":30");
                    }
                    if (j==2){
                        timeSpinnerList.add(i+":30-"+i+":45");
                    }
                    if (j==3){
                        timeSpinnerList.add(i+":45-"+(i+1)+":00");
                    }
                }

                new ParseTask3().execute();
            }



        }

    public class ParseTask3 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("https://med-schedule.herokuapp.com/api/v1/search/?format=json&&doctor="+idDoc.get(spinnerDoctor.getSelectedItemPosition()-1)+"&&data="+date+"&&ison=yes");

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

                    String s=menu.getString("time");

                    for (int j=0;j<timeSpinnerList.size();j++){
                        if (timeSpinnerList.get(j).equals(s)){
                            timeSpinnerList.remove(j);
                        }
                    }

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppointmentActivity.this, android.R.layout.simple_spinner_item, timeSpinnerList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTime.setAdapter(adapter);


        }
    }



    class SendJsonDataToServer extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = null;

                    url = new URL("https://med-schedule.herokuapp.com/api/v1/search/");

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                try {
                    writer.write(JsonDATA);
                } catch (Exception e) {
                    Log.e("TAG", "Error");
                }
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                Log.e("TAG", "asdfasd");
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    return null;
                }
                JsonResponse = buffer.toString();
                Log.e("TAG", JsonResponse);
                return JsonResponse;


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
            return "";

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            finish();
        }
    }


}
