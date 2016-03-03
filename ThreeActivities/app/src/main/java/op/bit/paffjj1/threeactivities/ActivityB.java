package op.bit.paffjj1.threeactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_b);

        //sets text view to activity
        String activity = "Activity B";
        TextView currentActivity = (TextView) findViewById(R.id.textViewActivity);
        currentActivity.setText(activity);

        Button nextActivity = (Button) findViewById(R.id.buttonNextActivity);
        ButtonClickHandler handler = new ButtonClickHandler();
        nextActivity.setOnClickListener(handler);
    }

    public class ButtonClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent changeActivity = new Intent(ActivityB.this, ActivityC.class);
            startActivity(changeActivity);

        }
    }
}
