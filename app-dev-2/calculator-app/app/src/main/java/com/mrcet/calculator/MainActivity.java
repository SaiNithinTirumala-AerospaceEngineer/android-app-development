package com.mrcet.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity — Calculator App
 *
 * Supports +, -, ×, ÷ with operator precedence,
 * decimal input, sign toggle, percentage, backspace,
 * division by zero handling, and last-5 history log.
 *
 * Course:      Application Development 2
 * Institution: MRCET, Department of Aeronautical Engineering
 * Guide:       Mrs. L. Sushma, Associate Professor
 * Year:        2022–2023
 */
public class MainActivity extends AppCompatActivity {

    private TextView displayText;
    private TextView historyText;
    private ScrollView historyScroll;

    private StringBuilder expression  = new StringBuilder();
    private StringBuilder historyLog  = new StringBuilder();
    private boolean       resultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText  = findViewById(R.id.display_text);
        historyText  = findViewById(R.id.history_text);
        historyScroll = findViewById(R.id.history_scroll);

        // Number buttons
        int[] numIds = {R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,
                R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7,
                R.id.btn_8,R.id.btn_9};
        String[] nums = {"0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i < numIds.length; i++) {
            final String n = nums[i];
            Button b = findViewById(numIds[i]);
            if (b != null) b.setOnClickListener(v -> append(n));
        }

        // Operators
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnSub = findViewById(R.id.btn_subtract);
        Button btnMul = findViewById(R.id.btn_multiply);
        Button btnDiv = findViewById(R.id.btn_divide);
        if (btnAdd != null) btnAdd.setOnClickListener(v -> appendOp("+"));
        if (btnSub != null) btnSub.setOnClickListener(v -> appendOp("-"));
        if (btnMul != null) btnMul.setOnClickListener(v -> appendOp("*"));
        if (btnDiv != null) btnDiv.setOnClickListener(v -> appendOp("/"));

        // Special
        Button btnDot  = findViewById(R.id.btn_decimal);
        Button btnPct  = findViewById(R.id.btn_percent);
        Button btnSign = findViewById(R.id.btn_sign);
        Button btnClr  = findViewById(R.id.btn_clear);
        Button btnBk   = findViewById(R.id.btn_back);
        Button btnEq   = findViewById(R.id.btn_equals);
        if (btnDot  != null) btnDot.setOnClickListener(v -> appendDot());
        if (btnPct  != null) btnPct.setOnClickListener(v -> percent());
        if (btnSign != null) btnSign.setOnClickListener(v -> toggleSign());
        if (btnClr  != null) btnClr.setOnClickListener(v -> clear());
        if (btnBk   != null) btnBk.setOnClickListener(v -> backspace());
        if (btnEq   != null) btnEq.setOnClickListener(v -> evaluate());
    }

    private void append(String d) {
        if (resultShown) { expression.setLength(0); resultShown = false; }
        expression.append(d);
        displayText.setText(expression.toString());
    }

    private void appendOp(String op) {
        resultShown = false;
        if (expression.length() == 0) { if (op.equals("-")) expression.append("-"); return; }
        char last = expression.charAt(expression.length()-1);
        if ("+-*/".indexOf(last) >= 0) expression.setCharAt(expression.length()-1, op.charAt(0));
        else expression.append(op);
        // Show × and ÷ symbols on display
        displayText.setText(expression.toString()
                .replace("*","×").replace("/","÷"));
    }

    private void appendDot() {
        if (resultShown) { expression.setLength(0); expression.append("0"); resultShown = false; }
        String e = expression.toString();
        int last = Math.max(Math.max(e.lastIndexOf('+'), e.lastIndexOf('-')),
                Math.max(e.lastIndexOf('*'), e.lastIndexOf('/')));
        if (!e.substring(last+1).contains(".")) {
            if (e.isEmpty() || "+-*/".indexOf(e.charAt(e.length()-1)) >= 0)
                expression.append("0");
            expression.append(".");
            displayText.setText(expression.toString());
        }
    }

    private void percent() {
        try {
            double v = Double.parseDouble(expression.toString()) / 100.0;
            expression.setLength(0);
            expression.append(fmt(v));
            displayText.setText(expression.toString());
        } catch (Exception ignored) {}
    }

    private void toggleSign() {
        if (expression.length() == 0) return;
        if (expression.charAt(0) == '-') expression.deleteCharAt(0);
        else expression.insert(0, '-');
        displayText.setText(expression.toString());
    }

    private void clear() {
        expression.setLength(0);
        resultShown = false;
        displayText.setText("0");
    }

    private void backspace() {
        if (resultShown) { clear(); return; }
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length()-1);
            String s = expression.toString();
            displayText.setText(s.isEmpty() ? "0" : s);
        }
    }

    private void evaluate() {
        if (expression.length() == 0) return;
        String expr = expression.toString();
        try {
            double result = eval(expr);
            String res = fmt(result);
            // Add to history (keep last 5)
            historyLog.append(expr.replace("*","×").replace("/","÷"))
                    .append(" = ").append(res).append("\n");
            String[] lines = historyLog.toString().split("\n");
            if (lines.length > 5) {
                historyLog.setLength(0);
                for (int i = lines.length-5; i < lines.length; i++)
                    historyLog.append(lines[i]).append("\n");
            }
            historyText.setText(historyLog.toString().trim());
            historyScroll.post(() -> historyScroll.fullScroll(ScrollView.FOCUS_DOWN));

            expression.setLength(0);
            expression.append(res);
            displayText.setText(res);
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

    /** Recursive descent evaluator — handles +, -, *, / with precedence. */
    private int pos;
    private String evalStr;

    private double eval(String s) {
        pos = 0; evalStr = s;
        double result = parseExpr();
        if (pos != evalStr.length()) throw new RuntimeException("Unexpected: " + evalStr.charAt(pos));
        return result;
    }

    private double parseExpr() {
        double v = parseTerm();
        while (pos < evalStr.length()) {
            char c = evalStr.charAt(pos);
            if (c == '+') { pos++; v += parseTerm(); }
            else if (c == '-') { pos++; v -= parseTerm(); }
            else break;
        }
        return v;
    }

    private double parseTerm() {
        double v = parseFactor();
        while (pos < evalStr.length()) {
            char c = evalStr.charAt(pos);
            if (c == '*') { pos++; v *= parseFactor(); }
            else if (c == '/') {
                pos++;
                double d = parseFactor();
                if (d == 0) throw new ArithmeticException("Division by zero");
                v /= d;
            } else break;
        }
        return v;
    }

    private double parseFactor() {
        boolean neg = false;
        if (pos < evalStr.length() && evalStr.charAt(pos) == '-') { neg = true; pos++; }
        StringBuilder num = new StringBuilder();
        while (pos < evalStr.length() &&
                (Character.isDigit(evalStr.charAt(pos)) || evalStr.charAt(pos) == '.'))
            num.append(evalStr.charAt(pos++));
        if (num.length() == 0) throw new RuntimeException("Expected number at pos " + pos);
        double v = Double.parseDouble(num.toString());
        return neg ? -v : v;
    }

    private String fmt(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v) && Math.abs(v) < 1e12)
            return String.valueOf((long) v);
        return String.valueOf(v);
    }
}
