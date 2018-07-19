package com.sensor_data_collect;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sensor_data_collect.FilesUtil;
import com.sensor_data_collect.PermisionUtils;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView x_acc;
    private TextView y_acc;
    private TextView z_acc;
    String filePath = "/sdcard/";
    String fileName = "sensor_data.txt";
//    private final

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermisionUtils.verifyStoragePermissions(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = (Sensor) mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        x_acc = (TextView)findViewById(R.id.x_acc_text);
        y_acc = (TextView)findViewById(R.id.y_acc_text);
        z_acc = (TextView)findViewById(R.id.z_acc_text);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(sensorEventListener, mSensor, 1000000);
//                registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @SuppressLint({"SetTextI18n", "ShowToast"})
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float a_x = sensorEvent.values[0];   // 获取x轴的加速度
            float a_y = sensorEvent.values[1];   // 获取y轴的加速度
            float a_z = sensorEvent.values[2];   // 获取z轴的加速度
            x_acc.setText("获取x轴的加速度:" + a_x);
            y_acc.setText("获取y轴的加速度:" + a_y);
            z_acc.setText("获取z轴的加速度:" + a_z);
            String write2file = Math.sqrt(a_x*a_x + a_y*a_y + a_z*a_z ) + "\n";
//            String write2file = a_x + "\t" + a_y + "\t" + a_z + "\t" + "\n";
            FilesUtil writer = new FilesUtil();
            writer.initData(write2file);
            Toast.makeText(MainActivity.this, write2file, Toast.LENGTH_SHORT);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

}
