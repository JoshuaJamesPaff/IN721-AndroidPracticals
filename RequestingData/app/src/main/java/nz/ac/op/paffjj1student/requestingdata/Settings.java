package nz.ac.op.paffjj1student.requestingdata;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //textview object to get color from
        TextView redText = (TextView)findViewById(R.id.textViewRedText);

        //returns to main screen as soon as its created, passing in the color of the textview
        Intent returnIntent = new Intent();
        returnIntent.putExtra("colorOfText", redText.getCurrentTextColor());

        //set return code passing intent as parameter
        setResult(Activity.RESULT_OK, returnIntent);

        //pop off activity stack
        finish();

    }
}
