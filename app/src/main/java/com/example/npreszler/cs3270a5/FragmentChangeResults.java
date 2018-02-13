package com.example.npreszler.cs3270a5;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

        resetTimer();

        resetGoal(new BigDecimal(100));

        return rootView;
    }

    public void resetTimer() {
        if (countDownTimer != null)
            countDownTimer.cancel();

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
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
}
