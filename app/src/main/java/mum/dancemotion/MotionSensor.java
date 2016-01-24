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

import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;

public class MotionSensor extends AppCompatActivity implements SensorEventListener {
    private final static int BUFFER_SIZE = 125;

    private long currentTime;

    private SensorManager mSensorManager;
    private Sensor mSensor; // Movementsensor
    private TextView accelaration; // Textoutput
    private float maxX,maxY,maxZ,minX,minY,minZ,avgX,avgY,avgZ;
    private int count; // Number of times the sensor method is activated

    private int index; // index of buffers

    private int systemTime; // Time since System started

    private String songName; //Name of the Song

    private DatenbankOperations DB;

    private Context ctx = this;

    private long delayForTimer = 30000;
    private int[] ratingArray = new int[5];
    private int ratingArrayPointer = 0;
    private Timer timer = new Timer();
    private boolean timerStarted = false;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (ratingArrayPointer < 5) {
                ratingArray[ratingArrayPointer] = getRating(calcSongAverage());
                resetRatingValue();
            } else {
                timer.cancel();
                timer.purge();
            }
            ratingArrayPointer++;
        }
    };



    /**
     * Buffers for average intensities
     */
    private float[] bufferX = new float[BUFFER_SIZE];
    private float[] bufferY = new float[BUFFER_SIZE];
    private float[] bufferZ = new float[BUFFER_SIZE];

    private boolean startBtnState = false;

    ImageButton startBtn;

    private ArrayList<MoveIntensity> songBuffer = new ArrayList<MoveIntensity>();

    /**
     * Add an intensity to the song buffer arraylist
     * @param moveIntensity
     */
    public void writeToSongBuffer(MoveIntensity moveIntensity){
        songBuffer.add(moveIntensity);
    }

    /**
     * Clears the song buffer list
     */
    public void clearSongBuffer(){
        songBuffer.clear();
    }

    /**
     * Writes a value into the buffer
     * @param buffer
     * @param value
     * @return
     */
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

    /**
     * Calculates the average value of a buffer
     * @param buffer
     * @return
     */
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

    /**
     * OnCreate method of activity
     * @param savedInstanceState
     */
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



        DB = new DatenbankOperations(ctx);

        //System.out.println(DB.getInformationFromSong(DB));



        songName = "no song yet";

    }

    /**
     * Starts the timer to save ratings into an array after a specified delay
     */
    protected void startTimer() {
        if (!timerStarted)
            timer.scheduleAtFixedRate(timerTask, delayForTimer, delayForTimer);
        timerStarted = true;
    }

    /**
     * OnClick for start button
     */
    View.OnClickListener startButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            if (!startBtnState) { // Activate
                currentTime = System.currentTimeMillis();
                startBtn.setImageResource(R.drawable.startbutton_active);
                startBtnState = true;

            } else { // Deactivate
                startBtn.setImageResource(R.drawable.startbutton_inactive);
                startBtnState = false;
                reset();
            }
        }
    };

    /**
     * Abstract method by SensorEventListener
     * @param s
     * @param i
     */
    public void onAccuracyChanged(Sensor s, int i) {

    }

    /**
     * this methods maps the avg values to a rating-scale from 0-10
     * @param avg
     * @return
     */
    public int getRating(float avg){

        if(avg>17){
            return 10;
        }
        switch((int)avg){


            case 0: return 0;

            case 1: return 1;

            case 2: return 2;

            case 3: return 3;

            case 4: return 3;

            case 5: return 3;

            case 6: return 4;

            case 7: return 5;

            case 8: return 5;

            case 9: return 6;

            case 10: return 6;

            case 11: return 7;

            case 12: return 7;

            case 13: return 8;

            case 14: return 8;

            case 15: return 9;

            case 16: return 9;


        }
        return 0;
    }



    /**
     *Clear the buffers for a new song.
     */
    public void resetRatingValue(){
        clearSongBuffer();
        bufferX = new float[BUFFER_SIZE];
        bufferY = new float[BUFFER_SIZE];
        bufferZ = new float[BUFFER_SIZE];
        count = 0;
        index = 0;
    }

    /**
     * Reset all variables to default state and clear the buffers.
     */
    public void reset(){
        clearSongBuffer();
        bufferX = new float[BUFFER_SIZE];
        bufferY = new float[BUFFER_SIZE];
        bufferZ = new float[BUFFER_SIZE];
        count = 0;
        index = 0;
        currentTime = 0;
    }



    /**
     * Calculates the average intensity of a song from the songBuffer
     * @return Average intensity the current song
     */
    public float calcSongAverage() {
        float x = 0;
        for (MoveIntensity moveIntensity : songBuffer) {
            x += moveIntensity.getValue();
        }

        return x/songBuffer.size();
    }

    /**
     * Main method for sensoring purposes
     * @param event
     */
    public void onSensorChanged(SensorEvent event) {
        if (!timerStarted) {
            startTimer();
        }

        if (startBtnState) {
            int test = 0;

            count += 1;

            writeToBuffer(bufferX, Math.abs(event.values[0]));
            writeToBuffer(bufferY, Math.abs(event.values[1]));
            if (writeToBuffer(bufferZ, Math.abs(event.values[2]))) {
                test = 1;
                float avgX = calcAverage(bufferX);
                float avgY = calcAverage(bufferY);
                float avgZ = calcAverage(bufferZ);
                float avg = (avgX+avgY+avgZ);
                /**if (avgX < avgY) {
                    if (avgY < avgZ) {
                        avg = avgZ;
                    } else {
                        avg = avgY;
                    }
                } else if (avgX < avgZ){
                    avg = avgZ;
                } else {
                    avg = avgX;
                }**/
                writeToSongBuffer(new MoveIntensity(avg));
            } else {
                test = 0;
            }

            if (Math.abs(event.values[0]) > maxX)
                maxX = Math.abs(event.values[0]);
            if (Math.abs(event.values[1]) > maxY)
                maxY = Math.abs(event.values[1]);
            if (Math.abs(event.values[2]) > maxZ)
                maxZ = Math.abs(event.values[2]);

            /*systemTime = (int)(System.currentTimeMillis() - currentTime) / 1000;

            if(systemTime>0 && systemTime<7)
                songName="MichaelJackson 1";

            else if(systemTime>=7){
                songName="David";
                //DB.putInformationsIntoSessionSong(DB, Integer.toString(getRating(calcSongAverage())));
            if(systemTime==7)
                  //DB.putInformationsIntoSessionSong(DB, getRating(calcSongAverage()));
                resetRatingValue();}*/


            /*accelaration.setText("X: " + event.values[0] +
                            "\nY: " + Math.abs(event.values[1]) +
                            "\nZ: " + Math.abs(event.values[2]) +
                            "\nAverage: " + calcSongAverage() +
                            "\nRating: " + getRating(calcSongAverage()) +
                            "\nStart: " + (System.currentTimeMillis() - currentTime) / 1000 + " seconds" +
                            "\nCount: " + count +
                            "\nsongBufferSize(): " + songBuffer.size()
            );*/

            String ratings = "1. " + ratingArray[0] + "\n";
            for (int i = 1; i < 5; i++) {
                ratings = ratings + (i+1)+". " + ratingArray[i] + "\n";
            }

            accelaration.setText(ratings +
                    "\nRating: " + getRating(calcSongAverage()));




            /**System.out.println("\nStart: " + (System.currentTimeMillis() - currentTime) / 1000 + " seconds" +
                    "\nAverage: " + calcSongAverage());**/




        }
    }
}
