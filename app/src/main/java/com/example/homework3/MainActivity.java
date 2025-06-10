package com.example.homework3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private RadioGroup difficultyGroup;
    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        difficultyGroup = findViewById(R.id.difficultyGroup);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> {
            int selectedId = difficultyGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                int digitCount = getDigitCount(selectedId);
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("DIGIT_COUNT", digitCount);
                startActivity(intent);
            }
        });
    }
    private int getDigitCount(int selectedId) {
        if (selectedId == R.id.twoDigit) {
            return 2;
        } else if (selectedId == R.id.threeDigit) {
            return 3;
        } else if (selectedId == R.id.fourDigit) {
            return 4;
        } else {
            return 0;
        }
    }
}