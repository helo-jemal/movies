package helo.mali.movies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    //https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=1afe9082c06f2e12037f4958f570ee76
    //https://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=1afe9082c06f2e12037f4958f570ee76


    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3";
    private static final String PATH_DISCOVER = "discover";
    private static final String PATH_MOVIES = "movie";


    /** Values for the URI*/
    private static final String apiKey = "1afe9082c06f2e12037f4958f570ee76";
    private static String sortBy = "";


    /** Keys for URI*/
    private static final String SORT_BY_PARAM = "sort_by";
    private static final String API_KEY_PARAM = "api_key";

    /** Method for building request URL for movies*/
    public static URL buildUrl(String sortBy){
        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(PATH_DISCOVER)
                .appendPath(PATH_MOVIES)
                .appendQueryParameter(SORT_BY_PARAM, sortBy)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URL is " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}