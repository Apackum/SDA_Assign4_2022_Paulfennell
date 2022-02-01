package com.example.SDA_ASSIGNMENT4_PAULFENNELL;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



public class Settings extends Fragment {
    EditText userId, userEmail, userBorId;
    Button saveDet,resetData;
    CheckBox checkBox;
    String mUser,mEmail,mBorId;
    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        userId = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.email);
        userBorId = view.findViewById(R.id.borrowerID);
        saveDet = view.findViewById(R.id.button);
        resetData = view.findViewById(R.id.button2);
        checkBox = view.findViewById(R.id.checkBox);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();

        String st1 = prefs.getString("I","");
        String st2 = prefs.getString("E","");
        String st3= prefs.getString("B","");

        userId.setText(st1);
        userEmail.setText(st2);
        userBorId.setText(st3);

        saveDet.setOnClickListener(v -> {
            if(verifyEmailAddress()){
                if(checkBox.isChecked()) {
                    //Save the UserId
                    mUser = userId.getText().toString();
                    mEmail = userEmail.getText().toString();
                    mBorId = userBorId.getText().toString();
                    editor.putString("I", mUser);
                    editor.putString("E", mEmail);
                    editor.putString("B", mBorId);
                    editor.apply();
                    Log.d("Test 1", "onClick: ");
                    Toast.makeText(getContext(), "Info saved", Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("I", "");
                    editor.putString("E", "");
                    editor.putString("B", "");
                    Log.d("Test 2", "onClick: ");
                    Toast.makeText(getContext(), "Information will not be saved ", Toast.LENGTH_SHORT).show();
                }
            }else {
                verifyEmailAddress();
            }
        });
        resetData.setOnClickListener(v -> {
            Log.d("Test 3", "onClick: Cleared");
            userEmail.getText().clear();
            userBorId.getText().clear();
            userId.getText().clear();
            prefs.edit().clear().apply();
        });

        return view;
    }
    private boolean verifyEmailAddress(){
        String emailInput = userEmail.getText().toString();
        String nameInput = userId.getText().toString();
        String borInput = userBorId.getText().toString();


        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches() && !nameInput.isEmpty() && !borInput.isEmpty()){
            Toast.makeText(getContext(),"Email Verified",Toast.LENGTH_SHORT).show();
            Log.d("Test 4", "verifyEmailAddress: Passed ");
            return true;
        }else{
            Toast.makeText(getContext(),"Please enter a valid email, don't leave fields empty",Toast.LENGTH_SHORT).show();
            Log.d("Test 5", "verifyEmailAddress: failure ");
            return false;
        }
    }
}

