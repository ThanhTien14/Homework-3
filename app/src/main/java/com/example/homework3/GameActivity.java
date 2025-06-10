package com.example.homework3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView lastGuessText, attemptsText, hintText, resultTitle, messageText, guessesText, correctNumberText, playAgainText;
    private EditText guessEditText;
    private Button confirmButton, noButton, yesButton;
    private LinearLayout resultBox;
    private int digitCount;
    private int correctNumber;
    private int remainingAttempts = 10;
    private ArrayList<Integer> guesses = new ArrayList<>();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Khởi tạo các thành phần giao diện
        lastGuessText = findViewById(R.id.lastGuessText);
        attemptsText = findViewById(R.id.attemptsText);
        hintText = findViewById(R.id.hintText);
        guessEditText = findViewById(R.id.guessEditText);
        confirmButton = findViewById(R.id.confirmButton);
        resultBox = findViewById(R.id.resultBox);
        resultTitle = findViewById(R.id.resultTitle);
        messageText = findViewById(R.id.messageText);
        guessesText = findViewById(R.id.guessesText);
        correctNumberText = findViewById(R.id.correctNumberText);
        playAgainText = findViewById(R.id.playAgainText);
        noButton = findViewById(R.id.noButton);
        yesButton = findViewById(R.id.yesButton);

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
        resultBox.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.GONE); // Ẩn nút CONFIRM
        guessEditText.setVisibility(View.GONE); // Ẩn trường nhập

        if (isWin) {
            resultTitle.setText("Number Guessing Game - Congratulations!");
            messageText.setText("You've guessed the number correctly!");
        } else {
            resultTitle.setText("Number Guessing Game - Game Over");
            messageText.setText("You've used all attempts. Game Over");
        }

        StringBuilder guessesStr = new StringBuilder("Your guesses: ");
        for (int guess : guesses) {
            guessesStr.append(guess).append(", ");
        }
        if (!guesses.isEmpty()) {
            guessesStr.setLength(guessesStr.length() - 2); // Xóa dấu phẩy cuối
        }
        guessesText.setText(guessesStr.toString());

        correctNumberText.setText("Correct number: " + correctNumber);

        yesButton.setOnClickListener(v -> {
            resetGame();
            resultBox.setVisibility(View.GONE);
            confirmButton.setVisibility(View.VISIBLE);
            guessEditText.setVisibility(View.VISIBLE);
        });

        noButton.setOnClickListener(v -> finish());
    }
    private void resetGame() {
        remainingAttempts = 10;
        guesses.clear();
        correctNumber = generateRandomNumber(digitCount);
        updateUI();
        hintText.setText("");
    }
}
