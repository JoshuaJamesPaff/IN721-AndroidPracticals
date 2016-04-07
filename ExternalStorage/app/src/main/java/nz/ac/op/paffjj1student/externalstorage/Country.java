package nz.ac.op.paffjj1student.externalstorage;

import java.util.ArrayList;

/**
 * Created by Mr Nobody on 8/04/2016.
 */
public class Country {

    private String name;
    private ArrayList<String> cities;

    public Country(String name, String city){
        this.name = name;
        cities.add(city);
    }

    public String toString(){
        return name;
    }

    public ArrayList<String> getCities(){
        return cities;
    }
}
