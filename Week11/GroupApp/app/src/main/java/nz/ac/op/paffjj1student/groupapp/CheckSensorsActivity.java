package nz.ac.op.paffjj1student.groupapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheckSensorsActivity extends AppCompatActivity {

    private TextView lightView;
    private TextView tiltView;
    private SensorManager mLightSensorManager;
    private SensorManager mTiltSensorManager;
    private Sensor mLight;
    private Sensor mTilt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sensors);

        lightView = (TextView) findViewById(R.id.textViewLight);
        tiltView = (TextView) findViewById(R.id.textViewTilt);

       mLightSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mTiltSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
       mLight = mLightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
       mTilt = mTiltSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    protected void onResume()
    {
        super.onResume();
        mLightSensorManager.registerListener(new LightSensorChanged(), mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mTiltSensorManager.registerListener(new TiltSensorChanged(), mTilt, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause()
    {
        super.onPause();
        mLightSensorManager.unregisterListener(new LightSensorChanged());
        mTiltSensorManager.unregisterListener(new TiltSensorChanged());
    }



    private class LightSensorChanged implements SensorEventListener{


        @Override
        public void onSensorChanged(SensorEvent event) {
            float value = event.values[0];
            String display = value + "";
            lightView.setText(display);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private class TiltSensorChanged implements SensorEventListener{


        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            tiltView.setText("x: " + x + ", y: " + y + ", z: " + z);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }



}
