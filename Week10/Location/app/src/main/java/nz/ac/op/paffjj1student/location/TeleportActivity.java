package nz.ac.op.paffjj1student.location;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class TeleportActivity extends AppCompatActivity {

    private IGetCoordinates coordinatesGenerator;
    //Tv displays to show output
    private TextView latitudeDisplay;
    private TextView longitudeDisplay;
    private TextView cityDisplay;
    private double latitude;
    private double longitude;
    private Random randGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleport);

        randGen = new Random();
        coordinatesGenerator = new RandomCoordinates(randGen);

        //display controls
        latitudeDisplay = (TextView) findViewById(R.id.textViewLatitudeDisplay);
        cityDisplay = (TextView) findViewById(R.id.textViewClosestCity);
        longitudeDisplay = (TextView) findViewById(R.id.textViewLongitudeDisplay);

        //creates button and sets custom listener handler
        Button teleportButton = (Button) findViewById(R.id.buttonTeleport);
        teleportButton.setOnClickListener(new ButtonClickHandler());
    }

    //extracts city from json object
    public void DisplayInfo(String jsonString){
        try {
            //json root object
            JSONObject cityData = new JSONObject(jsonString);
            //gets name and country code from json object
            String cityName = cityData.getString(APIConstants.cityName);
            String countryCode = cityData.getString(APIConstants.countryCode);

            //displays city info
            cityDisplay.setText(cityName + ", " + countryCode);
            latitudeDisplay.setText(String.format("%.3f", latitude));
            longitudeDisplay.setText(String.format("%.3f", longitude));

        } catch (JSONException je) {
            Toast.makeText(getApplicationContext(), "Json read error", Toast.LENGTH_LONG).show();
        }
    }

    //--------------------------------------------------------------------------------------------------
    //inner class for handling downloads from web
    public class AsyncWebDownloader extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            //sets json to empty result so that loop runs thru first time
            String jsonString = APIConstants.emptyResult;

            //loops till coordinates corresponding to a valid city are found
            while(jsonString.equals(APIConstants.emptyResult)) {
                try {
                    //gets random coordinates
                    latitude = coordinatesGenerator.CalculateLatitude();
                    longitude = coordinatesGenerator.CalculateLongitude();

                    //url with arguments that gets city based on lat/long input
                    String urlString = "http://geoplugin.net/extras/location.gp?lat=" + latitude + "&long=" + longitude + "&format=json";
                    //convert to url object
                    URL URLobject = new URL(urlString);
                    //create http connection object
                    HttpURLConnection connection = (HttpURLConnection) URLobject.openConnection();
                    //connect
                    connection.connect();
                    // if it doesnt return 200(code for successful connection) then pop an error message
                    int responseCode = connection.getResponseCode();
                    if (responseCode != 200) {
                        Toast.makeText(getApplicationContext(), "HTTP error:" + responseCode, Toast.LENGTH_LONG).show();
                    }
                    //setup input stream and input & buffered readers
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    //read input line by line into stringBuilder using buffered reader
                    String lineString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((lineString = bufferedReader.readLine()) != null) {
                        stringBuilder = stringBuilder.append(lineString);
                    }

                    //get string from stringBuilder
                    jsonString = stringBuilder.toString();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error getting JSON string from URL.", Toast.LENGTH_LONG).show();
                }
            }
            //returns json string
            return jsonString;
        }
        //calls display info passing the json string
        protected void onPostExecute(String jsonString){
            DisplayInfo(jsonString);
        }
    }

    //--------------------------------------------------------------------------------------------------
    //button click handler creates AsyncTask and executes it
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            AsyncWebDownloader loader = new AsyncWebDownloader();

            //checks internet connection
            if(checkInternet() == true) {
                //executes async object and pops toast
                loader.execute();
                Toast.makeText(getApplicationContext(), "Downloading data...", Toast.LENGTH_SHORT).show();
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
