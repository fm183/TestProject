package com.example.testproject.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.R;
import com.example.testproject.TestActivity;
import com.example.testproject.bean.User;
import com.example.testproject.viewholder.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> userList;

    public UserListAdapter(List<User> userList){
        this.userList = new ArrayList<>();
        this.userList.addAll(userList);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), TestActivity.class));
                user.setName("click");
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }
}
