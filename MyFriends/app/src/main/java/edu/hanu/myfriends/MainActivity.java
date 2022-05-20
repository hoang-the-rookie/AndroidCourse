package edu.hanu.myfriends;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.myfriends.adapters.FriendAdapter;
import edu.hanu.myfriends.models.Constants;
import edu.hanu.myfriends.models.Friend;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnAdd;
    RecyclerView rvFriend;
    private List<Friend> friends;
    private FriendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //    dataset
        friends = new ArrayList<>();
        friends.add(new Friend("Hoang","hoang@gmail.com","123456789"));
        friends.add(new Friend("Ha","ha@gmail.com","126456781"));
        friends.add(new Friend("Thom","thom@gmail.com","823456781"));
        friends.add(new Friend("Huong","huong@gmail.com","123656781"));
        friends.add(new Friend("Phong","phong@gmail.com","123956781"));
        friends.add(new Friend("Huy","huy@gmail.com","123463781"));
        friends.add(new Friend("Tri","tri@gmail.com","123458781"));

//        get recycle view
        rvFriend = findViewById(R.id.rvMain);

//        set layout manager
        rvFriend.setLayoutManager(new LinearLayoutManager(MainActivity.this));

//        initial adapter
        adapter = new FriendAdapter(friends, MainActivity.this);
        rvFriend.setAdapter(adapter);


//        handle add new friend
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConfigFriendActivity.class);
            String title = getResources().getString(R.string.newFriend);
            intent.putExtra("TITLE", title);
            intent.putExtra("TYPE","new");

            startActivityForResult(intent, Constants.RC_NEW_FRIEND);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        new
        if(requestCode==Constants.RC_NEW_FRIEND && resultCode == RESULT_OK){

//            get data
            Friend friend =(Friend) data.getSerializableExtra("NEW_FRIEND");

            friends.add(friend);

//            modify adapter
            adapter.notifyItemInserted(friends.size() - 1);
            rvFriend.scrollToPosition(friends.size() - 1);

        }

//        edit
        if(requestCode==Constants.RC_EDIT_FRIEND && resultCode == RESULT_OK){
//            get data
            Friend friend =(Friend) data.getSerializableExtra("EDIT_FRIEND");
            int position = data.getIntExtra("POSITION",0);
            Log.d("POSITION", String.valueOf(position));
            Log.d("POSITION", friend.toString());
//            modify
            friends.set(position, friend);
            adapter.notifyItemChanged(position);
        }

    }
}