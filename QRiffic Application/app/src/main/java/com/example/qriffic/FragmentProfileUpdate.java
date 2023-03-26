package com.example.qriffic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.qriffic.databinding.ProfileCreateBinding;

import java.util.ArrayList;

public class FragmentProfileUpdate extends Fragment {
    private ProfileCreateBinding binding;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private TextView textViewUsernameWarning;
    private UsernamePersistent usernamePersistent;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = ProfileCreateBinding.inflate(inflater, container, false);
        usernamePersistent = new UsernamePersistent(getActivity().getApplicationContext());

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextUsername = view.findViewById(R.id.edit_username);
        editTextUsername.setEnabled(false);
        editTextEmail = view.findViewById(R.id.edit_email);
        editTextPhone = view.findViewById(R.id.edit_phone);
        textViewUsernameWarning = view.findViewById(R.id.username_warning);

        PlayerProfile profile = new PlayerProfile();
        DBAccessor dba = new DBAccessor();
        profile.addListener(new fetchListener() {
            @Override
            public void onFetchComplete() {
                editTextUsername.setText(profile.getUsername());
                editTextEmail.setText(profile.getEmail());
                editTextPhone.setText(profile.getPhoneNum());
            }

            @Override
            public void onFetchFailure() {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        dba.getPlayer(profile, usernamePersistent.fetchUsername());

        binding.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //write to file, and then navigate back to QRDex

                if (editTextUsername.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    return;
                }

                profile.setUsername(editTextUsername.getText().toString());
                profile.setEmail(editTextEmail.getText().toString());
                profile.setPhoneNum(editTextPhone.getText().toString());
                dba.updateContactInfo(profile);

                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}