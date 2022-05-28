package com.example.se328nawafalzahemproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

                String iurl ="https://api.openweathermap.org/data/2.5/weather?q="+CityName+"&appid=1d47b8e245e55c478c2f352cf0299029&units=metric";
                Iwheather(iurl);


            }
        });
    }

    public void Iwheather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Gorge","Response  Received");
                        Log.d("Gorge",response.toString());
                        try {

                            TextView ICity = (TextView) findViewById(R.id.iCity);
                            TextView ITemp = (TextView) findViewById(R.id.iTemp);
                            TextView IRain = (TextView) findViewById(R.id.iRain);
                            TextView IHumd = (TextView) findViewById(R.id.iHumidity);

                            String town = response.getString("name");
                            Log.d("Gorge", town);
                            ICity.setText("Weather in " + town);

                            JSONObject jsonMain = response.getJSONObject("main");
                            double temp= jsonMain.getDouble("temp");
                            Log.d("Gorge-temp", String.valueOf(temp));
                            ITemp.setText("Teamperature: " +String.valueOf(temp)+" C");



                            double humd= jsonMain.getDouble("humidity");
                            IHumd.setText("Humidity : "+ String.valueOf(humd));


                            JSONObject jsonMain2 = response.getJSONObject("clouds");
                            double Ra= jsonMain2.getDouble("all");
                            IRain.setText("Chance of Rain: "+ String.valueOf(Ra)+"%");


                        }
                        catch (JSONException e){

                            e.printStackTrace();
                            Log.e("Recieve Error", e.toString());


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Gorge","Error retrieving URL");

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);

    }
}