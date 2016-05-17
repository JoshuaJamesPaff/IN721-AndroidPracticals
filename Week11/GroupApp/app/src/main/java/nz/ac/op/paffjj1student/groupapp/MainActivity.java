package nz.ac.op.paffjj1student.groupapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listSensors = (Button) findViewById(R.id.buttonSensors);
        Button changeIntent = (Button) findViewById(R.id.buttonIntent);

        changeIntent.setOnClickListener(new IntentClickHandler());

        listSensors.setOnClickListener(new ClickHandler());
        tv = (TextView) findViewById(R.id.textViewSensors);

    }


    private class IntentClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent sensorIntent = new Intent(MainActivity.this, CheckSensorsActivity.class);

            startActivity(sensorIntent);

        }


    }


    private class ClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            // Get the SensorManager
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

            // List of Sensors Available
            List<Sensor> msensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

            String sensors = "";
            for (Sensor s : msensorList) {
                sensors += (s.getName() + "\n ");
            }

            tv.setText(sensors);
        }


    }
}
