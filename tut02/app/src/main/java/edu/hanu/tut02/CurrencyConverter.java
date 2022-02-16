package edu.hanu.tut02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CurrencyConverter extends Fragment {

    //declare
    EditText usdInp,vndInp;
    Button convertBtn;
    float vnd,usd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_currency_converter, container, false);

//        catch
        convertBtn = view.findViewById(R.id.convertBtn);
        usdInp = view.findViewById(R.id.valueAmerican);
        vndInp = view.findViewById(R.id.valueVietNamese);

//        handle click
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Check editText

                if (isEmpty(usdInp) && isEmpty(vndInp)){
                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_LONG).show();
                }else if(!isEmpty(usdInp) && !isEmpty(vndInp)){
                    if(usdInp.hasFocus()){
                        exchange(usdInp,vndInp,"usd");
                    } else{
                        exchange(vndInp,usdInp,"vnd");
                    }
                } else {
                    if(isEmpty(usdInp)){
                        exchange(vndInp,usdInp,"vnd");
                    } else{
                        exchange(usdInp,vndInp,"usd");
                    }
                }
            }
        });

        return view;
    }

    private void exchange(EditText unit1, EditText unit2, String type){
        float result = Float.parseFloat(unit1.getText().toString());
        if(type.equals("usd")){
            Toast.makeText(getActivity(), "USD -> VND", Toast.LENGTH_LONG).show();
            result *= 23000;
        } else{
            Toast.makeText(getActivity(), "VND -> USD", Toast.LENGTH_LONG).show();
            result /= 23000;
        }
        unit2.setText(Float.toString(result));
    }

    private boolean isEmpty(EditText edittext){
        return edittext.getText().toString().trim().length() == 0;
    }
}