package nz.ac.op.paffjj1student.accelerometerapp;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager mLightSensorManager;
    private SensorManager mTiltSensorManager;
    private Sensor mLight;
    private Sensor mTilt;

    private ImageView spiderImage;
    private int imageX;
    private int imageY;
    private double lightMultiplyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets initial parameters
        spiderImage = (ImageView)findViewById(R.id.imageViewSpider);
        imageX = 0;
        imageY = 0;
        lightMultiplyer = 1;

        mLightSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mTiltSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLight = mLightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mTilt = mTiltSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    //changes imageview based on x,y input
    private void changeImageLocation(int x, int y){

        imageX -= (x*lightMultiplyer);
        imageY += (y*lightMultiplyer);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(imageX, imageY, 0,0);

        spiderImage.setLayoutParams(params);
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

    //changes speed spider changes based on light input
    private class LightSensorChanged implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float value = event.values[0];

            lightMultiplyer = value * 0.1;

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
    // changes spider location based on x,y change
    private class TiltSensorChanged implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            changeImageLocation((int)x, (int)y);

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
