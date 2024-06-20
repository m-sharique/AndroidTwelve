package com.example.twelve;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextDistance;
    private TextView textViewDistanceResult;

    private EditText editTextCurrency;
    private TextView textViewCurrencyResult;
    private Button buttonConvertCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Distance converter views
        editTextDistance = findViewById(R.id.editTextDistance);
        textViewDistanceResult = findViewById(R.id.textViewDistanceResult);

        // Currency converter views
        editTextCurrency = findViewById(R.id.editTextCurrency);
        textViewCurrencyResult = findViewById(R.id.textViewCurrencyResult);
        buttonConvertCurrency = findViewById(R.id.buttonConvertCurrency);

        // Set onClickListener using anonymous inner class
        buttonConvertCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }

    // Method referenced in XML using android:onClick
    public void convertDistance(View view) {
        String distanceStr = editTextDistance.getText().toString();
        if (!distanceStr.isEmpty()) {
            double kilometers = Double.parseDouble(distanceStr);
            double miles = kilometers * 0.621371;
            textViewDistanceResult.setText(String.format("%.2f miles", miles));
        } else {
            textViewDistanceResult.setText("Please enter a value");
        }
    }

    // Method called from button onClickListener
    private void convertCurrency() {
        String currencyStr = editTextCurrency.getText().toString();
        if (!currencyStr.isEmpty()) {
            double usd = Double.parseDouble(currencyStr);
            double eur = usd * 0.85; // Example conversion rate
            textViewCurrencyResult.setText(String.format("%.2f EUR", eur));
        } else {
            textViewCurrencyResult.setText("Please enter a value");
        }
    }
}