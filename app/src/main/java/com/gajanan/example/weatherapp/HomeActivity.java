package com.gajanan.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spnCities;


    TextView txtCity;
    Button btnViewWeatherInfo;

    public String[] arrCities={
            "Select City",
            "Mumbai,IN",
            "Pune,IN",
            "Aurangabad,IN",
            "Nagpur,IN",
            "Nashik,IN"
    };


    public String city;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnCities= (Spinner) findViewById(R.id.spn_cities);
        spnCities.setOnItemSelectedListener(this);


        txtCity=findViewById(R.id.txt_city);
        btnViewWeatherInfo =findViewById(R.id.btn_viewWeatherInfo);
        btnViewWeatherInfo.setEnabled(false);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrCities);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        spnCities.setAdapter(arrayAdapter);

        btnViewWeatherInfo.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {





        //Toast.makeText(this, arrCities[position], Toast.LENGTH_SHORT).show();

        city=(String)spnCities.getSelectedItem();

        //cityName=city+","+"IN";

        //txtCity.setText("City :" +city);


        if (!city.equals("Select City")){
            txtCity.setText("City :" +city);
            btnViewWeatherInfo.setEnabled(true);
        }

  }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent(HomeActivity.this, WeatherActivity.class);
        intent.putExtra("myCityName",city);
        startActivity(intent);
    }

}
