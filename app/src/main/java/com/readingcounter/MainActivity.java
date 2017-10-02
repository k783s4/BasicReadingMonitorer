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

        average.setText("0");
        pageTime.setText("0");
        breakTime.setText("0");
        improvement.setText("0");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!timerStarted) {
                    timerStarted = true;
                    Reset();
                   StartCount(pageTime, 0, 0);
                    start.setText("Stop reading");
                    start.setBackgroundColor(Color.RED);
                } else {
                    minutesReading = minutes;
                    secondsReading = seconds;
                   StopTimer();
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
                    aver = ((minutesReading +minutes) * 60 + (secondsReading +seconds));
                } else {
                    aver = (aver + ((minutesReading +minutes) * 60 + (secondsReading +seconds))) / 2;
                }
                improvement.setText(String.valueOf((float) aver / ((minutesReading +minutes) * 60 + (secondsReading +seconds))));
                if ((aver / ((minutesReading +minutes) * 60 + (secondsReading +seconds))) >= 1) {
                    improvement.setTextColor(Color.GREEN);
                    improvement.setText("+" + improvement.getText());
                } else {
                    improvement.setTextColor(Color.RED);
                    improvement.setText("-" + improvement.getText());
                }
               StopTimer();
                Reset();
               StartCount(pageTime, 0, 0);
                average.setText(String.valueOf((int) Math.floor(aver / 60) + ":" + aver % 60));
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pause.getText().equals("Break")) {
                    minutesReading =minutes;
                    secondsReading =seconds;
                   StopTimer();
                    pause.setText("Continue Reading");
                    Reset();
                   StartCount(breakTime, minutesBreaking, secondsBreaking);
                } else {
                    minutesBreaking =minutes;
                    secondsBreaking =seconds;
                   StopTimer();
                    pause.setText("Break");
                    Reset();
                   StartCount(pageTime, minutesReading, secondsReading);
                }

            }
        });

    }
    public int seconds = -1;
    public int minutes = 0;
    private Timer timer;

    public void StartCount(final TextView ti, final int additionMinutes, final int additionSeconds) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ti.setText((additionMinutes+ minutes) + ":" + (additionSeconds + seconds));
                    }
                });
            }
        }, 0, 1000);
    }
    public void Reset(){minutes = 0; seconds = 0;}

    public void StopTimer() {
        timer.cancel();
        timer = null;
    }
}


