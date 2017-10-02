package com.readingcounter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView average, pageTime, breakTime, improvement;
    Button start, np, pause;
    Watch w;
    int aver, minutesReading, minutesBreaking, secondsReading, secondsBreaking = 0;
    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        average = (TextView) findViewById(R.id.AverageTime);
        pageTime = (TextView) findViewById(R.id.pageTime);
        np = (Button) findViewById(R.id.np);
        pause = (Button) findViewById(R.id.break1);
        breakTime = (TextView) findViewById(R.id.breakTime);
        improvement = (TextView) findViewById(R.id.Improvement);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerStarted == false) {
                    timerStarted = true;
                    w = new Watch();
                    w.StartCount(pageTime, 0, 0);
                    start.setText("Stop reading");
                    start.setBackgroundColor(Color.RED);
                } else {
                    minutesReading = w.minutes;
                    secondsReading = w.seconds;
                    w.StopTimer();
                    w = new Watch();
                    pageTime.setText(minutesReading + ":" + secondsReading);
                    timerStarted = false;
                    start.setText("Start reading");
                    start.setBackgroundColor(Color.GREEN);
                }
            }
        });
        np.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aver == 0) {
                    aver = ((minutesReading+w.minutes) * 60 + (secondsReading+w.seconds));
                } else {
                    aver = (aver + ((minutesReading+w.minutes) * 60 + (secondsReading+w.seconds))) / 2;
                }
                improvement.setText(String.valueOf((float) aver / ((minutesReading+w.minutes) * 60 + (secondsReading+w.seconds))));
                if ((aver / ((minutesReading+w.minutes) * 60 + (secondsReading+w.seconds))) >= 1) {
                    improvement.setTextColor(Color.GREEN);
                } else {
                    improvement.setTextColor(Color.RED);
                }
                w.StopTimer();
                w = new Watch();
                w.StartCount(pageTime,0,0);
                average.setText(String.valueOf((int) Math.floor(aver / 60) + ":" + aver % 60));
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pause.getText().equals("Break")) {
                    minutesReading = w.minutes;
                    secondsReading = w.seconds;
                    w.StopTimer();
                    w = new Watch();
                    pause.setText("Continue Reading");
                    w.StartCount(breakTime, minutesBreaking, secondsBreaking);
                } else {
                    minutesBreaking = w.minutes;
                    secondsBreaking = w.seconds;
                    w.StopTimer();
                    pause.setText("Break");
                    w = new Watch();
                    w.StartCount(pageTime, minutesReading, secondsReading);
                }

            }
        });

    }
}

class Watch {
    public int seconds = -1;
    public int minutes = 0;
    private Timer timer;

    public void StartCount(final TextView ti, final int addidtionMinutes, final int additionSeconds) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                ti.setText((addidtionMinutes + minutes) + ":" + (additionSeconds + seconds));
            }
        }, 0, 1000);
    }

    public void StopTimer() {
        timer.cancel();
        timer = null;
    }
}
