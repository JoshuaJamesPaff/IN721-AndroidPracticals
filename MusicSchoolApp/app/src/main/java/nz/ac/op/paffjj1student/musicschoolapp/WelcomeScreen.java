package nz.ac.op.paffjj1student.musicschoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        //creates button and binds handler
        Button nextActivity = (Button) findViewById(R.id.buttonNextActivity);
        ButtonClickHandler click = new ButtonClickHandler();
        nextActivity.setOnClickListener(click);
    }

    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Intent changeActivity = new Intent(WelcomeScreen.this, MainActivity.class);
            startActivity(changeActivity);
        }
    }
}
