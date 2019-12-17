package com.example.android.weatherapp;

import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
    public static Weather fetchWeatherDetails(String weatherUrl){
        String jsonResponse = "";
        try {
            URL url = createUrl(weatherUrl);
            jsonResponse = makeHttpRequest(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Weather weather= readFromJson(jsonResponse);
        return weather;
    }
    public static URL createUrl(String resourseUrl) throws MalformedURLException {
        URL url = new URL(resourseUrl);
        return url;
    }
    public static String makeHttpRequest(URL url){
        String jsonResponse="";
        if(url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream =null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(1500);
            if(httpURLConnection.getResponseCode() == 200) {
                inputStream=httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }
    public static String readFromStream(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line= "";
        String data="";
        while(line!=null){
           line = bufferedReader.readLine();
           data = data + line;
        }
        return data;
    }
    public static Weather readFromJson(String jsonResponse){
        int temp=0;
        int humidity=0;int max=0,min=0;
        String city="",weatherInfo="";
        try {
            /// base Json OBject
            JSONObject baseJson = new JSONObject(jsonResponse);
            /// main branch
            String main = baseJson.getString("main");
            /// the weather branch
            String weather = baseJson.getString("weather");
            JSONArray weather_Json = new JSONArray(weather);
            JSONObject jsonObject = weather_Json.getJSONObject(0);
            weatherInfo = jsonObject.getString("main");
            JSONObject mainJson = new JSONObject(main);
            temp = mainJson.getInt("temp");
            humidity = mainJson.getInt("humidity");
            max = mainJson.getInt("temp_max");
            min = mainJson.getInt("temp_min");
            city = baseJson.getString("name");
            Log.i("TAG_I",""+temp+"-"+humidity+" ");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Weather(temp,city,humidity,max,min,weatherInfo);
    }
}
