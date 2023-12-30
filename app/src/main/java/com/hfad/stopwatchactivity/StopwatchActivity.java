package com.hfad.stopwatchactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.hfad.stopwatchactivity.databinding.ActivityStopwatchBinding;

public class StopwatchActivity extends AppCompatActivity {

    private ActivityStopwatchBinding binding;

    private int seconds = 0;

    private boolean running;

    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStopwatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");

        }
        runTimer();

        binding.startButton.setOnClickListener(view -> running = true);
        binding.stopButton.setOnClickListener(view -> running = false);
        binding.resetButton.setOnClickListener(view -> {
            running = true;
            seconds = 0;
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    private void runTimer() {
        final Handler handler = new Handler();
        final TextView timeView = binding.timerTextView;
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;

                int minutes = ((seconds % 3600) / 60);

                int secs = seconds % 60;

                @SuppressLint("DefaultLocale") String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


}