package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

// Grafische Ausgabe (drawControl, Smoother, IInstruments) übernommen von dem
// Ordner "Codebeispiele" wasserWaage.zip aus dem Moodle Kurs.
public class MainActivity extends AppCompatActivity implements SensorEventListener, IInstruments {

    private TextView textX;
    private TextView textY;
    private TextView textZ;
    private ImageView imageView;

    private SensorManager sensorManager;
    private Sensor accelerationSensor;

    private Smoother smoothX = new Smoother();
    private Smoother smoothY = new Smoother();

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textX = findViewById(R.id.text_x);
        textY = findViewById(R.id.text_y);
        textZ = findViewById(R.id.text_z);
        imageView = findViewById(R.id.imageView);


        bitmap = Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerationSensor != null) {
            sensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accelerationSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];

        double rot_x = Math.atan2(-z, y) * 180. / Math.PI;
        double rot_y = Math.atan2(-z, x) * 180. / Math.PI;
        double rot_z = Math.atan2(Math.sqrt(x * x + y * y), z) * 180. / Math.PI;

        double smoothed_rot_x = smoothX.smooth(rot_x);
        double smoothed_rot_y = smoothY.smooth(rot_y);

        if (textX != null) textX.setText(String.format("X-Achse: %1.1f°", smoothed_rot_x));
        if (textY != null) textY.setText(String.format("Y-Achse: %1.1f°", smoothed_rot_y));
        if (textZ != null) textZ.setText(String.format("Z-Achse: %1.1f°", rot_z));

        drawControl(smoothed_rot_x, smoothed_rot_y);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    void drawControl(double rot_x, double rot_y) {
        if (imageView == null) return;

        canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        int size = 250;

        canvas.drawLine(0, 0, size, 0, paint);
        canvas.drawLine(size, 0, size, size, paint);
        canvas.drawLine(size, size, 0, size, paint);
        canvas.drawLine(0, size, 0, 0, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(117, 249, 77));
        canvas.drawCircle(125, 125, 120, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(180, 249, 180));

        int blaseX = 125 + (int) (90. + rot_y);
        int blaseY = 125 + (int) (90. + rot_x);

        canvas.drawCircle(blaseX, blaseY, 16, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.rgb(180, 186, 50));
        canvas.drawCircle(blaseX, blaseY, 16, paint);

        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void updateHeight(double height) { }

    @Override
    public void updateAzimuth(double azimuth) { }

    @Override
    public void updateTilt(double tilt) { }
}