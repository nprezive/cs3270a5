package com.example.npreszler.cs3270a5;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetMaxChange extends Fragment {

    public interface FragmentSetMaxChangeListener {
        void onSetMaxChange(BigDecimal max);
    }

    FragmentSetMaxChangeListener mCallback;
    View rootView;
    Button btnOK;
    EditText edtChangeMax;

    public FragmentSetMaxChange() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            mCallback = (FragmentSetMaxChangeListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() +
                    " must implement FragmentSetMaxChangeListener");
        }

        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_max_change, container, false);

        edtChangeMax = rootView.findViewById(R.id.edtChangeMax);
        btnOK = rootView.findViewById(R.id.btnPositiveChangeMax);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtChangeMax.getText().toString();
                if(input.equals("")) {
                    Toast.makeText(getContext(),
                            R.string.max_amount_cant_be_blank,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mCallback.onSetMaxChange(new BigDecimal(input));
            }
        });

        return rootView;
    }

}
