package com.example.npreszler.cs3270a5;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangeButtons extends Fragment {

    View rootView;

    public FragmentChangeButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_buttons, container, false);

        // Use getCurrencyInstance to set button text with locale-specific money symbols
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        ((Button)rootView.findViewById(R.id.btn50dollar)).setText(numberFormat.format(50));
        ((Button)rootView.findViewById(R.id.btn20dollar)).setText(numberFormat.format(20));
        ((Button)rootView.findViewById(R.id.btn10dollar)).setText(numberFormat.format(10));
        ((Button)rootView.findViewById(R.id.btn5dollar)).setText(numberFormat.format(5));
        ((Button)rootView.findViewById(R.id.btn1dollar)).setText(numberFormat.format(1));
        ((Button)rootView.findViewById(R.id.btn50cent)).setText(numberFormat.format(.50));
        ((Button)rootView.findViewById(R.id.btn25cent)).setText(numberFormat.format(.25));
        ((Button)rootView.findViewById(R.id.btn10cent)).setText(numberFormat.format(.10));
        ((Button)rootView.findViewById(R.id.btn5cent)).setText(numberFormat.format(.05));
        ((Button)rootView.findViewById(R.id.btn1cent)).setText(numberFormat.format(.01));

        return rootView;
    }

}
