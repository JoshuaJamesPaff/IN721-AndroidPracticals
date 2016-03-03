package op.bit.paffjj1.threeactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        //sets text view to activity
        String activity = "Activity A";
        TextView currentActivity = (TextView) findViewById(R.id.textViewActivity);
        currentActivity.setText(activity);

        //creates button and binds handler
        Button nextActivity = (Button) findViewById(R.id.buttonNextActivity);
        ButtonClickHandler handler = new ButtonClickHandler();
        nextActivity.setOnClickListener(handler);
    }

    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Intent changeActivity = new Intent(ActivityA.this, ActivityB.class);
            startActivity(changeActivity);
        }
    }
}
