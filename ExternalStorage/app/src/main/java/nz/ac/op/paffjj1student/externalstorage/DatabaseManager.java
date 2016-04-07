package nz.ac.op.paffjj1student.externalstorage;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr Nobody on 8/04/2016.
 */
public class DatabaseManager {

    private SQLiteDatabase countries;


    public DatabaseManager(Activity currentAct){

        //needs an activity object to create database
        countries = currentAct.openOrCreateDatabase("countries", Context.MODE_PRIVATE, null);
        setDatabase();

    }


    //sets up initial tables and data
    public void setDatabase(){

        //drops old table if its there
        String dropQuery = "DROP TABLE IF EXISTS tblCity";
        countries.execSQL(dropQuery);

        //creates table and then sets data
        String createQuery = "CREATE TABLE IF NOT EXISTS tblCity(" +
                             "cityID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                             "cityName TEXT NOT NULL, " +
                             "countryName TEXT NOT NULL);";

        countries.execSQL(createQuery);

        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Amsterdam', 'The Netherlands')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Whangarei', 'New Zealand')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Auckland', 'New Zealand')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Wellington', 'New Zealand')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Dunedin', 'New Zealand')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Christchurch', 'New Zealand')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Hamilton', 'New Zealand')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Bluff', 'New Zealand')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'L.A', 'U.S.A')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'San Antonio', 'U.S.A')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Perth', 'Australia')");
        countries.execSQL("INSERT INTO tblCity VALUES(null, 'Darwin', 'Australia')");

    }

    //gets list fo cities from db
    public List<String> queryCities(String country) {

        List<String> cities = new ArrayList<String>();

        //creates query string based on country argument and sends it to db
        String selectQuery = "SELECT cityName FROM tblCity WHERE countryName = '" + country + "'";
        Cursor recordSet = countries.rawQuery(selectQuery, null);

        int numberOfRecords = recordSet.getCount();
        int cityNameIndex = recordSet.getColumnIndex("cityName");
        recordSet.moveToFirst();

        for (int i = 0; i < numberOfRecords; i++) {

            cities.add(recordSet.getString(cityNameIndex));
            recordSet.moveToNext();
        }
        return cities;
    }

    //gets list of countries from db
    public List<String> queryCountries(){

        List<String> differentCountries = new ArrayList<String>();

        String selectCountriesQuery = "SELECT DISTINCT countryName FROM tblCity";
        Cursor recordSet = countries.rawQuery(selectCountriesQuery, null);

        int numberOfRecords = recordSet.getCount();
        int countryNameIndex = recordSet.getColumnIndex("countryName");

        recordSet.moveToFirst();

        for (int i = 0; i < numberOfRecords; i++) {

            differentCountries.add(recordSet.getString(countryNameIndex));
            recordSet.moveToNext();

        }
        return differentCountries;
    }
}
