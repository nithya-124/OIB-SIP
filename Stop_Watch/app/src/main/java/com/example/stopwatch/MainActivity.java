package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView timerTextView;
    private Button startButton;
    private Button stopButton;
    private Button holdButton;
    private Button resumeButton;
    private Button resetButton;
    private LinearLayout startStopHoldLayout;
    private LinearLayout resumeResetLayout;

    private boolean isTimerRunning = false;
    private boolean isPaused = false;
    private long startTime = 0L;
    private long elapsedTime = 0L;

    private Handler handler = new Handler();
    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = SystemClock.elapsedRealtime() - startTime;
            int hours = (int) (elapsedTime / 3600000);
            int minutes = (int) ((elapsedTime - hours * 3600000) / 60000);
            int seconds = (int) ((elapsedTime - hours * 3600000 - minutes * 60000) / 1000);

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timerTextView.setText(timeString);

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        holdButton = findViewById(R.id.holdButton);
        resumeButton = findViewById(R.id.resumeButton);
        resetButton = findViewById(R.id.resetButton);
        startStopHoldLayout = findViewById(R.id.startStopHoldLayout);
        resumeResetLayout = findViewById(R.id.resumeResetLayout);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    handler.removeCallbacks(updateTimer);
                    isTimerRunning = false;
                    isPaused = true;
                    startStopHoldLayout.setVisibility(View.GONE);
                    resumeResetLayout.setVisibility(View.VISIBLE);
                } else {
                    // When the timer is not running, show the "Resume" and "Reset" buttons.
                    isPaused = true;
                    startStopHoldLayout.setVisibility(View.GONE);
                    resumeResetLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    if (startTime == 0L) {
                        startTime = SystemClock.elapsedRealtime();
                    } else {
                        startTime = SystemClock.elapsedRealtime() - elapsedTime;
                    }
                    handler.post(updateTimer);
                    isTimerRunning = true;
                    isPaused = false;
                    resumeResetLayout.setVisibility(View.GONE);
                    startStopHoldLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    handler.removeCallbacks(updateTimer);
                    isTimerRunning = false;
                    isPaused = true;
                    startStopHoldLayout.setVisibility(View.VISIBLE);
                    resumeResetLayout.setVisibility(View.GONE);
                }
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) {
                    startTime = SystemClock.elapsedRealtime() - elapsedTime;
                    handler.post(updateTimer);
                    isTimerRunning = true;
                    isPaused = false;
                    resumeResetLayout.setVisibility(View.GONE);
                    startStopHoldLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                isTimerRunning = false;
                isPaused = false;
                elapsedTime = 0L;
                timerTextView.setText("00:00:00");
                resumeResetLayout.setVisibility(View.GONE);
                startStopHoldLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
