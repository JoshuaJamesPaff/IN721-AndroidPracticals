package op.bit.paffjj1.threeactivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_c);

        //sets text view to activity
        String activity = "Activity C";
        TextView currentActivity = (TextView) findViewById(R.id.textViewActivity);
        currentActivity.setText(activity);

        Button nextActivity = (Button) findViewById(R.id.buttonNextActivity);
        ButtonClickHandler handler = new ButtonClickHandler();
        nextActivity.setOnClickListener(handler);
    }

    public class ButtonClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Uri checkPrice = Uri.parse("http://preev.com/btc/nzd");
            Intent btcExchange = new Intent(Intent.ACTION_VIEW, checkPrice);
            startActivity(btcExchange);

        }
    }
}
