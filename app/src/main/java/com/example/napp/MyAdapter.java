package com.example.napp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<ListItem> items;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView head,desc,dept;
        private ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.textviewhead);
            desc=itemView.findViewById(R.id.textviewdesc);
            dept=itemView.findViewById(R.id.department);
            image=itemView.findViewById(R.id.imageView);
        }
    }

    public MyAdapter(List<ListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ListItem listItem = items.get(i);
        myViewHolder.head.setText(listItem.getImageTitle());
        myViewHolder.desc.setText(listItem.getImageDescription());
        myViewHolder.dept.setText("Department: "+listItem.getSelectedDept());
        //Loading image from Glide Library
        Glide.with(context).load(listItem.getImageURL()).into(myViewHolder.image);



    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
