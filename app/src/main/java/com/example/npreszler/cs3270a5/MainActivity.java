package com.example.npreszler.cs3270a5;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements
        FragmentChangeResults.FragChangeResultsListener,
        FragmentChangeButtons.FragChangeButtonsListener,
        FragmentChangeActions.FragChangeActionsListener{

    FragmentManager fm;
    FragmentChangeResults fragChangeResults;
    FragmentChangeButtons fragChangeButtons;
    FragmentChangeActions fragChangeActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate fm and load fragments
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frameChangeResults, new FragmentChangeResults(),
                        "fragChangeResults")
                .replace(R.id.frameChangeButtons, new FragmentChangeButtons(),
                        "fragChangeButtons")
                .replace(R.id.frameChangeActions, new FragmentChangeActions(),
                        "fragChangeActions")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fragChangeResults = (FragmentChangeResults) fm.findFragmentByTag("fragChangeResults");
        fragChangeButtons = (FragmentChangeButtons) fm.findFragmentByTag("fragChangeButtons");
        fragChangeActions = (FragmentChangeActions) fm.findFragmentByTag("fragChangeActions");
    }

    @Override
    public void onChangeTotalEqualsGoal() {
        // TODO show winning dialog

        fragChangeActions.updateCorrectCount();
    }

    @Override
    public void onChangeTotalExceedsGoal() {
        Log.d("test", "change total exceeds goal");
    }

    @Override
    public void onNoTimeRemaining() {
        Log.d("test", "no time remaining");
    }

    @Override
    public void onButtonAmountClick(BigDecimal amount) {
        fragChangeResults.updateCurrentChange(amount);
    }

    @Override
    public void onStartOver() {
        // Reset running total
        fragChangeResults.resetCurrentChange();

        // Reset time
        fragChangeResults.resetTimer();
    }

    @Override
    public void onNewAmount() {
        // Reset goal
        fragChangeResults.resetGoal(new BigDecimal(100));

        // Reset running total
        fragChangeResults.resetCurrentChange();

        // Reset time
        fragChangeResults.resetTimer();
    }
}
