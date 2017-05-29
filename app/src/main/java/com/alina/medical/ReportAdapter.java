package com.alina.medical;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

/**
 * Created by ulan on 5/22/17.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private ArrayList<Appointment> records;

    public ReportAdapter(ArrayList<Appointment> records) {
        this.records = records;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_item, viewGroup, false);
        return new ViewHolder(v);
    }



    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Appointment record = records.get(i);
        viewHolder.recordd=record;
        viewHolder.name.setText(record.getName());
        viewHolder.phone.setText(record.getPhone());
        viewHolder.time.setText(record.getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,phone,time;
        private ImageView icon;
        Appointment recordd;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            time = (TextView) itemView.findViewById(R.id.time_ff);
            icon=(ImageView) itemView.findViewById(R.id.bt_delete);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("TAGGG","ONCLICK"+recordd.getId());
                    new SendJsonDataToServer().execute(recordd.getId());
                    records.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }
    }

    class SendJsonDataToServer extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String id=params[0];
            JSONObject object=new JSONObject();
            try {
                object.put("ison","no");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = null;
                Log.e("TAGGDD",id);
                url = new URL("https://med-schedule.herokuapp.com/api/v1/search/"+id+"/");

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                try {
                    writer.write(String.valueOf(object));
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

        }
    }
}
