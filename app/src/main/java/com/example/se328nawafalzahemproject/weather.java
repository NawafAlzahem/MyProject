package com.example.se328nawafalzahemproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class weather extends AppCompatActivity {
    RequestQueue rq;
    EditText city;
    Button setCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        city = findViewById(R.id.inp_city);
        setCity=findViewById(R.id.bttn_city);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        rq = Volley.newRequestQueue(this);
        rq.add(weatherHelper.weather(this));

        setCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CityName = city.getText()+"";

                if (CityName.isEmpty()){
                    Toast.makeText(weather.this,"City name required. Please type a city name.",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor myEditor = sp.edit();

                myEditor.putString("WeatherCity",CityName);
                myEditor.commit();

                rq.add(weatherHelper.weather(weather.this));


            }
        });
    }
}