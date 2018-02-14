package com.example.npreszler.cs3270a5;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements
        FragmentChangeResults.FragChangeResultsListener,
        FragmentChangeButtons.FragChangeButtonsListener,
        FragmentChangeActions.FragChangeActionsListener,
        DialogFragListener,
        FragmentSetMaxChange.FragmentSetMaxChangeListener{

    FragmentManager fm;
    FragmentChangeResults fragChangeResults;
    FragmentChangeButtons fragChangeButtons;
    FragmentChangeActions fragChangeActions;
    BigDecimal maxChange = new BigDecimal(100);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_zero_correct_count:
                zeroCorrectCount();
                return true;
            case R.id.action_set_change_max:
                loadFragSetChangeMax();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onChangeTotalEqualsGoal() {
        if(fragChangeResults == null || fragChangeActions == null)
            return;

        fragChangeResults.stopTimer();
        fragChangeActions.updateCorrectCount();
        FragmentWinDialog dialog = new FragmentWinDialog();
        dialog.show(fm, "fragWinDialog");
    }

    @Override
    public void onChangeTotalExceedsGoal() {
        if(fragChangeResults == null)
            return;

        fragChangeResults.stopTimer();
        FragmentChangeExceedsGoal dialog = new FragmentChangeExceedsGoal();
        dialog.show(fm, "fragChangeExceedsGoal");
    }

    @Override
    public void onNoTimeRemaining() {
        if(fragChangeResults == null)
            return;

        fragChangeResults.stopTimer();
        FragmentTooLong dialog = new FragmentTooLong();
        dialog.show(fm, "fragmentTooLong");
    }

    @Override
    public void onButtonAmountClick(BigDecimal amount) {
        if(fragChangeResults == null)
            return;

        fragChangeResults.updateCurrentChange(amount);
    }

    @Override
    public void onStartOver() {
        if(fragChangeResults == null)
            return;

        // Reset running total
        fragChangeResults.resetCurrentChange();

        // Reset time
        fragChangeResults.resetTimer(30000);
    }

    @Override
    public void onNewAmount() {
        if(fragChangeResults == null)
            return;

        // Reset maxGoal
        fragChangeResults.resetGoal(maxChange);

        // Reset running total
        fragChangeResults.resetCurrentChange();

        // Reset time
        fragChangeResults.resetTimer(30000);
    }

    public void zeroCorrectCount() {
        if(fragChangeActions == null)
            return;

        fragChangeActions.zeroCorrectCount();
    }

    @Override
    public void resetGame() {
        if(fragChangeResults == null)
            return;

        fragChangeResults.resetCurrentChange();
        fragChangeResults.resetGoal(maxChange);
        fragChangeResults.resetTimer(30000);
    }

    public void loadFragSetChangeMax() {
        if(fragChangeResults == null || fragChangeButtons == null || fragChangeActions == null)
            return;

        fragChangeResults.stopTimer();
        fm.beginTransaction()
                .replace(R.id.frameChangeResults,
                        new FragmentSetMaxChange(), "fragSetMaxChange")
                .hide(fragChangeButtons)
                .hide(fragChangeActions)
                .addToBackStack("fragSetMaxChange")
                .commit();
    }

    @Override
    public void onSetMaxChange(BigDecimal max) {
        if(fragChangeResults == null)
            return;

        maxChange = max;
        fm.beginTransaction()
                .replace(R.id.frameChangeResults, fragChangeResults)
                .show(fragChangeButtons)
                .show(fragChangeActions)
                .commit();
        fragChangeResults.resetGoal(max);
    }
}
