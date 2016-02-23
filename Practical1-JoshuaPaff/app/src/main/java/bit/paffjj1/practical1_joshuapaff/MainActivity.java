package bit.paffjj1.practical1_joshuapaff;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates textview obj then changes it by calling RandomDog()
        TextView txtDogDisplay = (TextView) findViewById(R.id.textViewDogDisplay);
        txtDogDisplay.setText(RandomDog());

        //creates textview obj then changes it by calling ShowFebFridays()
        TextView txtDates = (TextView) findViewById(R.id.textViewTitle);
        txtDates.setText(ShowFebFridays());
    }

    public String RandomDog(){
        String breed = "";
        Random rand = new Random();
        int dogValue = rand.nextInt(4);

        switch(dogValue)
        {
            case 0:
                breed = "Poodle";
                break;
            case 1:
                breed = "Shar Pei";
                break;
            case 2:
                breed = "Newfie";
                break;
            case 3:
                breed = "Labrador";
                break;
        }

        return breed;
    }

    public String ShowFebFridays(){

        //loads resource into object
        Resources resourceResolver = getResources();
        int datesArray[] = resourceResolver.getIntArray(R.array.FebFridays);

        //builds string
        String febFridays = "February Fridays are on: ";
        for (int date:datesArray) {
            febFridays += (date + " ");
        }

        return febFridays;

    }

}
