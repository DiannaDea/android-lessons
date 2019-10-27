package com.example.pz_3;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    EditText inputEditText;

    Calculator calculator = Calculator.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = (EditText) findViewById(R.id.inputEditText);

        this.initCalculator();

        ((Button) findViewById(R.id.buttoneql)).setOnClickListener(this.handleButtonEqualClick());
        ((Button) findViewById(R.id.buttonC)).setOnClickListener(this.handleButtonEmptyClick());
    }

    public View.OnClickListener handleButtonEmptyClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.setText("");
                calculator.emptyResult();
            }
        };
    }

    public View.OnClickListener handleButtonEqualClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.addValue(Integer.parseInt(inputEditText.getText().toString()));
                float result = calculator.getResult();
                inputEditText.setText(result + "");
            }
        };
    }

    public void initCalculator() {
        this.setNumberHandler((Button) findViewById(R.id.button0), "0");
        this.setNumberHandler((Button) findViewById(R.id.button1), "1");
        this.setNumberHandler((Button) findViewById(R.id.button2), "2");
        this.setNumberHandler((Button) findViewById(R.id.button3), "3");
        this.setNumberHandler((Button) findViewById(R.id.button4), "4");
        this.setNumberHandler((Button) findViewById(R.id.button5), "5");
        this.setNumberHandler((Button) findViewById(R.id.button6), "6");
        this.setNumberHandler((Button) findViewById(R.id.button7), "7");
        this.setNumberHandler((Button) findViewById(R.id.button8), "8");
        this.setNumberHandler((Button) findViewById(R.id.button9), "9");
        this.setNumberHandler((Button) findViewById(R.id.button10), ".");

        this.setButtonHandler((Button) findViewById(R.id.buttonadd), Operation.ADD);
        this.setButtonHandler((Button) findViewById(R.id.buttonsub), Operation.SUBTRACT);
        this.setButtonHandler((Button) findViewById(R.id.buttonmul), Operation.MULTIPLY);
        this.setButtonHandler((Button) findViewById(R.id.buttondiv), Operation.DIVIDE);
    }

    public void setNumberHandler(Button button, final String number){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.setText(inputEditText.getText() + number);
            }
        });
    }

    public void setButtonHandler(Button button, final Operation op){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEditText == null) {
                    inputEditText.setText("");
                } else {
                    calculator.addValue(Float.parseFloat(inputEditText.getText().toString()));
                    calculator.addOperation(op);
                    inputEditText.setText(null);
                }
            }
        });
    }
}
