package com.alina.medical;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    public void onClick(View view) {
        String url="https://www.google.kg/maps/place/%D0%9D%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9+%D1%86%D0%B5%D0%BD%D1%82%D1%80+%D0%BE%D1%85%D1%80%D0%B0%D0%BD%D1%8B+%D0%BC%D0%B0%D1%82%D0%B5%D1%80%D0%B8%D0%BD%D1%81%D1%82%D0%B2%D0%B0+%D0%B8+%D0%B4%D0%B5%D1%82%D1%81%D1%82%D0%B2%D0%B0,+%D0%B1%D0%B0%D0%B7%D0%B0+%E2%84%961/@42.84127,74.5688022,16z/data=!4m5!3m4!1s0x0:0x797766ebf293ac26!8m2!3d42.8424819!4d74.5707697?hl=ru";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void onClick1(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+996312492371"));
        startActivity(intent);
    }

    public void onClick2(View view) {
        String url="http://ncomid.kg/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
