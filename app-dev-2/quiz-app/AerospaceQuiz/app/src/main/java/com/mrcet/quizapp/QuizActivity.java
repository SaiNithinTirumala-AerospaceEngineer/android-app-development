package com.mrcet.quizapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * QuizActivity — Core Quiz Engine
 *
 * Features:
 *   - 15 aerospace and science questions from QuestionBank
 *   - 20-second countdown timer per question
 *   - Four answer buttons (A/B/C/D) with colour feedback
 *   - Progress bar showing question position
 *   - Score tracking with correct / incorrect / unanswered counts
 *   - Results screen: score percentage, grade, breakdown
 *   - Restart capability from results screen
 *
 * Timer behaviour:
 *   - 20 seconds per question
 *   - Timer turns red when ≤ 5 seconds remain
 *   - Auto-advances to next question on timeout (marked unanswered)
 *
 * Colour feedback:
 *   - Green  — correct answer selected
 *   - Red    — wrong answer selected (correct answer highlighted green)
 *   - Grey   — timeout, no answer selected
 */
public class QuizActivity extends AppCompatActivity {

    // UI components
    private TextView    tvQuestion;
    private TextView    tvProgress;
    private TextView    tvTimer;
    private TextView    tvScore;
    private ProgressBar progressBar;
    private Button[]    optionButtons = new Button[4];
    private Button      btnNext;

    // Quiz state
    private Question[]  questions;
    private int         currentIndex  = 0;
    private int         score         = 0;
    private int         correct       = 0;
    private int         incorrect     = 0;
    private int         unanswered    = 0;
    private boolean     answered      = false;

    // Timer
    private CountDownTimer countDownTimer;
    private static final long TIMER_MS   = 20000L;
    private static final long INTERVAL   = 1000L;

    // Colours
    private static final int CLR_DEFAULT  = Color.parseColor("#2C2C2E");
    private static final int CLR_CORRECT  = Color.parseColor("#34C759");
    private static final int CLR_WRONG    = Color.parseColor("#FF3B30");
    private static final int CLR_TIMEOUT  = Color.parseColor("#8E8E93");
    private static final int CLR_TIMER_OK = Color.parseColor("#1C1C1E");
    private static final int CLR_TIMER_LO = Color.parseColor("#FF3B30");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Bind views
        tvQuestion  = findViewById(R.id.tv_question);
        tvProgress  = findViewById(R.id.tv_progress);
        tvTimer     = findViewById(R.id.tv_timer);
        tvScore     = findViewById(R.id.tv_score);
        progressBar = findViewById(R.id.progress_bar);
        btnNext     = findViewById(R.id.btn_next);

