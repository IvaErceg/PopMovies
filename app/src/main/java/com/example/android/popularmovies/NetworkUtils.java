package com.example.android.popularmovies;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/*
Class for implementing network related jobs
 */
public class NetworkUtils {
    //constants
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String QUERY = "api_key";
    private static final String API_KEY = "";

    //Methods are modified from course Networking in Android Basics Nanodegree
    /**
     * From given String, create URL, connect to API, and parse response into list of Movie Objects
     *
     * @param requestUrl Sort type we use to construct full url
     * @return ArrayList<Movie> constructed from parsing api response
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        // Create URL object
        URL url = getUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of Movies
        return extractFeatureFromJson(jsonResponse);
    }

    /**
     * Get URL from String(sort type user selected)
     *
     * @param sort String that we need to construct full URL, describing sort type
     * @return URL form of string
     */
    public static URL getUrl(String sort) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(sort).appendQueryParameter(QUERY, API_KEY).build();
        URL moviesUrl = null;
        try {
            moviesUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "my url is " + moviesUrl);
        return moviesUrl;
    }

    /**
     * Open connection with given URL and get the response
     *
     * @param url URL that hits API's endpoint
     * @return response from server in form of String
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving movie's data.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server
     *
     * @param inputStream response from server in bytes
     * @return String Response converted into String
     * @throws IOException
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Parse server's response and construct list of objects from that data
     *
     * @param movieJSON String containing server response
     * @return list of Movie objects
     */
    private static List<Movie> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding movies to
        List<Movie> movies = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONArray results = baseJsonResponse.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject currentMovie = results.getJSONObject(i);
                String title = currentMovie.getString("original_title");
                String posterPath = currentMovie.getString("poster_path");
                String release = currentMovie.getString("release_date");
                String overview = currentMovie.getString("overview");
                String votes = currentMovie.getString("vote_average");
                //create News object
                Movie movie = new Movie(title, overview, release, votes);
                movie.setPoster(posterPath);
                // Add single Movie object to the list of movies.
                movies.add(movie);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }
        // Return the list of news
        return movies;
    }
}