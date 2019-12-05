package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nazmi.mobilexstaj.R;
import com.example.nazmi.mobilexstaj.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InternsFragment extends Fragment {
    //Todo: Identify Fragment View
    View internsView;
    private RecyclerView myInternList;
    private DatabaseReference internsRef;

    public InternsFragment() {
        //Todo: Required empty public constructor
    }

    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        internsView = inflater.inflate(R.layout.profileactivity_interns_fragment, container, false);
        myInternList = internsView.findViewById(R.id.interns_list);
        myInternList.setLayoutManager(new LinearLayoutManager(getContext()));
        internsRef = FirebaseDatabase.getInstance().getReference("Users");
        return internsView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //User yoktu ekledim
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(internsRef, User.class)
                        .build();
        FirebaseRecyclerAdapter<User, InternsViewHolder> adapter
                = new FirebaseRecyclerAdapter<User, InternsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final InternsViewHolder holder, int position, @NonNull final User model) {
                internsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.userName.setText(model.getUserName());
                        holder.userEmail.setText(model.getEmail());
                        holder.userSchool.setText(model.getSchoolName());
                        holder.userSection.setText(model.getSectionName());
                        holder.userDate.setText(model.getInternDate());
                        holder.userSocial.setText(model.getSocialInfo());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @NonNull
            @Override
            public InternsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.display_interns_layout, viewGroup, false);
                return new InternsViewHolder(view);
            }
        };
        myInternList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class InternsViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail, userSchool, userSection, userDate, userSocial;

        //Publicti private yaptım
        private InternsViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_profile_name);
            userEmail = itemView.findViewById(R.id.user_email);
            userSchool = itemView.findViewById(R.id.user_school);
            userSection = itemView.findViewById(R.id.user_section);
            userDate = itemView.findViewById(R.id.user_date);
            userSocial = itemView.findViewById(R.id.user_social);

        }
    }
}
