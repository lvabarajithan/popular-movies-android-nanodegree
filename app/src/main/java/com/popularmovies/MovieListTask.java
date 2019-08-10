package com.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;

import com.popularmovies.model.Movie;
import com.popularmovies.util.Constants;
import com.popularmovies.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Abarajithan
 */
public class MovieListTask extends AsyncTask<String, Void, List<Movie>> {

    private OnLoadListener<Movie> loadListener;

    MovieListTask(OnLoadListener<Movie> loadListener) {
        this.loadListener = loadListener;
    }

    @Override
    protected List<Movie> doInBackground(String... endpoints) {
        try {
            return makeApiRequest(endpoints[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Movie> makeApiRequest(String endpoint) throws IOException {
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            String urlStr = new Uri.Builder()
                    .scheme("https")
                    .path(Constants.API_PREFIX)
                    .appendPath(endpoint)
                    .appendQueryParameter(Constants.PARAM_API_KEY, BuildConfig.TMDB_API)
                    .toString();
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error: " + responseCode);
            }

            stream = connection.getInputStream();
            String json = readFromStream(stream);

            return JsonUtil.fromJson(json);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }
        return null;
    }

    private String readFromStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    @Override
    protected void onPostExecute(List<Movie> list) {
        super.onPostExecute(list);
        loadListener.onLoad(list);
    }

}