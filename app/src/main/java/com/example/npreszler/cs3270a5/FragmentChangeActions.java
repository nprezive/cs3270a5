package com.example.npreszler.cs3270a5;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChangeActions extends Fragment {

    FragChangeActionsListener mCallback;
    View rootView;
    Button btnStartOver, btnNewAmount;
    TextView txvCorrectCount;
    int correctCount = 0;

    public interface FragChangeActionsListener {
        void onStartOver();
        void onNewAmount();
    }

    public FragmentChangeActions() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (FragChangeActionsListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() +
                    " must implement FragChangeActionsListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        correctCount = sp.getInt("correctCount", 0);
        txvCorrectCount.setText(String.valueOf(correctCount));
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        sp.edit()
                .putInt("correctCount", correctCount)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_actions, container, false);

        // Pointers
        btnStartOver = (Button) rootView.findViewById(R.id.btnStartOver);
        btnNewAmount = (Button) rootView.findViewById(R.id.btnNewAmount);
        txvCorrectCount = (TextView) rootView.findViewById(R.id.txvCorrectCount);

        // Set listeners for buttons
        btnStartOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onStartOver();
            }
        });
        btnNewAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onNewAmount();
            }
        });

        return rootView;
    }

    public void updateCorrectCount() {
        txvCorrectCount.setText(String.valueOf(++correctCount));
    }

    public void zeroCorrectCount() {
        correctCount = 0;
        txvCorrectCount.setText(String.valueOf(correctCount));
    }

}
