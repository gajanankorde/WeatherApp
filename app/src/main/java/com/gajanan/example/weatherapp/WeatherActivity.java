package com.gajanan.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//import android.widget.Spinner;

//public class WeatherActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
public class WeatherActivity extends AppCompatActivity {
    Spinner spnCities;


    public String[] arrCities={
            "Select City",
            "Mumbai",
            "Pune",
            "Aurangabad",
            "Nagpur",
            "Nashik"
    };

    String city;

    public String cityName;

    /*String API = "8118ed6ee68db2debfaaa5a44c832918";*/
    String API="b4ec77755a568851113aaa61451bbe4a";

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        addressTxt = findViewById(R.id.txt_address);

        updated_atTxt = findViewById(R.id.txt_updated_at);
        statusTxt = findViewById(R.id.txt_status);
        tempTxt = findViewById(R.id.txt_temp);
        temp_minTxt = findViewById(R.id.txt_temp_min);
        temp_maxTxt = findViewById(R.id.txt_temp_max);
        sunriseTxt = findViewById(R.id.txt_sunrise);
        sunsetTxt = findViewById(R.id.txt_sunset);
        windTxt = findViewById(R.id.txt_wind);
        pressureTxt = findViewById(R.id.txt_pressure);
        humidityTxt = findViewById(R.id.txt_humidity);

/*
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnCities= (Spinner) findViewById(R.id.spn_cities);
        spnCities.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrCities);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnCities.setAdapter(arrayAdapter);*/

/*
        String myCityName=getIntent().getStringExtra("myCityName");

       Toast.makeText(this, myCityName, Toast.LENGTH_SHORT).show();*/


        cityName=getIntent().getStringExtra("myCityName");

        new weatherTask().execute();

    }

    /*

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(getApplicationContext(),arrCities[position] , Toast.LENGTH_LONG).show();


        city=(String)spnCities.getSelectedItem();

        cityName=city+","+"IN";

        Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
*/
/*

        cityName = "Aurangabad,IN";
*//*


    }
*/


    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.mainContainer).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
                addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);
                temp_minTxt.setText(tempMin);
                temp_maxTxt.setText(tempMax);
                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                windTxt.setText(windSpeed);
                pressureTxt.setText(pressure);
                humidityTxt.setText(humidity);

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }

        }
    }

}
