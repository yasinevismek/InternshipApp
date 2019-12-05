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
import com.example.nazmi.mobilexstaj.model.UsefulDefaultModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UseFulFragment extends Fragment{
    //Todo: Identify Fragment View
    View usefulView;
    private RecyclerView mUsefulList;
    private DatabaseReference mUseFulDefaultRef;

    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        usefulView =inflater.inflate(R.layout.profileactivity_usefull_fragment,container,false);

        mUsefulList = usefulView.findViewById(R.id.fragUseful_listLink);
        mUsefulList.setLayoutManager(new LinearLayoutManager(getContext()));
        mUseFulDefaultRef = FirebaseDatabase.getInstance().getReference("UseFulLink");


        return usefulView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<UsefulDefaultModel> options =
                new FirebaseRecyclerOptions.Builder<UsefulDefaultModel>()
                        .setQuery(mUseFulDefaultRef, UsefulDefaultModel.class)
                        .build();

        FirebaseRecyclerAdapter<UsefulDefaultModel, UseFulViewHolder> adapter
                = new FirebaseRecyclerAdapter<UsefulDefaultModel, UseFulViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UseFulViewHolder holder, int position, @NonNull final UsefulDefaultModel model) {
                mUseFulDefaultRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.linkHref.setText(model.getLinkHref());
                        holder.linkTitle.setText(model.getLinkTitle());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public UseFulViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.display_useful_layout,viewGroup,false);

                return new UseFulViewHolder(view);
            }
        };
        mUsefulList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class UseFulViewHolder extends RecyclerView.ViewHolder{
        TextView linkTitle, linkHref;
        private UseFulViewHolder (@NonNull View itemView){
            super(itemView);
            linkTitle = itemView.findViewById(R.id.displayUseful_linkTitle);
            linkHref = itemView.findViewById(R.id.displayUseful_link);
        }
    }
}
