package com.moringaschool.consult.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.moringaschool.consult.R;
import com.moringaschool.consult.ui.Dashboard;
import com.moringaschool.consult.ui.Model.Chatslist;
import com.moringaschool.consult.ui.Model.Users;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;

public class MyNetwork extends RecyclerView.Adapter<MyNetwork.NetworkViewHolder> {

    List<Users> usersList;

    public MyNetwork(List<Users> users) {
        this.usersList = users;
    }

    @Override
    public NetworkViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_network, parent, false);
        return new NetworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNetwork.NetworkViewHolder holder, int position) {
        holder.mName.setText(usersList.get(position).getUsername());
        holder.mProfession.setText(usersList.get(position).getProfession());
        if(usersList.get(position).getImageURL() != "") {
            Picasso.get().load(usersList.get(position).getImageURL()).into(holder.mImage);
        }

    }

    @Override
    public int getItemCount() {
        if (usersList != null) {
            return this.usersList.size();
        } else {
            return 0;
        }
    }

    public class NetworkViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mProfession;
        ImageView mImage;

        public NetworkViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.userName);
            mProfession = view.findViewById(R.id.userProfession);
            mImage = view.findViewById(R.id.userImage);

        }

    }
}

//    TextView mName;
//    TextView mProfession;
//    ImageView mImage;
//
//    public MyNetwork(@NonNull View itemView) {
//        super(itemView);
//    }
//
//    public void setItem(Dashboard dashboard,  String username, String imageURL){
//        mImage = itemView.findViewById(R.id.userImage);
//        mName = itemView.findViewById(R.id.userName);
//        mProfession = itemView.findViewById(R.id.userProfession);
//
//        Picasso.get().load(imageURL).into(mImage);
//        mName.setText(username);
//    }


//public class MyNetwork extends FirebaseRecyclerAdapter<MyNetwork.NetworkViewHolder> {
//
//    List<Chatslist> networkList;
//    private Context mContext;
//    List<Users> mUsers;
//    UserAdapter mAdapter;
//    FirebaseUser firebaseUser;
//
//    @Override
//    public MyNetwork.NetworkViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_network, parent, false);
//        return new NetworkViewHolder(view);
//    }
//
//    @Override
//    protected void populateViewHolder(MyNetwork.NetworkViewHolder viewHolder, Object o, int i) {
//
//        viewHolder.mName.setText(mUsers.get(i).getUsername());
//        viewHolder.mProfession.setText(mUsers.get(i).getStatus());
//        if(mUsers.get(i).getImageURL() != ""){
//            Picasso.get().load(mUsers.get(i).getImageURL()).into(viewHolder.mImage));
//        }
//    }

//    public class NetworkViewHolder extends RecyclerView.ViewHolder {
//
//        TextView mName;
//        TextView mProfession;
//        ImageView mImage;
//
//
//        public NetworkViewHolder(View view) {
//            super(view);
//            mName = view.findViewById(R.id.userName);
//            mProfession = view.findViewById(R.id.userProfession);
//            mImage = view.findViewById(R.id.userImage);
//
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
//}
