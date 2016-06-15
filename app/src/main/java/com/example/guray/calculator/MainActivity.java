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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.EmptyStackException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btnEquals;
    Button btnPlus;
    Button btnMinus;
    Button btnMultiply;
    Button btnDivide;
    Button btnDelete;
    Button btnDot;
    Button btnClear;
    Button btnOBracket;
    Button btnCBracket;
    TextView text;
    Calculator calculator;
    boolean equalsPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Standard Mode");
        setContentView(R.layout.activity_main);

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.binary_mode) {
//            setContentView(R.layout.activity_binary);
            startActivity(new Intent(this, BinaryActivity.class));
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
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btnEquals = (Button) findViewById(R.id.buttonEquals);
        btnPlus = (Button) findViewById(R.id.buttonPlus);
        btnMinus = (Button) findViewById(R.id.buttonMinus);
        btnMultiply = (Button) findViewById(R.id.buttonMultiply);
        btnDivide = (Button) findViewById(R.id.buttonDivide);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        btnDot = (Button) findViewById(R.id.buttonDot);
        btnClear = (Button) findViewById(R.id.buttonClear);
        btnOBracket = (Button) findViewById(R.id.buttonOBracket);
        btnCBracket = (Button) findViewById(R.id.buttonCBracket);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnOBracket.setOnClickListener(this);
        btnCBracket.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(equalsPressed) {
            text.setText("");
            equalsPressed = false;
        }

        switch(v.getId()) {
            case R.id.button0:

                if(text.length() == 0)
                    text.append(btn0.getText() + ".");
                else
                    text.append(btn0.getText());
                break;

            case R.id.button1:
                text.append(btn1.getText());
                break;

            case R.id.button2:
                text.append(btn2.getText());
                break;

            case R.id.button3:
                text.append(btn3.getText());
                break;

            case R.id.button4:
                text.append(btn4.getText());
                break;

            case R.id.button5:
                text.append(btn5.getText());
                break;

            case R.id.button6:
                text.append(btn6.getText());
                break;

            case R.id.button7:
                text.append(btn7.getText());
                break;

            case R.id.button8:
                text.append(btn8.getText());
                break;

            case R.id.button9:
                text.append(btn9.getText());
                break;

            case R.id.buttonEquals:
                calculator = new Calculator(text.getText().toString());
                double result;
                SpannableStringBuilder resultString;
                NumberFormat formatter = new DecimalFormat("#0.00000");
                try {
                    result = calculator.calculate();
                    resultString = new SpannableStringBuilder(text.getText().toString().concat("\n" +
                            "= " + String.valueOf(Double.parseDouble(formatter.format(result).replace(',', '.'))) + "\n"));
                    resultString.setSpan(new ForegroundColorSpan(Color.parseColor("#49A113")), resultString.toString().indexOf('='), resultString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    text.setText(resultString);
                } catch (NumberFormatException | EmptyStackException nfe) {
                    printInvalidText();
                }
                equalsPressed = true;
                break;

            case R.id.buttonClear:
                text.setText("");
                break;

            case R.id.buttonDot:
                if (isCorrectExpression())
                    text.append(btnDot.getText());
                break;

            case R.id.buttonPlus:
                if (isCorrectExpression())
                    text.append(" " + btnPlus.getText() + " ");
                break;

            case R.id.buttonMinus:
                if (isCorrectExpression())
                    text.append(" " + btnMinus.getText() + " ");
                break;

            case R.id.buttonMultiply:
                if (isCorrectExpression())
                    text.append(" " + btnMultiply.getText() + " ");
                break;

            case R.id.buttonDivide:
                if (isCorrectExpression())
                    text.append(" " + btnDivide.getText() + " ");
                break;

            case R.id.buttonDelete:
                if (text.length() > 0) {
                    if (text.getText().charAt(text.length() - 1) == ' ')
                        text.setText(text.getText().subSequence(0, text.length() - 3));
                    else
                        text.setText(text.getText().subSequence(0, text.length() - 1));
                }
                break;

            case R.id.buttonOBracket:
                if (text.length() == 0 || !isCorrectExpression() && text.getText().charAt(text.length() - 1) != '.')
                    text.append(btnOBracket.getText());
                else if (isCorrectExpression())
                    text.append(" * " + btnOBracket.getText());
                break;

            case R.id.buttonCBracket:
                if (isCorrectExpression() && !correctBrackets())
                    text.append(btnCBracket.getText());
                break;
        }
    }

    private void printInvalidText() {
        SpannableStringBuilder resultString = new SpannableStringBuilder(text.getText().toString().concat("\nInvalid format!\n"));
        resultString.setSpan(new ForegroundColorSpan(Color.parseColor("#cf2d2f")), resultString.toString().indexOf('I'), resultString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        text.setText(resultString);
    }

    private boolean isCorrectExpression() {

        if(text.length() == 0)
            return false;

        char lastChar = text.getText().charAt(text.length() - 1);
        return !(lastChar == '.' || lastChar == ' ' || lastChar == '(');

    }

    private boolean correctBrackets() {
        String expression = text.getText().toString();
        Stack<String> stack = new Stack<>();

        for(int index = 0; index < expression.length(); index++){
            if(expression.charAt(index) == '('){
                stack.push("(");
            }
            else if(expression.charAt(index) == ')'){
                if(stack.isEmpty()){
                    return false;
                }
                else{
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();

    }
}
