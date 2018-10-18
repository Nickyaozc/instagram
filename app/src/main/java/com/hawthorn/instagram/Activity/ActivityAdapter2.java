package com.hawthorn.instagram.Activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawthorn.instagram.R;

public class ActivityAdapter2 extends RecyclerView.Adapter{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder ) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return ActivityData.title2.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView profilePhoto;
        private TextView activityText;
        private ImageView post;
        public ListViewHolder(View itemView){
            super(itemView);
            profilePhoto = (ImageView) itemView.findViewById(R.id.profilePhoto);
            activityText = (TextView) itemView.findViewById(R.id.listText);
            post = (ImageView) itemView.findViewById(R.id.post);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            profilePhoto.setImageResource(ActivityData.profilePhoto[position]);
            activityText.setText(ActivityData.title2[position]);
            post.setImageResource(ActivityData.post[position]);

        }

        public void onClick(View view){}


    }

}
