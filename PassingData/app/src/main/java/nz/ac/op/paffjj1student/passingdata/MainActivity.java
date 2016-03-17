package nz.ac.op.paffjj1student.passingdata;

import android.content.DialogInterface;
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
        Button gotoSettingsButton = (Button) findViewById(R.id.buttonBackToMain);
        //listener object
        ButtonClickHandler handler = new ButtonClickHandler();
        //bind handler to button
        gotoSettingsButton.setOnClickListener(handler);

        //receives username from intent and setting to textview
        Intent receiveUserName = getIntent();
        String username = receiveUserName.getStringExtra("username");
        TextView showUserName = (TextView) findViewById(R.id.tvUserNameDisplay);

    }

    //click handler inner class
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //changes activity
            Intent changeActivityIntent = new Intent(MainActivity.this, CreateUsername.class);
            startActivity(changeActivityIntent);

        }
    }
}
