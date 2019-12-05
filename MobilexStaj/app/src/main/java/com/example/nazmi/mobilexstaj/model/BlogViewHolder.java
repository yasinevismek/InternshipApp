package com.example.nazmi.mobilexstaj.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nazmi.mobilexstaj.R;

public class BlogViewHolder extends RecyclerView.ViewHolder {
    View blogView;
    TextView textTitle,textContent,textLink,textUser,textTime;
    public CardView blogCard;


    public BlogViewHolder(@NonNull View itemView) {
        super(itemView);
        blogView = itemView;
        textTitle = blogView.findViewById(R.id.displayBlog_title);
        textContent = blogView.findViewById(R.id.displayBlog_description);
        textLink = blogView.findViewById(R.id.displayBlog_link);
        textUser = blogView.findViewById(R.id.displayBlog_userName);
        textTime = blogView.findViewById(R.id.displayBlog_updateTime);
        blogCard = blogView.findViewById(R.id.displayBlog_cardView);
    }

    public void setTextTitle(String title) {
        textTitle.setText(title);
    }

    public void setTextContent(String content) {
        textContent.setText(content);
    }

    public void setTextLink(String link) {
        textLink.setText(link);
    }

    public void setTextUser(String userName) {
        textUser.setText(userName);
    }

    public void setTextTime(String time) {
        textTime.setText(time);
    }

}