        int[] optIds = {R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d};
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = findViewById(optIds[i]);
            final int idx = i;
            optionButtons[i].setOnClickListener(v -> handleAnswer(idx));
        }

        btnNext.setOnClickListener(v -> nextQuestion());

        // Load and shuffle questions
        List<Question> list = Arrays.asList(QuestionBank.getQuestions());
        Collections.shuffle(list);
        questions = list.toArray(new Question[0]);

        progressBar.setMax(questions.length);
        showQuestion();
    }

    // ── Question display ──────────────────────────────────────────────────────

    private void showQuestion() {
        if (currentIndex >= questions.length) {
            showResults();
            return;
        }

        answered = false;
        Question q = questions[currentIndex];

        tvQuestion.setText(q.getText());
        tvProgress.setText((currentIndex + 1) + " / " + questions.length);
        tvScore.setText("Score: " + score);
        progressBar.setProgress(currentIndex + 1);

        String[] labels = {"A", "B", "C", "D"};
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(labels[i] + ".  " + q.getOptions()[i]);
            optionButtons[i].setBackgroundColor(CLR_DEFAULT);
            optionButtons[i].setEnabled(true);
            optionButtons[i].setTextColor(Color.WHITE);
        }

        btnNext.setVisibility(View.GONE);
        startTimer();
    }

    // ── Timer ─────────────────────────────────────────────────────────────────

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(TIMER_MS, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secs = millisUntilFinished / 1000;
                tvTimer.setText(secs + "s");
                tvTimer.setTextColor(secs <= 5 ? CLR_TIMER_LO : CLR_TIMER_OK);
            }

            @Override
            public void onFinish() {
                if (!answered) {
                    unanswered++;
                    tvTimer.setText("0s");
                    tvTimer.setTextColor(CLR_TIMER_LO);
                    // Highlight correct answer in grey (timeout)
                    for (Button b : optionButtons) {
                        b.setEnabled(false);
                        b.setBackgroundColor(CLR_TIMEOUT);
                    }
                    int correctIdx = questions[currentIndex].getCorrectIndex();
                    optionButtons[correctIdx].setBackgroundColor(CLR_CORRECT);
                    answered = true;
                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    // ── Answer handling ───────────────────────────────────────────────────────

    private void handleAnswer(int selectedIndex) {
        if (answered) return;
        answered = true;
        countDownTimer.cancel();

        Question q = questions[currentIndex];
        boolean isCorrect = q.isCorrect(selectedIndex);

        // Disable all buttons
        for (Button b : optionButtons) b.setEnabled(false);

        if (isCorrect) {
            optionButtons[selectedIndex].setBackgroundColor(CLR_CORRECT);
            score += 10;
            correct++;
        } else {
            optionButtons[selectedIndex].setBackgroundColor(CLR_WRONG);
            optionButtons[q.getCorrectIndex()].setBackgroundColor(CLR_CORRECT);
            incorrect++;
        }

        tvScore.setText("Score: " + score);
        btnNext.setVisibility(View.VISIBLE);
    }

    private void nextQuestion() {
        currentIndex++;
        showQuestion();
    }

    // ── Results screen ────────────────────────────────────────────────────────

    private void showResults() {
        if (countDownTimer != null) countDownTimer.cancel();
        setContentView(R.layout.activity_results);

        int total   = questions.length;
        int pct     = (score * 100) / (total * 10);
        String grade;
        if      (pct >= 90) grade = "A+  Excellent!";
        else if (pct >= 75) grade = "A   Very Good";
        else if (pct >= 60) grade = "B   Good";
        else if (pct >= 40) grade = "C   Average";
        else                grade = "D   Needs Improvement";

        ((TextView) findViewById(R.id.tv_final_score))
                .setText(score + " / " + (total * 10));
        ((TextView) findViewById(R.id.tv_percentage))
                .setText(pct + "%");
        ((TextView) findViewById(R.id.tv_grade))
                .setText(grade);
        ((TextView) findViewById(R.id.tv_breakdown))
                .setText("Correct: " + correct + "   Wrong: " + incorrect
                        + "   Unanswered: " + unanswered);

        Button restart = findViewById(R.id.btn_restart);
        restart.setOnClickListener(v -> {
            currentIndex = 0; score = 0;
            correct = 0; incorrect = 0; unanswered = 0;
            List<Question> list = Arrays.asList(QuestionBank.getQuestions());
            Collections.shuffle(list);
            questions = list.toArray(new Question[0]);
            setContentView(R.layout.activity_quiz);
            // Re-bind views
            tvQuestion  = findViewById(R.id.tv_question);
            tvProgress  = findViewById(R.id.tv_progress);
            tvTimer     = findViewById(R.id.tv_timer);
            tvScore     = findViewById(R.id.tv_score);
            progressBar = findViewById(R.id.progress_bar);
            btnNext     = findViewById(R.id.btn_next);
            int[] optIds = {R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d};
            for (int i = 0; i < 4; i++) {
                optionButtons[i] = findViewById(optIds[i]);
                final int idx = i;
                optionButtons[i].setOnClickListener(bv -> handleAnswer(idx));
            }
            btnNext.setOnClickListener(bv -> nextQuestion());
            progressBar.setMax(questions.length);
            showQuestion();
        });

        Button exit = findViewById(R.id.btn_exit);
        exit.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
