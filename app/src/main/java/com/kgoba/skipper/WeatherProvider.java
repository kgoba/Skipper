package com.kgoba.skipper;

import android.net.Uri;
import android.util.JsonReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * Created by karlis on 23.09.14.
 */
public class WeatherProvider {
    public void testJson() {
        String jsonRaw = "{\"cod\":\"200\",\"message\":0.0044,\"city\":{\"id\":456172,\"name\":\"Riga\",\"coord\":{\"lon\":24.1,\"lat\":56.950001},\"country\":\"LV\",\"population\":0},\"cnt\":27,\"list\":[{\"dt\":1411462800,\"main\":{\"temp\":281.15,\"temp_min\":281.15,\"temp_max\":282.246,\"pressure\":1014.27,\"sea_level\":1019.1,\"grnd_level\":1014.27,\"humidity\":80,\"temp_kf\":-1.1},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":8.41,\"deg\":359.501},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-23 09:00:00\"},{\"dt\":1411473600,\"main\":{\"temp\":280.86,\"temp_min\":280.86,\"temp_max\":281.898,\"pressure\":1015.98,\"sea_level\":1020.84,\"grnd_level\":1015.98,\"humidity\":76,\"temp_kf\":-1.04},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":9.06,\"deg\":344.003},\"rain\":{\"3h\":1},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-23 12:00:00\"},{\"dt\":1411484400,\"main\":{\"temp\":280.62,\"temp_min\":280.62,\"temp_max\":281.603,\"pressure\":1017.52,\"sea_level\":1022.29,\"grnd_level\":1017.52,\"humidity\":66,\"temp_kf\":-0.99},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":8.96,\"deg\":341.5},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-23 15:00:00\"},{\"dt\":1411495200,\"main\":{\"temp\":279.41,\"temp_min\":279.41,\"temp_max\":280.342,\"pressure\":1018.81,\"sea_level\":1023.88,\"grnd_level\":1018.81,\"humidity\":64,\"temp_kf\":-0.93},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":7.02,\"deg\":343.506},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-23 18:00:00\"},{\"dt\":1411506000,\"main\":{\"temp\":279.22,\"temp_min\":279.22,\"temp_max\":280.094,\"pressure\":1019.97,\"sea_level\":1025.06,\"grnd_level\":1019.97,\"humidity\":69,\"temp_kf\":-0.88},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":4.53,\"deg\":351.5},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-23 21:00:00\"},{\"dt\":1411516800,\"main\":{\"temp\":279.13,\"temp_min\":279.13,\"temp_max\":279.95,\"pressure\":1021.38,\"sea_level\":1026.39,\"grnd_level\":1021.38,\"humidity\":63,\"temp_kf\":-0.82},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":3.5,\"deg\":352.504},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-24 00:00:00\"},{\"dt\":1411527600,\"main\":{\"temp\":278.23,\"temp_min\":278.23,\"temp_max\":278.999,\"pressure\":1022.38,\"sea_level\":1027.41,\"grnd_level\":1022.38,\"humidity\":67,\"temp_kf\":-0.77},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":1.37,\"deg\":330.506},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-24 03:00:00\"},{\"dt\":1411538400,\"main\":{\"temp\":278.6,\"temp_min\":278.6,\"temp_max\":279.308,\"pressure\":1022.78,\"sea_level\":1027.76,\"grnd_level\":1022.78,\"humidity\":73,\"temp_kf\":-0.71},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":2.22,\"deg\":271.5},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-24 06:00:00\"},{\"dt\":1411549200,\"main\":{\"temp\":283.79,\"temp_min\":283.79,\"temp_max\":284.448,\"pressure\":1022.98,\"sea_level\":1027.79,\"grnd_level\":1022.98,\"humidity\":68,\"temp_kf\":-0.66},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.97,\"deg\":270.502},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-24 09:00:00\"},{\"dt\":1411560000,\"main\":{\"temp\":285.48,\"temp_min\":285.48,\"temp_max\":286.081,\"pressure\":1022.39,\"sea_level\":1027.15,\"grnd_level\":1022.39,\"humidity\":64,\"temp_kf\":-0.6},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":3.82,\"deg\":250.002},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-24 12:00:00\"},{\"dt\":1411570800,\"main\":{\"temp\":284.88,\"temp_min\":284.88,\"temp_max\":285.432,\"pressure\":1021.2,\"sea_level\":1025.85,\"grnd_level\":1021.2,\"humidity\":62,\"temp_kf\":-0.55},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":4.12,\"deg\":200.001},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-24 15:00:00\"},{\"dt\":1411581600,\"main\":{\"temp\":282.9,\"temp_min\":282.9,\"temp_max\":283.396,\"pressure\":1020.33,\"sea_level\":1025.2,\"grnd_level\":1020.33,\"humidity\":92,\"temp_kf\":-0.49},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":5.62,\"deg\":194.004},\"rain\":{\"3h\":1},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-24 18:00:00\"},{\"dt\":1411592400,\"main\":{\"temp\":282.81,\"temp_min\":282.81,\"temp_max\":283.248,\"pressure\":1019.62,\"sea_level\":1024.48,\"grnd_level\":1019.62,\"humidity\":92,\"temp_kf\":-0.44},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":6.01,\"deg\":196.003},\"rain\":{\"3h\":1},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-24 21:00:00\"},{\"dt\":1411603200,\"main\":{\"temp\":281.75,\"temp_min\":281.75,\"temp_max\":282.132,\"pressure\":1019.06,\"sea_level\":1023.9,\"grnd_level\":1019.06,\"humidity\":86,\"temp_kf\":-0.38},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":5.66,\"deg\":191},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-25 00:00:00\"},{\"dt\":1411614000,\"main\":{\"temp\":280.63,\"temp_min\":280.63,\"temp_max\":280.962,\"pressure\":1018.1,\"sea_level\":1023.02,\"grnd_level\":1018.1,\"humidity\":87,\"temp_kf\":-0.33},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":4.92,\"deg\":175.503},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-25 03:00:00\"},{\"dt\":1411624800,\"main\":{\"temp\":281.48,\"temp_min\":281.48,\"temp_max\":281.756,\"pressure\":1017.7,\"sea_level\":1022.56,\"grnd_level\":1017.7,\"humidity\":87,\"temp_kf\":-0.27},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":6.02,\"deg\":172},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-25 06:00:00\"},{\"dt\":1411635600,\"main\":{\"temp\":284.47,\"temp_min\":284.47,\"temp_max\":284.691,\"pressure\":1017.58,\"sea_level\":1022.42,\"grnd_level\":1017.58,\"humidity\":77,\"temp_kf\":-0.22},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":7.02,\"deg\":185.501},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-25 09:00:00\"},{\"dt\":1411646400,\"main\":{\"temp\":283.59,\"temp_min\":283.59,\"temp_max\":283.751,\"pressure\":1017.78,\"sea_level\":1022.33,\"grnd_level\":1017.78,\"humidity\":90,\"temp_kf\":-0.16},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":7.62,\"deg\":197.504},\"rain\":{\"3h\":1},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-25 12:00:00\"},{\"dt\":1411657200,\"main\":{\"temp\":284.3,\"temp_min\":284.3,\"temp_max\":284.414,\"pressure\":1017.19,\"sea_level\":1021.93,\"grnd_level\":1017.19,\"humidity\":87,\"temp_kf\":-0.11},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":6.36,\"deg\":195.501},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-25 15:00:00\"},{\"dt\":1411668000,\"main\":{\"temp\":284.27,\"temp_min\":284.27,\"temp_max\":284.321,\"pressure\":1016.96,\"sea_level\":1021.67,\"grnd_level\":1016.96,\"humidity\":94,\"temp_kf\":-0.05},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":5.25,\"deg\":200.001},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-25 18:00:00\"},{\"dt\":1411678800,\"main\":{\"temp\":283.398,\"temp_min\":283.398,\"temp_max\":283.398,\"pressure\":1016.7,\"sea_level\":1021.56,\"grnd_level\":1016.7,\"humidity\":93},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"02n\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":3.8,\"deg\":223.508},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-25 21:00:00\"},{\"dt\":1411689600,\"main\":{\"temp\":282.601,\"temp_min\":282.601,\"temp_max\":282.601,\"pressure\":1016.55,\"sea_level\":1021.58,\"grnd_level\":1016.55,\"humidity\":93},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":4.27,\"deg\":229.002},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-26 00:00:00\"},{\"dt\":1411700400,\"main\":{\"temp\":281.914,\"temp_min\":281.914,\"temp_max\":281.914,\"pressure\":1016.51,\"sea_level\":1021.34,\"grnd_level\":1016.51,\"humidity\":93},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":4.08,\"deg\":228.003},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2014-09-26 03:00:00\"},{\"dt\":1411711200,\"main\":{\"temp\":283.907,\"temp_min\":283.907,\"temp_max\":283.907,\"pressure\":1016.45,\"sea_level\":1021.3,\"grnd_level\":1016.45,\"humidity\":95},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":4.25,\"deg\":220},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-26 06:00:00\"},{\"dt\":1411722000,\"main\":{\"temp\":287.885,\"temp_min\":287.885,\"temp_max\":287.885,\"pressure\":1016.98,\"sea_level\":1021.75,\"grnd_level\":1016.98,\"humidity\":89},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":4.71,\"deg\":225.005},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-26 09:00:00\"},{\"dt\":1411732800,\"main\":{\"temp\":289.306,\"temp_min\":289.306,\"temp_max\":289.306,\"pressure\":1016.49,\"sea_level\":1021.42,\"grnd_level\":1016.49,\"humidity\":86},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":4.71,\"deg\":218.502},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-26 12:00:00\"},{\"dt\":1411743600,\"main\":{\"temp\":289.244,\"temp_min\":289.244,\"temp_max\":289.244,\"pressure\":1016.02,\"sea_level\":1020.76,\"grnd_level\":1016.02,\"humidity\":83},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":3.92,\"deg\":226.001},\"rain\":{\"3h\":0},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2014-09-26 15:00:00\"}]}";
    }

    public void doStuff() {
        HttpClient httpclient = new DefaultHttpClient();
        //String URL = "http://api.openweathermap.org/data/2.5/forecast/forecast?lat=56.95&lon=24.1&cnt=10&mode=json";

        float latitude = 56.95f;
        float longitude = 24.1f;

        String latitudeStr = String.format("%.2f", latitude);
        String longitudeStr = String.format("%.2f", longitude);

        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority("api.openweathermap.org")
                .appendPath("data/2.5/forecast/forecast")
                .appendQueryParameter("lat", latitudeStr)
                .appendQueryParameter("lon", longitudeStr)
                .appendQueryParameter("mode", "json")
                .build();

        HttpGet getRequest = new HttpGet(uri.toString());
        try {
            HttpResponse response = httpclient.execute(getRequest);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                InputStream is = httpEntity.getContent();
                try {
                    JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                    parseJson(reader);
                }
                finally {
                    is.close();
                }
            }
        }
        catch (ClientProtocolException e) {

        }
        catch (IOException e) {

        }
    }

    public void parseJson(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("list")) {
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }
}
