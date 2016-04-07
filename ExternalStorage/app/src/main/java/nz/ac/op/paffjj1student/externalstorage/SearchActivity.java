package nz.ac.op.paffjj1student.externalstorage;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ListView cityList;
    private Spinner countrySpinner;
    private Button loadCities;
    private  DatabaseManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //control objects
        cityList = (ListView) findViewById(R.id.listViewCities);
        countrySpinner = (Spinner) findViewById(R.id.spinnerCountries);
        loadCities = (Button) findViewById(R.id.buttonLoadCities);

        //database manager passes in activity instance
        dm = new DatabaseManager(this);

        //populate spinner
        setCountryList();

        //button handler
        loadCities.setOnClickListener(new LoadCitiesClickHandler());

    }

    //sets country spinner
    public void setCountryList(){

        List<String> countriesArray = new ArrayList<String>();

        //POPULATE COUNTRIES ARRAY FROM SQL

        countriesArray = dm.queryCountries();

        //set countries
        ArrayAdapter<String> countryNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countriesArray);
        countrySpinner.setAdapter(countryNamesAdapter);


    }

    // sets data to listview
    public void loadCities(){

        List<String> citiesArray = new ArrayList<String>();

        //POPULATE COUNTRIES ARRAY FROM SQL
        String currentCountry = countrySpinner.getSelectedItem().toString();

        citiesArray = dm.queryCities(currentCountry);

        //set countries
        ArrayAdapter<String> cityNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citiesArray);
        cityList.setAdapter(cityNamesAdapter);

    }

    //when clicked loads cities into listview
    public class LoadCitiesClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            loadCities();

        }
    }
}
