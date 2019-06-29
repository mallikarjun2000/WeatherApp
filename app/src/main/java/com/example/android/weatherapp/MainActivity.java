package com.example.android.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String WEATHER_URL="http://api.openweathermap.org/data/2.5/weather?q=Hyderabad&&appid=f665d3800f908bf500b504155297ba2e&&units=metric";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.myActionBarColor));

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(WEATHER_URL);

        ImageView refresh;
        refresh = findViewById(R.id.refresh_view);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherTask weatherTask = new WeatherTask();
                weatherTask.execute(WEATHER_URL);
                Toast.makeText(MainActivity.this, "Temperature Updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void UpdateUi(Weather weather) throws ParseException {
        RelativeLayout r1;
        TextView temp,hum,city, maxtemp,mintemp,dateview,stats;
        r1 = findViewById(R.id.relative_layout);
        stats=findViewById(R.id.status_view);
        temp = findViewById(R.id.temp_view);
        maxtemp = findViewById(R.id.max_temp);
        mintemp = findViewById(R.id.min_temp);
        dateview = findViewById(R.id.date_view);
        city = findViewById(R.id.city_view);
        hum = findViewById(R.id.humidity_view);
        /// get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int str = Integer.parseInt(sdf.format(new Date()));
        if(str >= 18 || str <= 6) {
            r1.setBackgroundResource(R.drawable.nightlight);
        }
        else
        {
            r1.setBackgroundResource(R.drawable.sunrise);
            //temp.setTextColor(getResources().getColor(R.color.Black));

        }
        stats.setText(weather.getMain());


        //Get the current date

        temp.setText(String.valueOf(weather.getTemperature())+".C");
        maxtemp.setText(""+weather.getMaximum()+".C");
        mintemp.setText(""+weather.getMinimum()+".C");
        city.setText(""+weather.getCity());
        hum.setText("Humidity :"+weather.getHumidity());

        String dateStr = "04/05/2010";

        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = curFormater.parse(dateStr);
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

        String newDateStr = postFormater.format(dateObj);
        dateview.setText(newDateStr);


    }

    public class WeatherTask extends AsyncTask<String,Void,Weather>{
        @Override
        protected Weather doInBackground(String... strings) {
            Weather weather = Utils.fetchWeatherDetails(WEATHER_URL);
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            try {
                UpdateUi(weather);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
