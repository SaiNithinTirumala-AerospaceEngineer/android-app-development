package com.mrcet.quizapp;

/**
 * Question — data model for a single quiz question.
 *
 * Holds the question text, four answer options, and the index
 * (0-based) of the correct option.
 */
public class Question {

    private final String   text;
    private final String[] options;
    private final int      correctIndex;

    public Question(String text, String[] options, int correctIndex) {
        this.text         = text;
        this.options      = options;
        this.correctIndex = correctIndex;
    }

    public String   getText()         { return text; }
    public String[] getOptions()      { return options; }
    public int      getCorrectIndex() { return correctIndex; }

    public boolean isCorrect(int selectedIndex) {
        return selectedIndex == correctIndex;
    }
}
