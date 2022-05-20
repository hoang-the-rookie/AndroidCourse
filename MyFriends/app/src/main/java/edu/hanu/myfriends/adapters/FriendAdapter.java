package edu.hanu.myfriends.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.hanu.myfriends.ConfigFriendActivity;
import edu.hanu.myfriends.MainActivity;
import edu.hanu.myfriends.R;
import edu.hanu.myfriends.models.Constants;
import edu.hanu.myfriends.models.Friend;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {

//    init ref
    TextView name;
    ImageButton btnDelete, btnMessage, btnMail, btnPhone;
    Context context;
    Intent intent;

//    view holder
    protected class FriendHolder extends RecyclerView.ViewHolder{
        public FriendHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Friend friend, int position){
//            find textview
            name = itemView.findViewById(R.id.friendName);
//            set text
            name.setText(friend.getName());

//            get ref
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnMessage = itemView.findViewById(R.id.btnMessage);
            btnMail = itemView.findViewById(R.id.btnMail);
            btnPhone = itemView.findViewById(R.id.btnPhone);

//           handle detail and edit
            itemView.setOnClickListener(view -> {
                intent = new Intent(itemView.getContext(), ConfigFriendActivity.class);
                String title = itemView.getResources().getString(R.string.editFriend);
                intent.putExtra("TITLE", title);
                intent.putExtra("TYPE","edit");
                intent.putExtra("FRIEND", friend);
                intent.putExtra("POSITION", position);

                ((Activity) context).startActivityForResult(intent, Constants.RC_EDIT_FRIEND);
            });

//           handle delete
            btnDelete.setOnClickListener(view -> {
//                show confirm dialog
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Confirm")
                        .setMessage("Do you want to delete \"" + friend.getName() + "\" ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                friends.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            });

//           handle activities
            btnPhone.setOnClickListener(view -> onClickActivities(view, friend));
            btnMail.setOnClickListener(view -> onClickActivities(view, friend));
            btnMessage.setOnClickListener(view -> onClickActivities(view, friend));

        }

        public void onClickActivities(View view, Friend friend){
             switch (view.getId()){
                 case R.id.btnPhone:
                     intent = new Intent(Intent.ACTION_DIAL);
                     intent.setData(Uri.parse("tel:" + friend.getPhoneNo()));
                     break;
                 case R.id.btnMail:
                     intent = new Intent(Intent.ACTION_SENDTO);
                     intent.setData(Uri.parse("mailto:" + Uri.encode(friend.getEmail())));
                     break;
                 case R.id.btnMessage:
                     intent = new Intent(Intent.ACTION_SENDTO);
                     intent.setData(Uri.parse("smsto:" + friend.getPhoneNo()));
                     break;
                 default:
                     break;
             }
            context.startActivity(intent);
        }
    }

//    constructor
    public FriendAdapter(List<Friend> friends, Context context) {
        this.friends = friends;
        this.context = context;
    }

    //    dataset
    private List<Friend> friends;

//    manage view
    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//get context
        Context context = parent.getContext();

//        inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView =  inflater.inflate(R.layout.item_friend, parent, false);

        return new FriendHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        //get data item at position
        Friend friend = friends.get(position);

        //bind data to view
        holder.bind(friend, position);

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }




}
