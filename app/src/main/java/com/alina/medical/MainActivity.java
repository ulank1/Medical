package com.alina.medical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void b(View view) {
        int id=view.getId();
      switch (id){
          case R.id.button:
            startActivity(new Intent(MainActivity.this,DateActivity.class));
              break;
          case R.id.button5:
              startActivity(new Intent(MainActivity.this,ContactsActivity.class));

              break;
          case R.id.button2:
              startActivity(new Intent(MainActivity.this,DateScheduleActivity.class));

              break;
          case R.id.button3:
              startActivity(new Intent(MainActivity.this,DoctorActivity.class));

              break;
          case R.id.button4:
              startActivity(new Intent(MainActivity.this,AboutActivity.class));

              break;
          case R.id.button6:
              startActivity(new Intent(MainActivity.this,Date2Activity.class));

              break;
          default:
              break;
      }
    }
}
