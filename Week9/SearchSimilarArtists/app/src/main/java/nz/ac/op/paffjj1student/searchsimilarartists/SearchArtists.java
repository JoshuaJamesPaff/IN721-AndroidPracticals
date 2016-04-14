package nz.ac.op.paffjj1student.searchsimilarartists;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchArtists extends AppCompatActivity {

    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artists);

        //serach bar and button with handler
        searchBar = (EditText) findViewById(R.id.editTextSearch);
        Button searchButton = (Button) findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new ButtonClickHandler());
    }

    //converts json string into formatted list of strings and sets that to listview
    public void setListview(String jsonRawInput) {

        //initialises list
        List<String> similarArtists = new ArrayList<>();
        JSONArray jsonArrayArtists = null;

        try {
            //sets JSON obj with raw input
            JSONObject rawData = new JSONObject(jsonRawInput);
            //gets array of artists from root object
            JSONObject rootObject = rawData.getJSONObject("similarartists");
            jsonArrayArtists = rootObject.getJSONArray("artist");

        } catch (JSONException je) {
            //Toast.makeText(getApplicationContext(), "Please enter a search term", Toast.LENGTH_LONG).show();
        }

        //if no artists in array pop toast
        if(jsonArrayArtists == null) {
            Toast.makeText(getApplicationContext(), "No similar artists found.", Toast.LENGTH_LONG).show();

        }else{
            //loops thru json array and concatenates the artist name with the listener count and loads into a list<string>
            for (int i = 0; i < jsonArrayArtists.length(); i++) {

                try {
                    JSONObject currentArtist = jsonArrayArtists.getJSONObject(i);
                    String artistInfo = currentArtist.getString("name");
                    similarArtists.add(artistInfo);
                } catch (JSONException je) {
                    Toast.makeText(getApplicationContext(), "Error getting artist from JSON string.", Toast.LENGTH_LONG).show();
                }
            }

            //listview object
            ListView displayArtists = (ListView) findViewById(R.id.listViewArtists);

            //sets adapter to listview
            displayArtists.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, similarArtists));
        }
    }

    //--------------------------------------------------------------------------------------------------
    //inner class for handling downloads from web and setting listview
    public class AsyncWebDownloader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... artistToSearch) {

            String JSONstring = null;

            try{

                //url with arguments that gets the top 10 similar artists information in json format from last.fm
                String urlString = "http://ws.audioscrobbler.com/2.0/?method=artist.getSimilar&limit=10&api_key=58384a2141a4b9737eacb9d0989b8a8c&format=json&artist=" +
                        artistToSearch[0];
                //convert to url object
                URL URLobject = new URL(urlString);
                //create http connection object
                HttpURLConnection connection = (HttpURLConnection) URLobject.openConnection();
                //connect
                connection.connect();

                // if it doesnt return 200(code for successful connection) then pop an error message
                int responseCode = connection.getResponseCode();
                if(responseCode != 200){
                    Toast.makeText(getApplicationContext(), "HTTP error:" + responseCode, Toast.LENGTH_LONG).show();
                }

                //setup input stream and input & buffered readers
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //read input line by line into stringBuilder using buffered reader
                String lineString;
                StringBuilder stringBuilder = new StringBuilder();
                while((lineString = bufferedReader.readLine()) != null){
                    stringBuilder = stringBuilder.append(lineString);
                }

                //get string from stringBuilder
                JSONstring = stringBuilder.toString();

            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "Error getting JSON string from URL.", Toast.LENGTH_LONG).show();
            }
            //returns json string
            return JSONstring;
        }

        //sets listview to custom array adapter populated with json data returned in "doInBackground"
        protected void onPostExecute(String jsonString){

            //calls a method that sets the listview based on json string
            setListview(jsonString);
        }
    }

    //--------------------------------------------------------------------------------------------------
    //button click handler creates AsyncTask and executes it
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //checks internet connection (popping toast if none)
            if(checkInternet() == true) {
                //error handling for empty search term
                String searchTerm;
                if (searchBar.getText().toString().equals("")) {
                    searchTerm = "?";
                } else {
                    searchTerm = searchBar.getText().toString();
                }

                //creates webdownloader passing in search term
                AsyncWebDownloader loader = new AsyncWebDownloader();
                loader.execute(searchTerm);

            }else{
                Toast.makeText(getApplicationContext(), "No Internet Connection Available", Toast.LENGTH_LONG).show();
            }
        }
    }

    //checks internet
    public boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
