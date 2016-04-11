package nz.ac.op.paffjj1student.jsonlistview;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private String jsonRawInput;
    private JSONArray jsonArrayEvents;
    private int numEvents;
    private ListView lvDisplayEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonRawInput = "";

        //listview to display json data
        lvDisplayEvents = (ListView) findViewById(R.id.listViewJsonDisplay);
        //button that loads data
        Button buttonLoad = (Button) findViewById(R.id.buttonLoadData);

        //sets handlers to controls
        lvDisplayEvents.setOnItemClickListener(new ListSelectHandler());
        buttonLoad.setOnClickListener(new ButtonClickHandler());



    }
    //CLICK HANDLERS-------------------------------------------------------------------------------

    //inner class for clicking on the listview to get description
    public class ListSelectHandler implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            try {
                //tries to get the description of the selected event
                Toast.makeText(getApplicationContext(), getDescription(position), Toast.LENGTH_LONG).show();

            }catch(JSONException je){
                //pops toast if json exception thrown
                Toast.makeText(getApplicationContext(), "Couldn't get description from JSON object", Toast.LENGTH_LONG).show();
            }
        }
    }

    //button click handler loads json data into list view
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //tries to load data, pops toast if exception thrown
            try {
                loadJSONData();
                ArrayAdapter<String> events = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, createListViewData(jsonRawInput));
                lvDisplayEvents.setAdapter(events);

            }catch(JSONException je){
                Toast.makeText(getApplicationContext(), "Failed to load JSON data into Listview", Toast.LENGTH_LONG).show();
            }
        }
    }
    //--------------------------------------------------------------------------------------

    //METHODS

    //loads data into class mermbers (throws exception otherwise)
    public void loadJSONData(){
        
        //tries to read input and return string
        try {
            //use assetmanager to get input stream from JSON file
            String filenameJSON = "dunedin_events.json";
            AssetManager am = getAssets();
            InputStream inputStream = am.open(filenameJSON);

            //prepare buffer array for read
            int fileSizeInBytes = inputStream.available();
            byte[] JSONBuffer = new byte[fileSizeInBytes];

            //read stream into buffer
            inputStream.read(JSONBuffer);
            inputStream.close();

            //create string from byte array
            jsonRawInput = new String(JSONBuffer);

            try {
                //sets JSON obj with raw input
                JSONObject eventData = new JSONObject(jsonRawInput);

                //sets jason array of events and length of array (theres an "events" object and an "event" array)
                JSONObject eventObject = eventData.getJSONObject("events");
                jsonArrayEvents = eventObject.getJSONArray("event");

            }catch(JSONException je){
                Toast.makeText(getApplicationContext(), "JSON load data error", Toast.LENGTH_LONG).show();
            }
            numEvents = jsonArrayEvents.length();

        //--------------------------------------------------------------------
        //else throws exception, pops toast with IO exception
        } catch(IOException ioE){
            Toast.makeText(getApplicationContext(), "IO Error", Toast.LENGTH_LONG).show();
        }
    }

    //returns the list of event titles from json object
    public List<String> createListViewData(String JSONInput) throws JSONException{

        List<String> events = new ArrayList<>();

        for(int i = 0; i < numEvents; i++){

            JSONObject currentEvent = jsonArrayEvents.getJSONObject(i);
            events.add(currentEvent.getString("title"));
        }
        return events;
    }

    //gets the description of an event based on index argument
    public String getDescription(int index) throws JSONException{

        JSONObject currentEvent = jsonArrayEvents.getJSONObject(index);
        return currentEvent.getString("description");
    }

}
