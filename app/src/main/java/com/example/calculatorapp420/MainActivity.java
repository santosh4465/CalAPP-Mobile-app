package com.example.calculatorapp420;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.BLACK);
        mainLayout.setPadding(16, 16, 16, 16);

        display = new TextView(this);
        display.setTextSize(48);
        display.setGravity(Gravity.END);
        display.setPadding(16, 16, 16, 16);
        display.setText("0");
        display.setTextColor(Color.WHITE);
        mainLayout.addView(display, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        GridLayout buttonGrid = new GridLayout(this);
        buttonGrid.setColumnCount(4);
        buttonGrid.setRowCount(5);

        String[] buttonLabels = {
                "C", "±", "%", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "="
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            Button button = new Button(this);
            button.setText(buttonLabels[i]);
            button.setTextSize(24);
            button.setOnClickListener(this);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(i % 4, 1f);
            params.rowSpec = GridLayout.spec(i / 4, 1f);

            if (i == 16) { // Make "0" button span two columns
                params.columnSpec = GridLayout.spec(i % 4, 2, 1f);
            }

            if ("÷×-+=".contains(buttonLabels[i])) {
                button.setBackgroundColor(Color.parseColor("#FF9500"));
                button.setTextColor(Color.WHITE);
            } else if ("C±%".contains(buttonLabels[i])) {
                button.setBackgroundColor(Color.parseColor("#A5A5A5"));
                button.setTextColor(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.parseColor("#333333"));
                button.setTextColor(Color.WHITE);
            }

            buttonGrid.addView(button, params);
        }

        mainLayout.addView(buttonGrid, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        setContentView(mainLayout);
    }

    @Override
    public void onClick(View view) {
        String buttonText = ((Button) view).getText().toString();

        switch (buttonText) {
            case "C":
                onClearClick();
                break;
            case "=":
                onEqualClick();
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
                onOperatorClick(buttonText);
                break;
            default:
                onDigitClick(buttonText);
                break;
        }
    }

    private void onDigitClick(String digit) {
        if (isNewOperation) {
            display.setText("");
            isNewOperation = false;
        }
        currentInput += digit;
        display.setText(currentInput);
    }

    private void onOperatorClick(String op) {
        if (!currentInput.isEmpty()) {
            firstOperand = Double.parseDouble(currentInput);
            operator = op;
            currentInput = "";
            isNewOperation = false;
        }
    }

    private void onEqualClick() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "×":
                    result = firstOperand * secondOperand;
                    break;
                case "÷":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }
            display.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
            operator = "";
            isNewOperation = true;
        }
    }

    private void onClearClick() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        isNewOperation = true;
        display.setText("0");
    }
}