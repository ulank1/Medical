package com.alina.medical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class DateActivity extends AppCompatActivity {
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        datePicker=(DatePicker) findViewById(R.id.date_picker);
        Calendar today = Calendar.getInstance();

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                    }
                });

    }


    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());

        Log.e("TAGg",""+" "+calendar.get(Calendar.DAY_OF_WEEK));
        String date=calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR);
        startActivity(new Intent(DateActivity.this,AppointmentActivity.class).putExtra("week",calendar.get(Calendar.DAY_OF_WEEK)-1).putExtra("date",date));



    }
}
