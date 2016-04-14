package nz.ac.op.paffjj1student.gettopartistimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetArtistImage extends AppCompatActivity {

    ImageView topArtistImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_artist_image);

        topArtistImage = (ImageView) findViewById(R.id.imageViewTopArtist);

        Button loadImage = (Button) findViewById(R.id.buttonGetImage);
        loadImage.setOnClickListener(new ButtonClickHandler());
    }

    //gets URL of image from json string
    public String getImageURL(String jsonRawInput) {

        String imageURL = null;
        try {
            //sets JSON obj with raw input
            JSONObject rawData = new JSONObject(jsonRawInput);
            //json obj = "artists" > "artist" > "image"
            JSONObject artistObject = rawData.getJSONObject("artists");
            JSONArray jsonArtists = artistObject.getJSONArray("artist");
            //gets json image array
            JSONObject topArtist = jsonArtists.getJSONObject(0);
            JSONArray imageURLArray =  topArtist.getJSONArray("image");
            //url for large image is located in this context within image array
            imageURL = imageURLArray.getJSONObject(2).getString("#text");
            if(imageURL.equals(null)) Toast.makeText(getApplicationContext(), "Error reading json input", Toast.LENGTH_LONG).show();

        } catch (JSONException je) {
            Toast.makeText(getApplicationContext(), "Error reading json string", Toast.LENGTH_LONG).show();
        }
        //returns image url
        return imageURL;
    }

    //--------------------------------------------------------------------------------------------------
    //inner class for handling downloads from web and setting listview
    public class AsyncWebDownloader extends AsyncTask<Void, Void, Bitmap> {

        //gets an input stream based on url
        public InputStream getStream(String urlString) throws IOException{
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
            //return input stream
            return connection.getInputStream();
        }

        //gets bitmap from url
        @Override
        protected Bitmap doInBackground(Void... args) {

            String imageURL;
            Bitmap artistImage = null;
            //url to fetch from
            String jsonURL = "http://ws.audioscrobbler.com/2.0/" +
                    "?method=chart.getTopArtists&limit=1&api_key=58384a2141a4b9737eacb9d0989b8a8c&format=json";
            try {
                //gets json string
                InputStreamReader inputStreamReader = new InputStreamReader(getStream(jsonURL));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String lineString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((lineString = bufferedReader.readLine()) != null) {
                    stringBuilder = stringBuilder.append(lineString);
                }
                //gets bitmap
                imageURL = getImageURL(stringBuilder.toString());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(getStream(imageURL));
                artistImage = BitmapFactory.decodeStream(bufferedInputStream);
            }catch(Exception e){
                e.printStackTrace();
            }
            //returns fetched bitmap
            return artistImage;
        }

        //sets imageview to fetched bitmap
        protected void onPostExecute(Bitmap artistImage){

            topArtistImage.setImageBitmap(artistImage);
        }
    }

    //--------------------------------------------------------------------------------------------------
    //button click handler creates AsyncTask and executes it
    public class ButtonClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            if (checkInternet() == true) {
                //creates webdownloader and executes it
                AsyncWebDownloader loader = new AsyncWebDownloader();
                loader.execute();
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
