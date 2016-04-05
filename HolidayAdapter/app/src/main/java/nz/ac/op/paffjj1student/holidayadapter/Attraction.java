package nz.ac.op.paffjj1student.holidayadapter;


import android.graphics.drawable.Drawable;

/**
 * Created by Mr Nobody on 1/04/2016.
 */
public class Attraction {

    String attractionName;
    Drawable attractionImage;

    public Attraction(String attractionName, Drawable attractionImage){
        this.attractionName = attractionName;
        this.attractionImage = attractionImage;

    }

    @Override
    public String toString(){
        return attractionName;
    }
}

