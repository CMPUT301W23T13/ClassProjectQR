package com.example.qriffic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTempAddQr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTempAddQr extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTempAddQr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTempAddQr.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTempAddQr newInstance(String param1, String param2) {
        FragmentTempAddQr fragment = new FragmentTempAddQr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temp_add_qr, container, false);

        // get reference to the button, EditText, and TextView
        Button addQR = view.findViewById(R.id.button_add_qr);
        EditText qrCode = view.findViewById(R.id.editText_enter_qr);
        TextView temp = view.findViewById(R.id.textView_temp);

        // create a HashMap for each "layer" of the name of the QR code
        // create a java List of 6 random names


        List<String> names = Arrays.asList("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p");

        // when the button is clicked, the contents of the qrCode EditText is displayed in the temp TextView
        addQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the EditText is empty, prompt user to enter something
                if (qrCode.getText().toString().isEmpty()) {
                    temp.setText("Please enter a QR code");
                }
                // otherwise, display the hash value of whatever they entered
                else {
                    QRCode tempQR;
                    tempQR = new QRCode(qrCode.getText().toString(), "Matlock");
                    String hash = tempQR.getIdHash();
                    // score and name will not be done here in final product, this is just for example
                    temp.setText("hash: " + hash +
                                "\nname: " + names.get(0) + names.get(1) + names.get(2) + names.get(3) + names.get(4) + names.get(5) +
                                "\nscore: " + tempQR.getScore());

                }
            }
        });

        return view;
    }
}