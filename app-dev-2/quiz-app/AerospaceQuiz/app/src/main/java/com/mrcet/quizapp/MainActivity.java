package com.mrcet.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity — Quiz App Welcome Screen
 *
 * Entry point for the Aerospace & Science Quiz. Displays the app
 * title, brief instructions, and a Start Quiz button that navigates
 * to QuizActivity.
 *
 * Course:      Application Development 2
 * Institution: MRCET, Department of Aeronautical Engineering
 * Guide:       Mrs. L. Sushma, Associate Professor
 * Year:        2022–2023
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.btn_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}
