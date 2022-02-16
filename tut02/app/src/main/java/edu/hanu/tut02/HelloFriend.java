package edu.hanu.tut02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HelloFriend extends Fragment {

    Button submitBtn;
    EditText inputName;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_friend, container, false);

//        catch
        submitBtn = view.findViewById(R.id.submit);
        inputName = view.findViewById(R.id.name);

//      handle click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = inputName.getText().toString();
                Toast.makeText(getActivity(), "Hello " + name, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}