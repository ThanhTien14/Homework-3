package com.example.homework3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView lastGuessText, attemptsText, hintText;
    private EditText guessEditText;
    private Button confirmButton;
    private int digitCount;
    private int correctNumber;
    private int remainingAttempts = 10;
    private ArrayList<Integer> guesses = new ArrayList<>();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        lastGuessText = findViewById(R.id.lastGuessText);
        attemptsText = findViewById(R.id.attemptsText);
        hintText = findViewById(R.id.hintText);
        guessEditText = findViewById(R.id.guessEditText);
        confirmButton = findViewById(R.id.confirmButton);

        // Lấy độ khó từ Intent
        digitCount = getIntent().getIntExtra("DIGIT_COUNT", 0);
        correctNumber = generateRandomNumber(digitCount);
        updateUI();

        confirmButton.setOnClickListener(v -> checkGuess());
    }

    private int generateRandomNumber(int digits) {
        int min = (int) Math.pow(10, digits - 1); // Ví dụ: 2 chữ số -> 10
        int max = (int) Math.pow(10, digits) - 1; // Ví dụ: 2 chữ số -> 99
        return random.nextInt(max - min + 1) + min;
    }

    private void checkGuess() {
        String guessStr = guessEditText.getText().toString().trim();
        if (guessStr.isEmpty()) {
            hintText.setText("Please enter a number");
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(guessStr);
        } catch (NumberFormatException e) {
            hintText.setText("Please enter a valid number");
            return;
        }

        // Kiểm tra độ dài
        if (String.valueOf(guess).length() != digitCount) {
            hintText.setText("Please enter a " + digitCount + "-digit number");
            return;
        }

        guesses.add(guess);
        remainingAttempts--;

        if (guess == correctNumber) {
            showResult(true);
        } else {
            hintText.setText(guess < correctNumber ? "Try a bigger number" : "Try a smaller number");
            updateUI();
            if (remainingAttempts == 0) {
                showResult(false);
            }
        }
    }

    private void updateUI() {
        lastGuessText.setText("Your last guess: " + (guesses.isEmpty() ? "N/A" : guesses.get(guesses.size() - 1)));
        attemptsText.setText("Remaining attempts: " + remainingAttempts);
        guessEditText.setText("");
    }

    private void showResult(boolean isWin) {
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putIntegerArrayListExtra("GUESSES", guesses);
        intent.putExtra("CORRECT_NUMBER", correctNumber);
        intent.putExtra("IS_WIN", isWin);
        startActivity(intent);
        finish();
    }
}
