package edu.hanu.myfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.hanu.myfriends.models.Constants;
import edu.hanu.myfriends.models.Friend;

public class ConfigFriendActivity extends AppCompatActivity {

    //    define ref
    String title, type, name, phone, mail;
    TextView tvTitle;
    EditText inpName, inpPhone, inpMail;
    ImageButton btnCheck, btnCancel;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_friend);

//        get ref
        tvTitle = findViewById(R.id.title);
        btnCheck = findViewById(R.id.btnCheck);
        btnCancel = findViewById(R.id.btnCancel);
        inpName = findViewById(R.id.inpName);
        inpPhone = findViewById(R.id.inpPhone);
        inpMail = findViewById(R.id.inpMail);


//        get parsing data
        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        type = intent.getStringExtra("TYPE");

//        set title
        tvTitle.setText(title);

//        check type
        if (type.equals("new")) {
//            cancel
            btnCancel.setOnClickListener(view -> {
//                confirm
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Confirm")
                        .setMessage("Are you sure to cancel adding new friend ?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            });

//            check
            btnCheck.setOnClickListener(view -> {
//                get data
                name = inpName.getText().toString();
                phone = inpPhone.getText().toString();
                mail = inpMail.getText().toString();

//                set friend object
                Friend friend = new Friend(name, mail, phone);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("NEW_FRIEND", friend);
                setResult(RESULT_OK, resultIntent);
                finish();
            });

        } else {
//            get friend
            Friend friend = (Friend) intent.getSerializableExtra("FRIEND");
            int position = intent.getIntExtra("POSITION",0);

//            set old data
            inpName.setText(friend.getName());
            inpPhone.setText(friend.getPhoneNo());
            inpMail.setText(friend.getEmail());

            //        handle button TODO: detect edittext change
            btnCancel.setVisibility(View.INVISIBLE);

//            check
            btnCheck.setOnClickListener(view -> {
//                get data
                name = inpName.getText().toString();
                phone = inpPhone.getText().toString();
                mail = inpMail.getText().toString();

//                check if have any change
                if (!name.equals(friend.getName()) || !phone.equals(friend.getPhoneNo()) || !mail.equals(friend.getEmail())) {
//                    set friend object
                    Friend newFriend = new Friend(name, mail, phone);

                    Intent resultIntent = new Intent(getApplicationContext(),MainActivity.class);
                    resultIntent.putExtra("POSITION", position);
                    resultIntent.putExtra("EDIT_FRIEND", newFriend);
                    setResult(RESULT_OK, resultIntent);
                }

//                return by default
                finish();
            });
        }


    }
}