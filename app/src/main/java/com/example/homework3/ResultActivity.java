package com.example.homework3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private TextView resultTitle, messageText, guessesText, correctNumberText;
    private Button noButton, yesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTitle = findViewById(R.id.resultTitle);
        messageText = findViewById(R.id.messageText);
        guessesText = findViewById(R.id.guessesText);
        correctNumberText = findViewById(R.id.correctNumberText);
        noButton = findViewById(R.id.noButton);
        yesButton = findViewById(R.id.yesButton);

        // Lấy dữ liệu từ Intent
        ArrayList<Integer> guesses = getIntent().getIntegerArrayListExtra("GUESSES");
        int correctNumber = getIntent().getIntExtra("CORRECT_NUMBER", 0);
        boolean isWin = getIntent().getBooleanExtra("IS_WIN", false);

        if (isWin) {
            resultTitle.setText("Number Guessing Game - Congratulations!");
            messageText.setText("You've guessed the number correctly!");
        } else {
            resultTitle.setText("Number Guessing Game - Game Over");
            messageText.setText("You've used all attempts. Game Over");
        }

        // Hiển thị danh sách các số đã đoán
        StringBuilder guessesStr = new StringBuilder("Your guesses: ");
        for (int guess : guesses) {
            guessesStr.append(guess).append(", ");
        }
        if (!guesses.isEmpty()) {
            guessesStr.setLength(guessesStr.length() - 2); // Xóa dấu phẩy cuối
        }
        guessesText.setText(guessesStr.toString());

        // Hiển thị số đúng
        correctNumberText.setText("Correct number: " + correctNumber);

        // Xử lý nút Yes
        yesButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Xử lý nút No
        noButton.setOnClickListener(v -> finish());
    }
}
