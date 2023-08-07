/*
This takes the input for purchase price, down payment, and interest rate from the user to calculate
the total loan amount and monthly payments for a 10, 20, or 30 year term.

Created by: Lily Mekeel
Date: 04/07/2023
 */

package com.example.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text
import java.text.NumberFormat; // for currency formatting

public class MainActivity extends AppCompatActivity {

    // currency formatter object
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();

    //Variable declarations
    private double purchasePrice;
    private double downPayment;
    private double interestRate;
    double loanAmount;
    int loanTerm = 20;

    //TextView and Seekbar declarations
    private TextView loanAmountView; //loan amount (purchase price - down payment)
    private TextView loanTermView; //loan term next to seekbar
    private TextView monthlyPaymentView; //displays monthly payment

    /*
    MAIN METHOD
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set editText references
        EditText purchasePriceView = findViewById(R.id.purchasePriceEditText); //purchase price from user input
        purchasePriceView.addTextChangedListener(purchasePriceWatcher);

        EditText downPaymentView = findViewById(R.id.downPaymentEditText); //down payment from user input
        downPaymentView.addTextChangedListener(downPaymentWatcher);

        EditText interestRateView = findViewById(R.id.interestRateEditText); //interest rate from user input
        interestRateView.addTextChangedListener(interestRateWatcher);

        //set TextView references
        loanAmountView = findViewById(R.id.loanAmountResultView);
        loanTermView = findViewById(R.id.loanTermTextView);
        monthlyPaymentView= findViewById(R.id.paymentResultView);

        //set SeekBar reference
        //seekBar
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarListener);

    }


    /*
    METHODS FOR UPDATING LOAN AMOUNT AND MONTHLY PAYMENT
   */

    //This method updates the loan amount View
    private void updateLoanAmount(){
            loanAmount = purchasePrice - downPayment;
            loanAmountView.setText(currencyFormat.format(loanAmount)); //set loan amount View
    }

    //This method calculates and updates the monthly Payment View
    private void calculateMonthlyPayment(){
        double monthlyInterestRate = interestRate / 12; //monthly interest rate
        int loanTermInMonths = loanTerm * 12; //number of months in loan term

        double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths)); //Calculates monthly payment
        monthlyPaymentView.setText(currencyFormat.format(monthlyPayment)); //update monthly payment View
    }

     /*
    SEEK BAR
    */

    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        @SuppressLint("DefaultLocale")

        //Changes the loan term View as SeekBar progress changes and sets loanTerm variable
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            loanTerm = (progress + 1) * 10;
            loanTermView.setText(String.format("Loan Term %dyrs:", loanTerm));


            calculateMonthlyPayment();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    /*
    TEXT WATCHERS
    */

    //TEXT WATCHER FOR PURCHASE PRICE
    private final TextWatcher purchasePriceWatcher = new TextWatcher() {

        //Sets the purchasePrice variable to users input then calls the updateLoanAmount method
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //updates price variable, sets price to 0.0 if user inputs incorrect number format
            try{
                purchasePrice = Integer.parseInt(s.toString());
            }
            catch(NumberFormatException e){
                purchasePrice = 0.0;
            }
            updateLoanAmount(); //call to update loan Amount View
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    //TEXT WATCHER FOR DOWN PAYMENT
    private final TextWatcher downPaymentWatcher = new TextWatcher() {

        //updates down payment variable, sets value to 0.0 if user inputs incorrect number format
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           try{
               downPayment = Double.parseDouble(s.toString());
           }
           catch(NumberFormatException e){

               downPayment = 0.0;
           }
           updateLoanAmount(); //calls update loan amount method
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //TEXT WATCHER FOR INTEREST RATE
    private final TextWatcher interestRateWatcher = new TextWatcher() {

        //updates interest rate variable, sets value to 0.0 if user inputs incorrect number format
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                interestRate = Double.parseDouble(s.toString()) / 100.0; //divide by 100 for decimal rate
            }
            catch(NumberFormatException e){
                interestRate = 0.0;
            }
            calculateMonthlyPayment(); //calls calculate monthly payment method
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    
}