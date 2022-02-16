package edu.hanu.tut02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeBack extends Fragment {

    Button loginBtn;
    EditText usernameInp, passwordInp;
    String username,password,message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_back, container, false);

//        get
        loginBtn = view.findViewById(R.id.loginBtn);
        usernameInp = view.findViewById(R.id.username);
        passwordInp = view.findViewById(R.id.password);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameInp.getText().toString();
                password = passwordInp.getText().toString();

//                check username & password
                if (username.equals("admin") && password.equals("admin")){
                    message = "Welcome back!";
                } else message = "Incorrect username or password!";

//                display message
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}