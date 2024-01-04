package com.example.mindsharpener;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindsharpener.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private EditText answerEditText;
    private Spinner levelSpinner;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView descriptionTextView = findViewById(R.id.descriptionTextView);

        String descriptionText = "This is a simple mathematic games which is believed can train your brain." +
                "Two numbers are given randomly based on your level choice together with the operator." +
                "You just need to calculates the answer, write your answer and press check button." +
                "Every correct answer will give you 1 point while wrong answer will deduct 1 point.";

        descriptionTextView.setText(descriptionText);

        // Set the app title in the application bar
        getSupportActionBar().setTitle(R.string.app_name);

        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        levelSpinner = findViewById(R.id.levelSpinner);

        // Set up the spinner with levels
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.levels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);

        // Set up the button click listeners
        Button findViewByrateQuestionButton = null;
        Button generateQuestionButton = findViewByrateQuestionButton;
        generateQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateQuestion();
            }
        });

        Button checkAnswerButton = findViewById(R.id.checkAnswerButton);
        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });
    }

    private void generateQuestion() {
        // Get the selected level from the spinner
        String selectedLevel = levelSpinner.getSelectedItem().toString();

        // Generate random numbers based on the selected level
        int randomNumber1, randomNumber2;
        Random random = new Random();

        switch (selectedLevel) {
            case "I3":
                // For I3 level, generate one-digit numbers
                randomNumber1 = random.nextInt(10);
                randomNumber2 = random.nextInt(10);
                break;

            case "I5":
                // For I5 level, generate two-digit numbers
                randomNumber1 = random.nextInt(100);
                randomNumber2 = random.nextInt(100);
                break;

            case "I7":
                // For I7 level, generate three-digit numbers
                randomNumber1 = random.nextInt(1000);
                randomNumber2 = random.nextInt(1000);
                break;

            default:
                // Default to I3 level if something goes wrong
                randomNumber1 = random.nextInt(10);
                randomNumber2 = random.nextInt(10);
                break;
        }

        // Generate a random number for the operator (0 to 3)
        int operator = random.nextInt(4);

        // Display the generated question in the TextView
        String operatorSymbol = getOperatorSymbol(operator);
        String question = randomNumber1 + " " + operatorSymbol + " " + randomNumber2 + " = ?";
        questionTextView.setText(question);

        // Clear the answer EditText
        answerEditText.getText().clear();
    }

    private void checkAnswer() {
        // Get user answer
        String userAnswerString = answerEditText.getText().toString().trim();

        if (!userAnswerString.isEmpty()) {
            // Convert user answer to integer
            int userAnswer = Integer.parseInt(userAnswerString);

            // Get operands and operator from the displayed question
            String questionText = questionTextView.getText().toString();
            String[] parts = questionText.split(" ");
            int firstNumber = Integer.parseInt(parts[0]);
            int secondNumber = Integer.parseInt(parts[2]);
            int operator = getOperatorIndex(parts[1]);

            // Calculate the correct answer
            int correctAnswer = calculateAnswer(firstNumber, secondNumber, operator);

            // Compare the answer with the user's answer
            if (userAnswer == correctAnswer) {
                // If correct, increase points by 1
                points++;
                Toast.makeText(this, "Correct! Points: " + points, Toast.LENGTH_SHORT).show();
            } else {
                // If incorrect, deduct points by 1
                points--;
                Toast.makeText(this, "Incorrect! Points: " + points, Toast.LENGTH_SHORT).show();
            }

            // Display another question
            generateQuestion();
        } else {
            Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculateAnswer(int firstNumber, int secondNumber, int operator) {
        switch (operator) {
            case 0:
                return firstNumber + secondNumber;
            case 1:
                return firstNumber - secondNumber;
            case 2:
                return firstNumber * secondNumber;
            case 3:
                // Ensure non-zero denominator for division
                int denominator = secondNumber == 0 ? 1 : secondNumber;
                return firstNumber / denominator;
            default:
                return 0;
        }
    }

    private String getOperatorSymbol(int operator) {
        switch (operator) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            default:
                return "";
        }
    }

    private int getOperatorIndex(String operatorSymbol) {
        switch (operatorSymbol) {
            case "+":
                return 0;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 3;
            default:
                return 0;
        }
    }
}
