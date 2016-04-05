package nz.ac.op.paffjj1student.holidayadapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Attr;

public class Activities extends AppCompatActivity {

    private Attraction[] attractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        //sets up array of attractions
        initialiseImages();

        AttractionArrayAdapter arrayAdapter = new AttractionArrayAdapter(this, R.layout.custom_listview_item, attractions);

        ListView displayActivities = (ListView) findViewById(R.id.listViewActivities);
        displayActivities.setAdapter(arrayAdapter);
    }

    //sets images and their names
    private void initialiseImages(){

        Resources resourceMachine = getResources();
        Drawable castle = resourceMachine.getDrawable(R.drawable.larnach_castle, null);
        Drawable pool = resourceMachine.getDrawable(R.drawable.moana_pool, null);
        Drawable monarch = resourceMachine.getDrawable(R.drawable.monarch, null);
        Drawable octagon = resourceMachine.getDrawable(R.drawable.octagon, null);
        Drawable olveston = resourceMachine.getDrawable(R.drawable.olveston, null);
        Drawable op = resourceMachine.getDrawable(R.drawable.op, null);
        Drawable peninsula = resourceMachine.getDrawable(R.drawable.peninsula, null);
        Drawable salt_pool = resourceMachine.getDrawable(R.drawable.salt_water_pool, null);
        Drawable speights = resourceMachine.getDrawable(R.drawable.speights_brewery, null);
        Drawable beach = resourceMachine.getDrawable(R.drawable.st_kilda_beach, null);
        Drawable railway = resourceMachine.getDrawable(R.drawable.taeri_gorge_railway, null);
        Drawable cathedral = resourceMachine.getDrawable(R.drawable.ic_launcher, null);

        attractions = new Attraction[12];
        attractions[0] = new Attraction("Larnach Castle", castle);
        attractions[1] = new Attraction("Moana Pool", pool);
        attractions[2] = new Attraction("Monarch Boat", monarch);
        attractions[3] = new Attraction("Octagon",  octagon);
        attractions[4] = new Attraction("Olveston", olveston);
        attractions[5] = new Attraction("Otago Polytechnic", op);
        attractions[6] = new Attraction("Otago Peninsula", peninsula);
        attractions[7] = new Attraction("Salt-water Pool", salt_pool);
        attractions[8] = new Attraction("Speights Brewery", speights);
        attractions[9] = new Attraction("St Kilda Beach", beach);
        attractions[10] = new Attraction("Taieri Railway", railway);
        attractions[11] = new Attraction("Cathedral", cathedral);

    }

    //inner class extends ArrayAdapter class to display an imageview beside a textview
    public class AttractionArrayAdapter extends ArrayAdapter<Attraction>
    {

        public AttractionArrayAdapter(Context context, int resource, Attraction[] objects){
            super(context, resource, objects);
        }

        //overide getview method
        @Override
        public View getView(int position, View concertView, ViewGroup container){

            //inflate custom layout
            LayoutInflater inflater = LayoutInflater.from(Activities.this);
            View customView = inflater.inflate(R.layout.custom_listview_item, container, false);

            //imageview and textview objects
            ImageView itemImageView = (ImageView) customView.findViewById(R.id.ivItemImage);
            TextView itemTextView = (TextView) customView.findViewById(R.id.tvItemWords);

            //get current holiday from the array
            Attraction currentItem = getItem(position);

            //set current view data
            itemImageView.setImageDrawable(currentItem.attractionImage);
            itemTextView.setText(currentItem.toString());

            return  customView;
        }

    }
}
