package com.mrcet.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity — Calculator App
 *
 * A fully functional Android calculator supporting:
 *   - Basic arithmetic: addition, subtraction, multiplication, division
 *   - Full expression evaluation (not just two-number operations)
 *   - Decimal point support
 *   - Percentage calculation
 *   - Sign toggle (+/-)
 *   - Error handling: division by zero, malformed expressions
 *   - Calculation history log (last 5 calculations)
 *   - Clear (C) and backspace (⌫) operations
 *
 * Course:      Application Development 2
 * Institution: MRCET, Department of Aeronautical Engineering
 * Guide:       Mrs. L. Sushma, Associate Professor
 * Year:        2022–2023
 */
public class MainActivity extends AppCompatActivity {

    // Display
    private TextView displayText;
    private TextView historyText;
    private ScrollView historyScroll;

    // State
    private StringBuilder expression = new StringBuilder();
    private String        history     = "";
    private boolean       resultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText  = findViewById(R.id.display_text);
        historyText  = findViewById(R.id.history_text);
        historyScroll = findViewById(R.id.history_scroll);

        // Number buttons
        int[] numberIds = {
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
            R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9
        };
        String[] numberLabels = {"0","1","2","3","4","5","6","7","8","9"};

        for (int i = 0; i < numberIds.length; i++) {
            final String label = numberLabels[i];
            Button btn = findViewById(numberIds[i]);
            if (btn != null) {
                btn.setOnClickListener(v -> appendToExpression(label));
            }
        }

        // Operator buttons
        setupButton(R.id.btn_add,      () -> appendOperator("+"));
        setupButton(R.id.btn_subtract, () -> appendOperator("-"));
        setupButton(R.id.btn_multiply, () -> appendOperator("×"));
        setupButton(R.id.btn_divide,   () -> appendOperator("÷"));

        // Special buttons
        setupButton(R.id.btn_decimal,  () -> appendDecimal());
        setupButton(R.id.btn_percent,  () -> appendPercent());
        setupButton(R.id.btn_sign,     () -> toggleSign());
        setupButton(R.id.btn_clear,    () -> clearAll());
        setupButton(R.id.btn_back,     () -> backspace());
        setupButton(R.id.btn_equals,   () -> evaluate());
    }

    private void setupButton(int id, Runnable action) {
        Button btn = findViewById(id);
        if (btn != null) btn.setOnClickListener(v -> action.run());
    }

    // ── Input handling ────────────────────────────────────────────────────────

    private void appendToExpression(String digit) {
        if (resultShown) {
            expression.setLength(0);
            resultShown = false;
        }
        expression.append(digit);
        updateDisplay();
    }

    private void appendOperator(String op) {
        resultShown = false;
        if (expression.length() == 0) {
            if (op.equals("-")) {
                expression.append("-");
            }
            return;
        }
        // Replace last operator if already one at end
        char last = expression.charAt(expression.length() - 1);
        if (last == '+' || last == '-' || last == '×' || last == '÷') {
            expression.setCharAt(expression.length() - 1, op.charAt(0));
        } else {
            expression.append(op);
        }
        updateDisplay();
    }

    private void appendDecimal() {
        if (resultShown) {
            expression.setLength(0);
            expression.append("0");
            resultShown = false;
        }
        // Find start of current number segment
        String expr = expression.toString();
        int lastOp  = Math.max(Math.max(
            expr.lastIndexOf('+'), expr.lastIndexOf('-')),
            Math.max(expr.lastIndexOf('×'), expr.lastIndexOf('÷')));
        String currentNum = expr.substring(lastOp + 1);
        if (!currentNum.contains(".")) {
            if (currentNum.isEmpty()) expression.append("0");
            expression.append(".");
            updateDisplay();
        }
    }

    private void appendPercent() {
        if (expression.length() == 0) return;
        try {
            double val = Double.parseDouble(expression.toString());
            val /= 100.0;
            expression.setLength(0);
            expression.append(formatResult(val));
            updateDisplay();
        } catch (NumberFormatException ignored) {}
    }

    private void toggleSign() {
        if (expression.length() == 0) return;
        String expr = expression.toString();
        if (expr.startsWith("-")) {
            expression.deleteCharAt(0);
        } else {
            expression.insert(0, "-");
        }
        updateDisplay();
    }

    private void clearAll() {
        expression.setLength(0);
        resultShown = false;
        displayText.setText("0");
    }

    private void backspace() {
        if (resultShown) {
            clearAll();
            return;
        }
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length() - 1);
            updateDisplay();
        }
    }

    // ── Evaluation ────────────────────────────────────────────────────────────

    private void evaluate() {
        if (expression.length() == 0) return;
        String expr = expression.toString();

        try {
            double result = evalExpression(expr);
            String resultStr = formatResult(result);

            // Add to history
            addToHistory(expr + " = " + resultStr);

            expression.setLength(0);
            expression.append(resultStr);
            displayText.setText(resultStr);
            resultShown = true;

        } catch (ArithmeticException e) {
            displayText.setText("Error: " + e.getMessage());
            expression.setLength(0);
            resultShown = true;
        } catch (Exception e) {
            displayText.setText("Error");
            expression.setLength(0);
            resultShown = true;
        }
    }

    /**
     * Simple two-pass expression evaluator.
     * Pass 1: handle × and ÷
     * Pass 2: handle + and -
     */
    private double evalExpression(String expr) {
        // Replace display operators with standard chars
        expr = expr.replace("×", "*").replace("÷", "/");

        // Split by + and - (keeping delimiters)
        java.util.List<String> addTerms = splitOnAddSub(expr);
        double result = 0;
        for (int i = 0; i < addTerms.size(); i++) {
            String term = addTerms.get(i);
            double termVal = evalMulDiv(term);
            result += termVal;
        }
        return result;
    }

    private java.util.List<String> splitOnAddSub(String expr) {
        java.util.List<String> parts = new java.util.ArrayList<>();
        int start = 0;
        for (int i = 1; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if ((c == '+' || c == '-') && i > 0
                    && expr.charAt(i-1) != '*'
                    && expr.charAt(i-1) != '/') {
                parts.add(expr.substring(start, i));
                start = i;
            }
        }
        parts.add(expr.substring(start));
        return parts;
    }

    private double evalMulDiv(String expr) {
        String[] parts = expr.split("(?<=[*/])(?=[^*/])|(?<=[^*/])(?=[*/])");
        double result = Double.parseDouble(parts[0].trim());
        for (int i = 1; i < parts.length - 1; i += 2) {
            String op  = parts[i].trim();
            double val = Double.parseDouble(parts[i+1].trim());
            if (op.equals("*"))      result *= val;
            else if (op.equals("/")) {
                if (val == 0) throw new ArithmeticException("Division by zero");
                result /= val;
            }
        }
        return result;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String formatResult(double val) {
        if (val == Math.floor(val) && !Double.isInfinite(val)
                && Math.abs(val) < 1e12) {
            return String.valueOf((long) val);
        }
        return String.valueOf(val);
    }

    private void updateDisplay() {
        String expr = expression.toString();
        displayText.setText(expr.isEmpty() ? "0" : expr);
    }

    private void addToHistory(String entry) {
        String[] lines = history.isEmpty()
                ? new String[0] : history.split("\n");
        java.util.List<String> list = new java.util.ArrayList<>(
                java.util.Arrays.asList(lines));
        list.add(entry);
        if (list.size() > 5) list.remove(0);
        history = String.join("\n", list);
        historyText.setText(history);
        historyScroll.post(() ->
                historyScroll.fullScroll(ScrollView.FOCUS_DOWN));
    }
}