package com.example.npreszler.cs3270a5;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangeResults extends Fragment {


    FragChangeResultsListener mCallback;
    View rootView;
    TextView txvChangeGoal, txvCurrentChange, txvTimer;
    CountDownTimer countDownTimer;
    BigDecimal currentChange = new BigDecimal(0);
    BigDecimal maxGoal = new BigDecimal(100);
    long tick;


    public interface FragChangeResultsListener {
        void onChangeTotalEqualsGoal();
        void onChangeTotalExceedsGoal();
        void onNoTimeRemaining();
    }


    public FragmentChangeResults() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (FragChangeResultsListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() +
                    " must implement FragChangeResultsListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        txvChangeGoal.setText(sp.getString("changeGoal", numberFormat.format(
                (new BigDecimal(100)).multiply(new BigDecimal(Math.random())))));
        txvCurrentChange.setText(sp.getString("currentChange", numberFormat.format(0)));
        txvTimer.setText(sp.getString("timer", getString(R.string.x_30)));
        currentChange = new BigDecimal(sp.getString("bdCurrentChange", getString(R.string._0)));
        maxGoal = new BigDecimal(sp.getString("bdMaxGoal", getString(R.string._0)));
        tick = sp.getLong("tick", 30000);

        resetTimer(tick);
    }

    @Override
    public void onPause() {
        super.onPause();

        countDownTimer.cancel();

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        sp.edit()
                .putString("changeGoal", txvChangeGoal.getText().toString())
                .putString("currentChange", txvCurrentChange.getText().toString())
                .putString("timer", txvTimer.getText().toString())
                .putString("bdCurrentChange", currentChange.toString())
                .putString("bdMaxGoal", maxGoal.toString())
                .putLong("tick", tick)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_results, container, false);

        txvChangeGoal = (TextView) rootView.findViewById(R.id.txvChangeGoal);
        txvCurrentChange = (TextView) rootView.findViewById(R.id.txvChangeTotal);
        txvTimer = (TextView) rootView.findViewById(R.id.txvCountDownTimer);

        txvCurrentChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BigDecimal current, goal;

                try {
                    current = new BigDecimal(charSequence.toString().substring(1));
                    goal = new BigDecimal(txvChangeGoal.getText().toString().substring(1));
                }
                catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
                    return;
                }

                if(current.compareTo(goal) == 0) {
                    mCallback.onChangeTotalEqualsGoal();
                }
                else if(current.compareTo(goal) > 0) {
                    mCallback.onChangeTotalExceedsGoal();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        resetTimer(30000);

        resetGoal(maxGoal);

        return rootView;
    }

    public void resetTimer(long input) {
        if (countDownTimer != null)
            countDownTimer.cancel();

        countDownTimer = new CountDownTimer(input, 1000) {
            @Override
            public void onTick(long l) {
                tick = l;
                txvTimer.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                mCallback.onNoTimeRemaining();
            }
        }.start();
    }

    public void resetGoal(BigDecimal max) {
        BigDecimal min = new BigDecimal(0);
        BigDecimal range = max.subtract(min);
        BigDecimal result = min.add(range.multiply(new BigDecimal(Math.random())));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        txvChangeGoal.setText(numberFormat.format(result));
    }

    public void resetCurrentChange() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        txvCurrentChange.setText(numberFormat.format(0));
        currentChange = new BigDecimal(0);
    }

    public void updateCurrentChange(BigDecimal amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        currentChange = currentChange.add(amount);
        txvCurrentChange.setText(numberFormat.format(currentChange));
    }

    public void stopTimer() {
        countDownTimer.cancel();
    }

}
