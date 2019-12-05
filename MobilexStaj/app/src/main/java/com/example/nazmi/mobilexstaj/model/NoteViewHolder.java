package com.example.nazmi.mobilexstaj.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nazmi.mobilexstaj.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    View mView;
    TextView textTitle, textTime;
    public CardView noteCard;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        textTitle = mView.findViewById(R.id.note_title);
        textTime = mView.findViewById(R.id.note_time);
        noteCard = mView.findViewById(R.id.displayUseful_listUseful);
    }

    public void setNoteTitle(String title) {
        textTitle.setText(title);
    }

    public void setNoteTime(String time){
        textTime.setText(time);
    }


}
