package nz.ac.op.paffjj1student.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BUTTON CODE
        //button object

        Button changeColorButton = (Button) findViewById(R.id.buttonChangeColor);
        //listener object
        ButtonClickHandler handler = new ButtonClickHandler();
        //bind handler to button
        changeColorButton.setOnClickListener(handler);
    }

    //gets data from previous activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent){

        // checks request code and result code
        if ((requestCode == 0)&&(resultCode == Activity.RESULT_OK)){

            //creates textview object to set color or
            TextView textToChange = (TextView) findViewById(R.id.textViewChangeColor);

            //gets the color passed in by the settings activity and also sets the default value at 0
            textToChange.setTextColor(dataIntent.getIntExtra("colorOfText", 0));
        }
    }

    //click handler inner class
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //changes activity using startActivityForResult method
            Intent changeActivityIntent = new Intent(MainActivity.this, Settings.class);
            startActivityForResult(changeActivityIntent, 0);

        }
    }
}
