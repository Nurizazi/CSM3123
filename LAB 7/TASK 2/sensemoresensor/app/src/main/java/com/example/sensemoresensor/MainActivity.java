package com.example.sensemoresensor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensemoresensor.R;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager senseMan;
    Sensor lightSensor;
    Sensor proximitySensor;
    Sensor temperatureSensor;
    TextView textProximity;
    TextView textPressure;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textProximity = (TextView) findViewById(R.id.proximity);
        textPressure = (TextView) findViewById(R.id.pressure);

        senseMan = (SensorManager) getSystemService(SENSOR_SERVICE);

        lightSensor = senseMan.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = senseMan.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        temperatureSensor = senseMan.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if (proximitySensor != null) {
            Toast.makeText(this, "Proximity Sensor Found", Toast.LENGTH_SHORT).show();
            senseMan.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Proximity Sensor Not Found", Toast.LENGTH_SHORT).show();
        }

        if (temperatureSensor != null) {
            Toast.makeText(this, "Temperature Sensor Found", Toast.LENGTH_SHORT).show();
            senseMan.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Temperature Sensor Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            textProximity.setText("Proximity: " + Float.toString(sensorEvent.values[0]));
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
            textPressure.setText("Pressure: " + Float.toString(sensorEvent.values[0]));
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        senseMan.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senseMan.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        senseMan.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}