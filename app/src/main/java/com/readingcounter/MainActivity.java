package com.readingcounter;

import android.app.Notification;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView average,pageTime,breakTime,improvement;
    Button start,np,pause;
    Watch w;
    int aver = 0;
    boolean timerStarted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button)findViewById(R.id.start);
        average = (TextView)findViewById(R.id.AverageTime);
        pageTime = (TextView)findViewById(R.id.pageTime);
        np = (Button)findViewById(R.id.np);
        pause = (Button)findViewById(R.id.break1);
        breakTime = (TextView)findViewById(R.id.breakTime);
        improvement = (TextView)findViewById(R.id.Improvement);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(timerStarted == false){
                        timerStarted = true;
                        w = new Watch();
                        w.StartCount(pageTime);
                        start.setText("Stop reading");
                        start.setBackgroundColor(Color.RED);
                    }
                    else{
                        int minutes = w.minutes;
                        int seconds = w.seconds;
                        w.StopTimer();
                        w.Reset();
                        pageTime.setText(minutes+":"+seconds);
                        timerStarted = false;
                        start.setText("Start reading");
                        start.setBackgroundColor(Color.GREEN);
                    }
                }
        });
        np.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aver == 0){
                    aver = w.minutes * 60 + w.seconds;
                }else{
                    aver = (aver + (w.minutes * 60 + w.seconds))/2;
                }
                w.Reset();
                average.setText((int)Math.floor(aver/60) + ":" + aver%60);
            }
        });

    }
}

class Watch{
    private Timer timer;
    public int seconds = -1;
    public int minutes = 0;

    public void StartCount(final TextView ti){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if(seconds == 60){
                    seconds = 0;
                    minutes++;
                }
                ti.setText(minutes + ":" + seconds);
            }
        },0,1000);}
        public void StopTimer(){
        timer.cancel();
        timer = null;
    }
    public void Reset(){minutes = 0; seconds = 0;}
}
