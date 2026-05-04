package com.mrcet.temperatureconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity — Temperature Converter App
 *
 * Converts temperatures between Celsius, Fahrenheit and Kelvin
 * in real-time as the user types. Supports all six conversion
 * directions and validates input for physical limits (absolute zero).
 *
 * Conversions:
 *   C → F : F = (C × 9/5) + 32
 *   C → K : K = C + 273.15
 *   F → C : C = (F − 32) × 5/9
 *   F → K : K = (F − 32) × 5/9 + 273.15
 *   K → C : C = K − 273.15
 *   K → F : F = (K − 273.15) × 9/5 + 32
 *
 * Course:      Application Development 2
 * Institution: MRCET, Department of Aeronautical Engineering
 * Guide:       Mrs. L. Sushma, Associate Professor
 * Year:        2022–2023
 */
public class MainActivity extends AppCompatActivity {

    private EditText etCelsius, etFahrenheit, etKelvin;
    private TextView tvStatus;
    private Button   btnClear;

    // Flag to prevent recursive TextWatcher calls
    private boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCelsius     = findViewById(R.id.et_celsius);
        etFahrenheit  = findViewById(R.id.et_fahrenheit);
        etKelvin      = findViewById(R.id.et_kelvin);
        tvStatus      = findViewById(R.id.tv_status);
        btnClear      = findViewById(R.id.btn_clear);

        // Celsius input watcher
        etCelsius.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {
                if (isUpdating) return;
                convertFromCelsius(s.toString());
            }
        });

        // Fahrenheit input watcher
        etFahrenheit.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {
                if (isUpdating) return;
                convertFromFahrenheit(s.toString());
            }
        });

        // Kelvin input watcher
        etKelvin.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {
                if (isUpdating) return;
                convertFromKelvin(s.toString());
            }
        });

        // Clear button
        btnClear.setOnClickListener(v -> {
            isUpdating = true;
            etCelsius.setText("");
            etFahrenheit.setText("");
            etKelvin.setText("");
            tvStatus.setText("Enter a temperature in any field");
            tvStatus.setTextColor(getColor(android.R.color.darker_gray));
            isUpdating = false;
        });
    }

    private void convertFromCelsius(String input) {
        if (input.isEmpty() || input.equals("-")) {
            clearOthers(etCelsius); return;
        }
        try {
            double c = Double.parseDouble(input);
            if (c < -273.15) {
                showError("Below absolute zero (−273.15 °C)"); return;
            }
            double f = c * 9.0/5.0 + 32.0;
            double k = c + 273.15;
            isUpdating = true;
            etFahrenheit.setText(fmt(f));
            etKelvin.setText(fmt(k));
            isUpdating = false;
            showStatus(fmt(c) + " °C  =  " + fmt(f) + " °F  =  " + fmt(k) + " K");
        } catch (NumberFormatException e) { clearOthers(etCelsius); }
    }

    private void convertFromFahrenheit(String input) {
        if (input.isEmpty() || input.equals("-")) {
            clearOthers(etFahrenheit); return;
        }
        try {
            double f = Double.parseDouble(input);
            if (f < -459.67) {
                showError("Below absolute zero (−459.67 °F)"); return;
            }
            double c = (f - 32.0) * 5.0/9.0;
            double k = c + 273.15;
            isUpdating = true;
            etCelsius.setText(fmt(c));
            etKelvin.setText(fmt(k));
            isUpdating = false;
            showStatus(fmt(f) + " °F  =  " + fmt(c) + " °C  =  " + fmt(k) + " K");
        } catch (NumberFormatException e) { clearOthers(etFahrenheit); }
    }

    private void convertFromKelvin(String input) {
        if (input.isEmpty() || input.equals("-")) {
            clearOthers(etKelvin); return;
        }
        try {
            double k = Double.parseDouble(input);
            if (k < 0) {
                showError("Kelvin cannot be negative"); return;
            }
            double c = k - 273.15;
            double f = c * 9.0/5.0 + 32.0;
            isUpdating = true;
            etCelsius.setText(fmt(c));
            etFahrenheit.setText(fmt(f));
            isUpdating = false;
            showStatus(fmt(k) + " K  =  " + fmt(c) + " °C  =  " + fmt(f) + " °F");
        } catch (NumberFormatException e) { clearOthers(etKelvin); }
    }

    private void clearOthers(EditText source) {
        isUpdating = true;
        if (source != etCelsius)    etCelsius.setText("");
        if (source != etFahrenheit) etFahrenheit.setText("");
        if (source != etKelvin)     etKelvin.setText("");
        isUpdating = false;
        tvStatus.setText("Enter a temperature in any field");
        tvStatus.setTextColor(getColor(android.R.color.darker_gray));
    }

    private void showStatus(String msg) {
        tvStatus.setText(msg);
        tvStatus.setTextColor(0xFF34C759);
    }

    private void showError(String msg) {
        isUpdating = true;
        etCelsius.setText(""); etFahrenheit.setText(""); etKelvin.setText("");
        isUpdating = false;
        tvStatus.setText("⚠ " + msg);
        tvStatus.setTextColor(0xFFFF3B30);
    }

    private String fmt(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v))
            return String.valueOf((long) v);
        return String.format("%.2f", v);
    }
}
