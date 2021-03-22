package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WeatherPage extends AppCompatActivity {

    public static final String WEATHER_API_KEY = "f8e6722ee4ec3265109565d2287a7a17";
    private RequestQueue queue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_display);

        context = getApplicationContext();
        queue = Volley.newRequestQueue(this);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.inCoords);

        if (isInt(message)){
            Toast.makeText(WeatherPage.this, "Zip Detected",Toast.LENGTH_SHORT).show();
            int zip = Integer.parseInt(message);
            grabWeather("https://api.openweathermap.org/data/2.5/weather?zip=" + zip + "&appid=" + WEATHER_API_KEY);
        } else {
            Toast.makeText(WeatherPage.this, "City Detected",Toast.LENGTH_SHORT).show();
            grabWeather("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&appid=" + WEATHER_API_KEY);
        }
        // Capture the layout's TextView and set the string as its tex
        //textView.setText(message);
    }

    void grabWeather(String url) {
        TextView inCity = findViewById(R.id.inCity);
        TextView inCoords = findViewById(R.id.inCoords);
        TextView inTemp = findViewById(R.id.inTemp);
        TextView inDesc = findViewById(R.id.inDesc);
        TextView inHumidity = findViewById(R.id.inHumidity);
        TextView inPressure = findViewById(R.id.inPressure);
        TextView inSunrise = findViewById(R.id.inSunrise);
        TextView inSunset = findViewById(R.id.inSunset);
        TextView inWindSpeed = findViewById(R.id.inWindSpeed);
        TextView inWindDirection = findViewById(R.id.inWindDirection);

        Log.i("test", "Test");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject main = null;
                try {
                    Log.i("test", "Test1");
                    main = response.getJSONObject("main");
                    JSONObject coords = response.getJSONObject("coord");
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject w0 = weather.getJSONObject(0);
                    JSONObject wind = response.getJSONObject("wind");
                    JSONObject sys = response.getJSONObject("sys");

                    inCity.setText(response.getString("name"));
                    inCoords.setText("["+coords.getString("lat")+","+coords.getString("lon")+","+"]");
                    inTemp.setText(Math.round((main.getDouble("temp") - 273.15)*9/5+32) + "°F");
                    inDesc.setText(w0.getString("main"));
                    inHumidity.setText(main.getString("humidity")+"%");
                    inPressure.setText(main.getString("pressure")+" mb");
                    inWindSpeed.setText(wind.getString("speed")+" MPH");
                    inWindDirection.setText(wind.getString("deg")+"°");
                    String srise = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date (sys.getInt("sunrise")*1000));
                    String sset = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date (sys.getInt("sunset")*1000));
                    inSunrise.setText(srise);
                    inSunset.setText(sset);

                } catch (JSONException e) {
                    Log.i("test", "JSON Explosion");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", "Error With Volley Request");
            }
        });

        queue.add(jsonObjectRequest);
    }

    void searchGPS(String zip) {

    }

    boolean isInt(String message) {
        for (int i = 0; i < message.length(); i++) {
            if (!Character.isDigit(message.charAt(i))) return false;
        }
        return true;
    }
}
