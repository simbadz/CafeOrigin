package com.example.calculator.cafeorigin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.SaveRequest;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MakeOrderActivity extends AppCompatActivity {

    private static final String EXTRA_USER_NAME = "userName";

    private TextView textViewGreetings;
    private TextView textViewAdditivies;
    private RadioGroup radioGroupDrinks;
    private RadioButton radioButtonTea;
    private RadioButton radioButtonCoffee;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxLemon;

    private Spinner spinnerTea;
    private Spinner spinnerCoffee;
    private Button buttonMakeOrder;
    private String userName;
    private String drink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        initViews();
        setupUserName();
        radioGroupDrinks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                if (id == radioButtonTea.getId()) {
                    onUserChoseTea();

                } else if (id == radioButtonCoffee.getId()) {
                    onUserChoseCoffee();
                }
            }
        });

        radioButtonTea.setChecked(true);

        buttonMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserMadeOrder();

            }
        });


    }

    private void onUserMadeOrder() {
        ArrayList<String> additivies = new ArrayList<>();
        if (checkBoxSugar.isChecked()) {
            additivies.add(checkBoxSugar.getText().toString());
        }

        if (radioButtonTea.isChecked() && checkBoxLemon.isChecked()) {
            additivies.add(checkBoxLemon.getText().toString());
        }

        if (checkBoxMilk.isChecked()) {
            additivies.add(checkBoxMilk.getText().toString());
        }

        String drinkType = "";
        if (radioButtonTea.isChecked()) {
            drinkType = spinnerTea.getSelectedItem().toString();
        } else if (radioButtonCoffee.isChecked()) {
            drinkType = spinnerCoffee.getSelectedItem().toString();
        }

        Intent intent = OrderDetailActivity.newIntent(
                this,
                userName,
                drink,
                drinkType,
                additivies.toString());

        startActivity(intent);



    }

    private void onUserChoseTea() {
        drink = getString(R.string.tea);
        String additiviesLabel = getString(R.string.additivies, drink);
        textViewAdditivies.setText(additiviesLabel);
        checkBoxLemon.setVisibility(View.VISIBLE);
        spinnerTea.setVisibility(View.VISIBLE);
        spinnerCoffee.setVisibility(View.INVISIBLE);

    }

    private void onUserChoseCoffee() {
        drink = getString(R.string.coffee);
        String additiviesLabel = getString(R.string.additivies, drink);
        textViewAdditivies.setText(additiviesLabel);
        checkBoxLemon.setVisibility(View.INVISIBLE);
        spinnerTea.setVisibility(View.INVISIBLE);
        spinnerCoffee.setVisibility(View.VISIBLE);

    }

    private void initViews() {
        textViewGreetings = findViewById(R.id.textViewGreetings);
        textViewAdditivies = findViewById(R.id.textViewAdditivies);
        radioGroupDrinks = findViewById(R.id.radioGroupDrinks);
        radioButtonTea = findViewById(R.id.radioButtonTea);
        radioButtonCoffee = findViewById(R.id.radioButtonCoffee);
        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);

        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        buttonMakeOrder = findViewById(R.id.buttonMakeOrder);

    }

    private void setupUserName() {
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String greetings_second = getString(R.string.greetings_second);
        String result = String.format(greetings_second, userName);
        textViewGreetings.setText(result);
    }


    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, MakeOrderActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }
}