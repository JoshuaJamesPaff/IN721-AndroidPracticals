package nz.ac.op.paffjj1student.topartists;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DisplayTopArtists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_top_artists);

        //creates button and sets custom handler
        Button loadTop20 = (Button) findViewById(R.id.buttonLoadTop20);
        loadTop20.setOnClickListener(new ButtonClickHandler());

    }

    //converts json string into formatted list of strings and sets that to listview
    public void setListview(String jsonRawInput) {

        //initialises list
        List<String> top20Artists = new ArrayList<>();
        JSONArray jsonArrayTop20 = null;

        try {
            //sets JSON obj with raw input
            JSONObject eventData = new JSONObject(jsonRawInput);

            //sets jason array of events and length of array (theres an "artists" object and an "artist" array)
            JSONObject eventObject = eventData.getJSONObject("artists");
            jsonArrayTop20 = eventObject.getJSONArray("artist");

        } catch (JSONException je) {
            Toast.makeText(getApplicationContext(), "JSON load data error", Toast.LENGTH_LONG).show();
        }

        //loops thru json array and concatenates the artist name with the listener count and loads into a list<string>
        for (int i = 0; i < jsonArrayTop20.length(); i++) {

            try {
                JSONObject currentArtist = jsonArrayTop20.getJSONObject(i);
                String artistInfo = currentArtist.getString("name") + " : " + currentArtist.getInt("listeners");
                top20Artists.add(artistInfo);
            }catch(JSONException je){
                Toast.makeText(getApplicationContext(), "Error getting artist from JSON string.", Toast.LENGTH_LONG).show();
            }
        }

        //listview object
        ListView displayArtists = (ListView) findViewById(R.id.listViewArtists);

        //sets adapter to listview
        displayArtists.setAdapter(new Top20ArrayAdapter(this, R.layout.custom_listview_layout, top20Artists));
    }

    //--------------------------------------------------------------------------------------------------
    //inner class for handling downloads from web and setting listview
    public class AsyncWebDownloader extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {

            String JSONstring = null;

            try{

                //url with arguments that gets the top 20 artists information in json format from last.fm
                String urlString = "http://ws.audioscrobbler.com/2.0/?method=chart.getTopArtists&limit=20&api_key=58384a2141a4b9737eacb9d0989b8a8c&format=json";

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
    //inner class extends ArrayAdapter class to display an textview beside a textview
    public class Top20ArrayAdapter extends ArrayAdapter<String>
    {
        public Top20ArrayAdapter(Context context, int resource, List<String> objects){
            super(context, resource, objects);
        }

        //overide getview method
        @Override
        public View getView(int position, View concertView, ViewGroup container){

            //inflate custom layout
            LayoutInflater inflater = LayoutInflater.from(DisplayTopArtists.this);
            View customView = inflater.inflate(R.layout.custom_listview_layout, container, false);

            //textviews for artist and listener count
            TextView artistTextView = (TextView) customView.findViewById(R.id.artistName);
            TextView listenerCountTextView = (TextView) customView.findViewById(R.id.artistListenerCount);

            //get current artist info from the list
            String currentArtist = getItem(position);

            //extracts artist and listenerCount from current string
            String artist = currentArtist.substring(0, currentArtist.indexOf(":"));
            String listenerCount = currentArtist.substring(currentArtist.indexOf(":") + 1, currentArtist.length());

            //sets text views
            artistTextView.setText(artist);
            listenerCountTextView.setText(listenerCount);

            return  customView;
        }

    }
    //--------------------------------------------------------------------------------------------------
    //button click handler creates AsyncTask and executes it
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            AsyncWebDownloader loader = new AsyncWebDownloader();
            loader.execute();

        }
    }


}
