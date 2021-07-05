package com.moringaschool.consult.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.consult.R;
import com.moringaschool.consult.ui.Model.Groups;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyGroups extends RecyclerView.Adapter<MyGroups.GroupViewHolder>{

    List<String> list_of_groups;

    public MyGroups(List<String> list_of_groups) {
        this.list_of_groups = list_of_groups;
    }

    @Override
    public MyGroups.GroupViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groups, parent, false);
        return new MyGroups.GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyGroups.GroupViewHolder holder, int position) {
        holder.mName.setText(list_of_groups.get(position).toString());
    }

    @Override
    public int getItemCount() {
        if (list_of_groups != null) {
            return this.list_of_groups.size();
        } else {
            return 0;
        }
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView mName;

        public GroupViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.groupNameD);

        }

    }

}
