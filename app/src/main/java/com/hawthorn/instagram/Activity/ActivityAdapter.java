package com.hawthorn.instagram.Activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawthorn.instagram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter{
    private ArrayList<String> photoList1; // For photo 1.
    private ArrayList<String> contentList; // For the activity content.
    private ArrayList<String> photoList2; // For photo 2.
    public ActivityAdapter(ArrayList<String> photoList1, ArrayList<String> contentList, ArrayList<String> photoList2){
        this.photoList1 = photoList1;
        this.contentList = contentList;
        this.photoList2 = photoList2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false);
        return new ListViewHolder(view, this.photoList1, this.contentList, this.photoList2);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder ) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return this.contentList.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView profilePhoto;
        private TextView activityText;
        private ImageView post;
        private ArrayList<String> photoList1; // For photo 1.
        private ArrayList<String> contentList; // For the activity content.
        private ArrayList<String> photoList2; // For photo 2.
        public ListViewHolder(View itemView, ArrayList<String> photoList1, ArrayList<String> contentList, ArrayList<String> photoList2){
            super(itemView);
            profilePhoto = (ImageView) itemView.findViewById(R.id.profilePhoto);
            activityText = (TextView) itemView.findViewById(R.id.listText);
            post = (ImageView) itemView.findViewById(R.id.post);
            this.photoList1 = photoList1;
            this.contentList = contentList;
            this.photoList2 = photoList2;
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){

            activityText.setText(this.contentList.get(position).toString());

            Picasso.get().load(this.photoList1.get(position).toString()).into(profilePhoto);
            Picasso.get().load(this.photoList2.get(position).toString()).into(post);
        }

        public void onClick(View view){}


    }


}
