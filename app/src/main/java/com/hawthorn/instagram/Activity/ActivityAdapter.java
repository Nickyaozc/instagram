package com.hawthorn.instagram.Activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawthorn.instagram.R;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter{
    private ArrayList dataset;
    public ActivityAdapter(ArrayList dataset){
        this.dataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false);
        return new ListViewHolder(view, this.dataset);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder ) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView profilePhoto;
        private TextView activityText;
        private ImageView post;
        private ArrayList dataset;
        public ListViewHolder(View itemView, ArrayList dataset){
            super(itemView);
            profilePhoto = (ImageView) itemView.findViewById(R.id.profilePhoto);
            activityText = (TextView) itemView.findViewById(R.id.listText);
            post = (ImageView) itemView.findViewById(R.id.post);
            this.dataset = dataset;
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            profilePhoto.setImageResource(ActivityData.profilePhoto[position]);
            activityText.setText(this.dataset.toArray()[position].toString());
            post.setImageResource(ActivityData.post[position]);

        }

        public void onClick(View view){}


    }


}
