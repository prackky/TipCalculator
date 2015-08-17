package com.calculator.examp.tipcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class TipCalculator extends ActionBarActivity {

    private static final String BILL_TOTAL = "BILL_TOTAL";
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    private double currentBillTotal;
    private int currentCustomPercent;
    private EditText tip10EditText;
    private EditText total10EditText;
    private EditText tip15EditText;
    private EditText total15EditText;
    private EditText billEditText;
    private EditText tip20EditText;
    private EditText total20EditText;
    private TextView customTipTextView;
    private EditText tipCustomEditText;
    private EditText totalCustomEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            currentBillTotal = 0.0;
            currentCustomPercent = 18;
        }
        else {
            currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
            currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
        }

        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditText);
        billEditText = (EditText) findViewById(R.id.billEditText);
        tip20EditText = (EditText) findViewById(R.id.tip20EditText);
        total20EditText = (EditText) findViewById(R.id.total20EditText);
        customTipTextView = (TextView) findViewById(R.id.customTipTextView);
        tipCustomEditText = (EditText) findViewById(R.id.customTipEditText);
        totalCustomEditText = (EditText) findViewById(R.id.customTotalEditText);

        billEditText.addTextChangedListener(billEditTextWatcher);

        SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putDouble(BILL_TOTAL, currentBillTotal);
        outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
    }

    // updates 10, 15 and 20 % tip EditTexts
    private void updateStandard(){

        // calculate bill total with a ten percent tip
        double tenPercentTip = currentBillTotal * .1;
        double tenPercentTotal = currentBillTotal + tenPercentTip;
        // set tip10EditText's text to tenPercentTip
        tip10EditText.setText(String.format(getString(R.string.two_dec_float), tenPercentTip));
        //set total10EditText's text to tenPercentTotal
        total10EditText.setText(String.format(getString(R.string.two_dec_float), tenPercentTotal));

        // calculate bill total with a fifteen percent tip
        double fifteenPercentTip = currentBillTotal * .15;
        double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;
        // set tip15EditText's text to fifteenPercentTip
        tip15EditText.setText(String.format(getString(R.string.two_dec_float), fifteenPercentTip));
        //set total15EditText's text to fifteenPercentTotal
        total15EditText.setText(String.format(getString(R.string.two_dec_float), fifteenPercentTotal));

        // calculate bill total with a twenty percent tip
        double twentyPercentTip = currentBillTotal * .20;
        double twentyPercentTotal = currentBillTotal + twentyPercentTip;
        // set tip20EditText's text to twentyPercentTip
        tip20EditText.setText(String.format(getString(R.string.two_dec_float), twentyPercentTip));
        //set total20EditText's text to twentyPercentTotal
        total20EditText.setText(String.format(getString(R.string.two_dec_float), twentyPercentTotal));

    }

    //updated the custom tip and total EditTexts
    private void updateCustom(){

        // sets customTipTextView's text to match the position of the SeekBar
        customTipTextView.setText(currentCustomPercent + getString(R.string.percent_sign));

        // calculate the custom tip amount
        double customTipAmount = currentBillTotal * currentCustomPercent * .01;

        // calculate the total bill including the custom tip
        double customTotalAmount = currentBillTotal + customTipAmount;

        // display the tip and total bill amounts
        tipCustomEditText.setText(String.format(getString(R.string.two_dec_float), customTipAmount));
        totalCustomEditText.setText(String.format(getString(R.string.two_dec_float), customTotalAmount));
    }

    // called when user changes the position of SeekBar
    private SeekBar.OnSeekBarChangeListener customSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    currentCustomPercent = seekBar.getProgress();
                    updateCustom();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    // event-handling object that responds to billEditText's events
    private TextWatcher billEditTextWatcher =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // convert billEditText's text to a double
            try{
                currentBillTotal = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e){
                currentBillTotal = 0.0; // default if an sxception occurs
            }

            updateStandard();
            updateCustom();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
