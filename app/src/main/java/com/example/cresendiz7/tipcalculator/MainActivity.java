package com.example.cresendiz7.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView total, tipNumber, totalTip, peopleValue, tipLabel, balanceLabel;
    private EditText balance;
    private Double balanceValue;
    private int actualProgress;

    DecimalFormat df = new DecimalFormat("#,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total = (TextView) findViewById(R.id.totalBalance);
        SeekBar tipAmount = (SeekBar) findViewById(R.id.tipSeekBar);
        balanceLabel = (TextView) findViewById(R.id.totalView);
        balance = (EditText) findViewById(R.id.balance);
        tipLabel = (TextView) findViewById(R.id.tipView);
        tipNumber = (TextView) findViewById(R.id.tipNumber);
        totalTip = (TextView) findViewById(R.id.totalTip);
        Button minus = (Button) findViewById(R.id.buttonMinus);
        Button plus = (Button) findViewById(R.id.buttonPlus);
        peopleValue = (TextView) findViewById(R.id.peopleValue);

        balance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(balance.getText().toString().equals(""))
                    balanceValue = 0.00;
                else if(balance.getText().toString().equals(".")){
                    balance.setText("0.");
                    balance.setSelection(balance.getText().length());
                }
                else
                    balanceValue = Double.parseDouble(balance.getText().toString());

                int pv = Integer.parseInt(peopleValue.getText().toString());

                String t = "$" + df.format((balanceValue * actualProgress/100) / pv);
                totalTip.setText(t);
                String b = "$" + df.format((balanceValue + (balanceValue * actualProgress/100)) / pv);
                total.setText(b);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        tipAmount.incrementProgressBy(1);
        tipAmount.setMax(6);
        tipAmount.setProgress(0);
        tipAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(balance.getText().toString().equals(""))
                    balanceValue = 0.00;
                else
                    balanceValue = Double.parseDouble(balance.getText().toString());
                int pv = Integer.parseInt(peopleValue.getText().toString());
                actualProgress = progress * 5;
                String p = actualProgress+ "%";
                tipNumber.setText(p);
                String t = "$" + df.format((balanceValue * actualProgress/100) / pv);
                totalTip.setText(t);
                String b = "$" + df.format((balanceValue + (balanceValue * actualProgress/100)) / pv);
                total.setText(b);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pv = Integer.parseInt(peopleValue.getText().toString());
                int newPV = pv - 1;
                if(newPV <= 1){
                    newPV = 1;
                    peopleValue.setText(String.valueOf(newPV));
                    tipLabel.setText(R.string.total_tip);
                    balanceLabel.setText(R.string.total_balance);
                }
                else
                    peopleValue.setText(String.valueOf(newPV));
                if(balance.getText().toString().equals(""))
                    balanceValue = 0.00;
                else
                    balanceValue = Double.parseDouble(balance.getText().toString());
                String t = "$" + df.format((balanceValue * actualProgress/100) / newPV);
                totalTip.setText(t);
                String b = "$" + df.format((balanceValue + (balanceValue * actualProgress/100)) / newPV);
                total.setText(b);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pv = Integer.parseInt(peopleValue.getText().toString());
                int newPV = pv + 1;
                peopleValue.setText(String.valueOf(newPV));

                if(newPV > 1){
                    tipLabel.setText(R.string.total_tip_per_person);
                    balanceLabel.setText(R.string.total_balance_per_person);
                }

                if(balance.getText().toString().equals(""))
                    balanceValue = 0.00;
                else
                    balanceValue = Double.parseDouble(balance.getText().toString());
                String t = "$" + df.format((balanceValue * actualProgress/100) / newPV);
                totalTip.setText(t);
                String b = "$" + df.format((balanceValue + (balanceValue * actualProgress/100)) / newPV);
                total.setText(b);
            }
        });
    }
}