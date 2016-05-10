package nz.ac.op.paffjj1student.location;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Random;

public class TeleportActivity extends AppCompatActivity {


    //Tv displays to show output
    private TextView latitudeDisplay;
    private TextView longitudeDisplay;
    private TextView cityDisplay;
    private ImageView imageDisplay;

    //holds data on current location
    private double latitude;
    private double longitude;
    private String city;
    private String countryCode;
    private Bitmap cityBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleport);

        //location manager setup
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
        String providerName = lm.getBestProvider(criteria, false);

        //turn location polling on
        lm.requestLocationUpdates(providerName, 200, 1, new CustomLocationlistener());

        //text views
        latitudeDisplay = (TextView) findViewById(R.id.textViewLatitudeDisplay);
        cityDisplay = (TextView) findViewById(R.id.textViewClosestCity);
        longitudeDisplay = (TextView) findViewById(R.id.textViewLongitudeDisplay);
        imageDisplay = (ImageView) findViewById(R.id.imageViewCity);

    }

    //displays data to views
    public void DisplayInfo(){
        cityDisplay.setText(city + ", " + countryCode);
        latitudeDisplay.setText(String.format("%.3f", latitude));
        longitudeDisplay.setText(String.format("%.3f", longitude));
        imageDisplay.setImageBitmap(cityBitmap);
    }

    //-------------------------------------------------------------------------------------------------
    //custom location listener
    private class CustomLocationlistener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            AsyncCityDownloader loader = new AsyncCityDownloader();

            //checks internet connection before executing
            if(checkInternetConnected()) {
                loader.execute();
            }else{
                Toast.makeText(getApplicationContext(), "No Internet Connection Available", Toast.LENGTH_LONG).show();
            }

            DisplayInfo();
            Toast.makeText(getApplicationContext(), "location changed", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "provider enabled", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "provider disabled", Toast.LENGTH_LONG).show();
        }
    }

    //--------------------------------------------------------------------------------------------------
    //inner class for handling downloads from web
    public class AsyncCityDownloader extends AsyncTask<Void, Void, Bitmap> {

        //process dialog for showing that data is being downloaded
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(TeleportActivity.this);
            pd.setMessage("Downloading Data...");
            pd.show();
        }
        @Override
        protected Bitmap doInBackground(Void... params) {

            //sets json to empty
            String cityNameJson = APIConstants.emptyResult;
            String cityPictureJson = null;
            String urlString = "http://geoplugin.net/extras/location.gp?lat=" + latitude + "&long=" + longitude + "&format=json";
            try {
                //get string from stringBuilder
                cityNameJson = getJSONData(urlString);

            } catch (Exception e) {
                Log.w("JSON", "error getting json");
            }
            //sets city name and area
            calculateCityName(cityNameJson);

            // get image json object
            //api key
            String apiKey = "eda41a123d459be0f85276d37290651e";
            //make url
            String imageUrlJson = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" + apiKey + "&tags=" + city + "&format=json&nojsoncallback=1";
            //image jason string
            cityPictureJson = getJSONData(imageUrlJson);

            //gets url of image from json
            String imageUrlString = calculateCityImageURL(cityPictureJson);

            //returns bitmap
            return getCityBitmap(imageUrlString);
        }
        //calls display info passing the json string
        protected void onPostExecute(Bitmap cityImage){

            cityBitmap = cityImage;
            DisplayInfo();

            //kills loading dialog
            pd.dismiss();
        }
    }
    //----------------------------------------------------------------------------------------------
    // other methods
    public void calculateCityName(String jsonString){

        try {
            //json root object
            JSONObject cityData = new JSONObject(jsonString);
            //gets name and country code from json object
            city = cityData.getString(APIConstants.cityName);
            countryCode = cityData.getString(APIConstants.countryCode);
        } catch (JSONException je) {
            Toast.makeText(getApplicationContext(), "Json read city info error", Toast.LENGTH_LONG).show();
        }
    }

    public String calculateCityImageURL(String jsonString){
        String imageURL = "";
        try {
            JSONObject data = new JSONObject(jsonString);
            JSONObject images = data.getJSONObject("photos");
            JSONArray imageArray = images.getJSONArray("photo");
            if(imageArray.length() > 0) {
                JSONObject imageData = imageArray.getJSONObject(0);
                String farmId = imageData.getString("farm");
                String serverId = imageData.getString("server");
                String secretId = imageData.getString("secret");
                String imageId = imageData.getString("id");
                imageURL = "https://farm" + farmId + ".staticflickr.com/" + serverId + "/" + imageId + "_" + secretId + ".jpg";
            }

        }catch (JSONException je) {
            Log.w("image", "error creating image");
        }
        if(imageURL.equals("")) {
            imageURL = "https://umexpert.um.edu.my/Avatar/no-image-found.jpg";
        }else{
            Log.w("image", imageURL);
        }
        return imageURL;
    }

    //gets input stream from url
    public InputStream getInputStream(String urlString){
        InputStream stream = null;
        try{
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
            stream = connection.getInputStream();

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting JSON string from URL.", Toast.LENGTH_LONG).show();
        }
        return stream;
    }

    //gets json string from url
    public String getJSONData(String urlString){
        String output = "";
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(getInputStream(urlString));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //read input line by line into stringBuilder using buffered reader
            String lineString;
            StringBuilder stringBuilder = new StringBuilder();
            while ((lineString = bufferedReader.readLine()) != null) {
                stringBuilder = stringBuilder.append(lineString);
            }
            output = stringBuilder.toString();

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting JSON string from URL.", Toast.LENGTH_LONG).show();
        }
        return output;
    }

    //gets bitmap from url
    public Bitmap getCityBitmap(String urlString){
        Bitmap cityImage = null;
        try{
            InputStream inputStream  = getInputStream(urlString);
            cityImage = BitmapFactory.decodeStream(inputStream);

        }catch (Exception e) {
            Log.w("Bitmap", "error decoding Bitmap");
        }
        return cityImage;
    }

    //checks internet
    public boolean checkInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
