package com.alina.medical;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class Date2Activity extends AppCompatActivity {
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

        startActivity(new Intent(Date2Activity.this,ReportActivity.class).putExtra("week",date));



    }
}
