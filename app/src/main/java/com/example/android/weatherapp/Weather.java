package com.example.android.weatherapp;

public class Weather {
    String main;
    int temperature;
    String city;
    int humidity;
    int maximum;
    int minimum;
    Weather(int temperature,String city,int humidity,int maximum,int minimum,String main){
        this.city=city;
        this.main=main;
        this.temperature=temperature;
        this.humidity=humidity;
        this.maximum=maximum;
        this.minimum=minimum;
    }
    public String getMain(){
        return main;
    }
    public String getCity(){
        return city;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getMaximum() {
        return maximum;
    }

    public int getMinimum() {
        return minimum;
    }
    public int getTemperature() {
        return temperature;
    }
}
