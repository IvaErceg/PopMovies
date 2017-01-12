package com.example.android.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie/";
    private static final String QUERY = "q";
    private static final String SORT = "sort_by";
    private static final String API_KEY = "api key here";
    private static final String sortBy = "popularity.desc";

    private TextView displayResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayResponse = (TextView) findViewById(R.id.tw_display);
        URL moviesUrl = getUrl();
        try {
            displayResponse.setText(getResponseFromHttp(moviesUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(QUERY, API_KEY).appendQueryParameter(SORT, sortBy).build();
        URL moviesUrl = null;
        try {
            moviesUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return moviesUrl;
    }

    public String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String response = null;
        try {
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                response = readStream(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response;
    }

    private String readStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
