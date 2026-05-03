package com.mrcet.smsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity — Android SMS Sending App using Intent
 *
 * Demonstrates Android Intent system by launching the device's default
 * SMS messaging application with a pre-filled phone number and message.
 *
 * Flow:
 *   1. User enters recipient phone number
 *   2. User composes message body
 *   3. On button click — Intent with ACTION_VIEW and smsto: URI is fired
 *   4. Android routes to default SMS app with fields pre-populated
 *   5. User confirms and sends from within the messaging app
 *
 * Permission required (AndroidManifest.xml):
 *   <uses-permission android:name="android.permission.SEND_SMS" />
 *
 * Course:      Application Development 2
 * Institution: MRCET, Department of Aeronautical Engineering
 * Guide:       Mrs. L. Sushma, Associate Professor
 * Year:        2022–2023
 */
public class MainActivity extends AppCompatActivity {

    // UI components
    private EditText phoneNumberEditText;
    private EditText messageEditText;
    private Button   sendSmsButton;
    private Button   clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind UI components
        phoneNumberEditText = findViewById(R.id.phone_number);
        messageEditText     = findViewById(R.id.message);
        sendSmsButton       = findViewById(R.id.send_sms);
        clearButton         = findViewById(R.id.clear_fields);

        // Send SMS button click listener
        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        // Clear button click listener
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText("");
                messageEditText.setText("");
                phoneNumberEditText.requestFocus();
                Toast.makeText(MainActivity.this,
                        "Fields cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Validates inputs and fires an SMS Intent to the default messaging app.
     *
     * Uses Intent.ACTION_VIEW with a smsto: URI scheme, which pre-fills
     * the recipient number and message body in the system SMS application.
     * This approach does not require runtime SEND_SMS permission on
     * Android 6.0+ (the permission check is delegated to the SMS app).
     */
    private void sendSms() {
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String message     = messageEditText.getText().toString().trim();

        // Validate phone number
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEditText.setError("Phone number is required");
            phoneNumberEditText.requestFocus();
            return;
        }

        // Validate phone number format (digits, +, -, spaces only)
        if (!phoneNumber.matches("[+\\d\\s\\-()]{7,15}")) {
            phoneNumberEditText.setError("Enter a valid phone number");
            phoneNumberEditText.requestFocus();
            return;
        }

        // Validate message
        if (TextUtils.isEmpty(message)) {
            messageEditText.setError("Message cannot be empty");
            messageEditText.requestFocus();
            return;
        }

        // Build and fire SMS intent
        try {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
            smsIntent.putExtra("sms_body", message);

            // Ensure an SMS app is available on the device
            if (smsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(smsIntent);
            } else {
                Toast.makeText(this,
                        "No SMS application found on this device",
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this,
                    "Failed to open SMS app: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}