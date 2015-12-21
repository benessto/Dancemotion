package mum.dancemotion;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MotionSensor extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView accelaration;
    private float maxX,maxY,maxZ,minX,minY,minZ,avgX,avgY,avgZ;
    private int count;

    private int index;
    private float[] bufferX = new float[50];
    private float[] bufferY = new float[50];
    private float[] bufferZ = new float[50];

    private boolean startBtnState = false;

    ImageButton startBtn;

    private ArrayList<MoveIntensity> songBuffer = new ArrayList<MoveIntensity>();


    public void writeToSongBuffer(MoveIntensity moveIntensity){
        songBuffer.add(moveIntensity);
    }

    public void clearSongBuffer(){
        songBuffer.clear();
    }

    public boolean writeToBuffer(float[] buffer, float value) {
        if (index < buffer.length) {
            buffer[index] = value;
            if (index + 1 >= buffer.length) {
                index = 0;
                return true;
            } else {
                index++;
                return false;
            }
        }
        return false;
    }

    public float calcAverage(float[] buffer){
        float avg = 0;
        int total = 0;

        for (int i =0; i < buffer.length; i++) {
            if(buffer[i] != 0) {
                total++;
            }
            avg += buffer[i];
        }

        return avg/total;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);

        startBtn = (ImageButton)findViewById(R.id.startButton_Inactive);
        startBtn.setOnClickListener(startButtonHandler);


        accelaration=(TextView) findViewById(R.id.accelaration);

    }

    View.OnClickListener startButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            if (!startBtnState) { // Activate
                startBtn.setImageResource(R.drawable.startbutton_active);
                startBtnState = true;
            } else { // Deactivate
                startBtn.setImageResource(R.drawable.startbutton_inactive);
                startBtnState = false;
                reset();
            }
        }
    };

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void reset(){
        clearSongBuffer();
        bufferX = new float[300];
        bufferY = new float[300];
        bufferZ = new float[300];
        count = 0;
        index = 0;
    }

    public float calcSongAverage() {
        float x = 0;
        for (MoveIntensity moveIntensity : songBuffer) {
            x += moveIntensity.getValue();
        }

        return x/songBuffer.size();
    }

    public void onSensorChanged(SensorEvent event) {
        if (startBtnState) {


            count += 1;

            writeToBuffer(bufferX, Math.abs(event.values[0]));
            writeToBuffer(bufferY, Math.abs(event.values[1]));
            if (writeToBuffer(bufferZ, Math.abs(event.values[2]))) {
                float avgX = calcAverage(bufferX);
                float avgY = calcAverage(bufferY);
                float avgZ = calcAverage(bufferZ);
                float avg = (avgX+avgY+avgZ)/3;
                writeToSongBuffer(new MoveIntensity(avg));
            }

            if (Math.abs(event.values[0]) > maxX)
                maxX = Math.abs(event.values[0]);
            if (Math.abs(event.values[1]) > maxY)
                maxY = Math.abs(event.values[1]);
            if (Math.abs(event.values[2]) > maxZ)
                maxZ = Math.abs(event.values[2]);



            accelaration.setText("X: " + event.values[0] +
                    "\nY: " + Math.abs(event.values[1]) +
                    "\nZ: " + Math.abs(event.values[2]) +
                    "\nAverage: "+ calcSongAverage() +
                    /*"\nMaxX: " + maxX +
                    "\nMaxY: " + maxY +
                    "\nMaxZ: " + maxZ +
                    "\nAvgX: " + avgX +
                    "\nAvgY: " + avgY +
                    "\nAvgZ: " + avgZ + */
                    "\nStart: " + SystemClock.currentThreadTimeMillis() +
                    "\nCount: " + count);

        }
    }
}
