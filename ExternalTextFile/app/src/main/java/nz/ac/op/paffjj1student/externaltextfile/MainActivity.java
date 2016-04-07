package nz.ac.op.paffjj1student.externaltextfile;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    //listview object
    private ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = (ListView) findViewById(R.id.listViewCities);

        //tries to populate list or throws error
        try{
            setMenuList();
        }
        catch (IOException e){
            Toast.makeText(getApplicationContext(), "Error reading file",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // sets labels to list view using adapter
    public void setMenuList() throws IOException{

        String filename = "cities.txt";

        //asset mangager for file reader
        AssetManager am = getAssets();
        InputStream is = am.open(filename);
        InputStreamReader ir = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(ir);

        //get strings from file
        List<String> citiesArray = new ArrayList<String>();

        //tries to read data from text file
        String currentCity;

        while ((currentCity = br.readLine()) != null) {
            citiesArray.add(currentCity);
        }

        //closes buffered reader
        br.close();

        //array adapter created then sets cities to list view
        ArrayAdapter<String> menuNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citiesArray);
        cityList.setAdapter(menuNamesAdapter);

    }

}
