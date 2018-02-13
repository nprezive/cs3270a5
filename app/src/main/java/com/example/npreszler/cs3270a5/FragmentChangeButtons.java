package com.example.npreszler.cs3270a5;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.math.BigDecimal;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangeButtons extends Fragment {

    FragChangeButtonsListener mCallback;
    View rootView;
    Button btn50, btn20, btn10, btn5, btn1, btn50cent, btn25cent, btn10cent, btn5cent, btn1cent;
    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mCallback.onButtonAmountClick(
                    new BigDecimal(((Button)view).getText().toString().substring(1)));
        }
    };

    public interface FragChangeButtonsListener {
        void onButtonAmountClick(BigDecimal amount);
    }

    public FragmentChangeButtons() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (FragChangeButtonsListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() +
                    " must implement ClassCastException.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_buttons, container, false);

        // Assign pointers to buttons
        btn50 = ((Button)rootView.findViewById(R.id.btn50dollar));
        btn20 = ((Button)rootView.findViewById(R.id.btn20dollar));
        btn10 = ((Button)rootView.findViewById(R.id.btn10dollar));
        btn5 = ((Button)rootView.findViewById(R.id.btn5dollar));
        btn1 = ((Button)rootView.findViewById(R.id.btn1dollar));
        btn50cent = ((Button)rootView.findViewById(R.id.btn50cent));
        btn25cent = ((Button)rootView.findViewById(R.id.btn25cent));
        btn10cent = ((Button)rootView.findViewById(R.id.btn10cent));
        btn5cent = ((Button)rootView.findViewById(R.id.btn5cent));
        btn1cent = ((Button)rootView.findViewById(R.id.btn1cent));

        // Use getCurrencyInstance to set button text with locale-specific money symbols
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        btn50.setText(numberFormat.format(50));
        btn20.setText(numberFormat.format(20));
        btn10.setText(numberFormat.format(10));
        btn5.setText(numberFormat.format(5));
        btn1.setText(numberFormat.format(1));
        btn50cent.setText(numberFormat.format(.50));
        btn25cent.setText(numberFormat.format(.25));
        btn10cent.setText(numberFormat.format(.10));
        btn5cent.setText(numberFormat.format(.05));
        btn1cent.setText(numberFormat.format(.01));

        // Set button click listeners
        btn50.setOnClickListener(btnClickListener);
        btn20.setOnClickListener(btnClickListener);
        btn10.setOnClickListener(btnClickListener);
        btn5.setOnClickListener(btnClickListener);
        btn1.setOnClickListener(btnClickListener);
        btn50cent.setOnClickListener(btnClickListener);
        btn25cent.setOnClickListener(btnClickListener);
        btn10cent.setOnClickListener(btnClickListener);
        btn5cent.setOnClickListener(btnClickListener);
        btn1cent.setOnClickListener(btnClickListener);

        return rootView;
    }

}
