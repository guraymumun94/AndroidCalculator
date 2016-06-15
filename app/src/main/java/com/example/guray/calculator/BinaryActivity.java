package com.example.guray.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BinaryActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn0;
    Button btn1;
    Button btnEquals;
    Button btnOr;
    Button btnAnd;
    Button btnDelete;
    Button btnClear;
    Button btnDecimal;
    Button btnXor;
    Button btnNot;
    TextView text;

    boolean equalsPressed;
    boolean decimalPressed;
    boolean notPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Binary Mode");
        setContentView(R.layout.activity_binary);

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.binary_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main_mode) {
//            setContentView(R.layout.activity_main);
            startActivity(new Intent(this, MainActivity.class));

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initialize() {
        text = (TextView) findViewById(R.id.textView);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText("");

        btn0 = (Button) findViewById(R.id.button0);
        btn1 = (Button) findViewById(R.id.button1);
        btnEquals = (Button) findViewById(R.id.buttonEquals);
        btnOr = (Button) findViewById(R.id.buttonOr);
        btnAnd = (Button) findViewById(R.id.buttonAnd);
        btnXor = (Button) findViewById(R.id.buttonXor);
        btnNot = (Button) findViewById(R.id.buttonNot);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        btnDecimal = (Button) findViewById(R.id.buttonDecimal);
        btnClear = (Button) findViewById(R.id.buttonClear);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btnOr.setOnClickListener(this);
        btnAnd.setOnClickListener(this);
        btnXor.setOnClickListener(this);
        btnNot.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnDecimal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(equalsPressed || decimalPressed || notPressed) {
            text.setText("");
            equalsPressed = false;
            decimalPressed = false;
            notPressed = false;
        }

        switch (v.getId()) {
            case R.id.button0:
                text.append(btn0.getText());
                break;

            case R.id.button1:
                text.append(btn1.getText());
                break;

            case R.id.buttonOr:
                if(!containsOperator() && text.length() > 0) {
                    text.append(getOperator(btnOr.getText().toString()));
                    disableButtons();
                }

                break;

            case R.id.buttonAnd:
                if(!containsOperator() && text.length() > 0) {
                    text.append(getOperator(btnAnd.getText().toString()));
                    disableButtons();
                }


                break;

            case R.id.buttonXor:
                if(!containsOperator() && text.length() > 0) {
                    text.append(getOperator(btnXor.getText().toString()));
                    disableButtons();
                }

                break;

            case R.id.buttonNot:
                try {
                    if(isCorrectExpression() && !containsOperator() || text.length() > 0) {
                        SpannableStringBuilder resultString;
                        int result = Integer.parseInt(text.getText().toString(), 2);
                        String binaryString = Integer.toBinaryString(~result);
                        resultString = new SpannableStringBuilder(text.getText().toString().concat("\n= (NOT) " + binaryString.substring(binaryString.length() - text.getText().length())));
                        resultString.setSpan(new ForegroundColorSpan(Color.parseColor("#49A113")), resultString.toString().indexOf('='), resultString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        text.setText(resultString);
                    }

                } catch (NullPointerException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
                    printInvalidText();
                }

                notPressed = true;
                break;

            case R.id.buttonEquals:
                try {
                    if(isCorrectExpression() && text.getText() != null) {
                        BinaryCalculator calculator = new BinaryCalculator(text.getText().toString());
                        String result = calculator.calculate();
                        SpannableStringBuilder resultString;

                        resultString = new SpannableStringBuilder(text.getText().toString().concat("\n" +
                                "= " + result + "\n"));
                        resultString.setSpan(new ForegroundColorSpan(Color.parseColor("#49A113")), resultString.toString().indexOf('='), resultString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        text.setText(resultString);


                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
                    printInvalidText();
                }
                enableButtons();
                equalsPressed = true;

                break;

            case R.id.buttonDecimal:
                try {
                    if(isCorrectExpression() && !containsOperator()) {
                        SpannableStringBuilder resultString;
                        resultString = new SpannableStringBuilder(text.getText().toString().concat("\n= (DECIMAL) " + String.valueOf(Integer.parseInt(text.getText().toString(), 2))));
                        resultString.setSpan(new ForegroundColorSpan(Color.parseColor("#49A113")), resultString.toString().indexOf('='), resultString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        text.setText(resultString);
                    }
                    else
                        printInvalidText();
                    enableButtons();
                } catch (NullPointerException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
                    printInvalidText();
                }

                decimalPressed = true;

                break;

            case R.id.buttonDelete:
                if (text.length() > 0) {
                    if (text.getText().charAt(text.length() - 1) == '\n') {
                        text.setText(text.getText().subSequence(0, text.getText().toString().indexOf('\n')));
                        enableButtons();
                    }
                    else if (text.getText().charAt(text.length() - 1) == ')')
                        text.setText(text.getText().subSequence(0, text.getText().toString().indexOf('(')));
                    else
                        text.setText(text.getText().subSequence(0, text.length() - 1));
                }
                break;

            case R.id.buttonClear:
                text.setText("");
                enableButtons();

                break;
        }
    }

    private boolean containsOperator() {
        return text.getText().toString().contains("AND") || text.getText().toString().contains("OR")
                || text.getText().toString().contains("XOR") || text.getText().toString().contains("NOT");

    }


    private String getOperator(String operator) {
        return "\n" + operator + String.format("%1$-" + (42 - operator.length()) + "s", "") + "\n";
    }

    private boolean isCorrectExpression() {
        return text.getText().toString().charAt(text.length() - 1) != '\n' || text.getText().toString().charAt(text.length() - 1) != ' ';

    }

    private void printInvalidText() {
        SpannableStringBuilder resultString = new SpannableStringBuilder(text.getText().toString().concat("\nInvalid format!\n"));
        resultString.setSpan(new ForegroundColorSpan(Color.parseColor("#cf2d2f")), resultString.toString().indexOf('I'), resultString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        text.setText(resultString);
    }

    private void disableButtons() {
        btnAnd.setEnabled(false);
        btnOr.setEnabled(false);
        btnNot.setEnabled(false);
        btnXor.setEnabled(false);
    }

    private void enableButtons() {
        btnAnd.setEnabled(true);
        btnOr.setEnabled(true);
        btnNot.setEnabled(true);
        btnXor.setEnabled(true);
    }
}
