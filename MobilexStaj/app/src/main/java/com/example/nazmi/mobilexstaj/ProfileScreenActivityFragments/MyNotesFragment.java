package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nazmi.mobilexstaj.MainActivity;
import com.example.nazmi.mobilexstaj.R;
import com.example.nazmi.mobilexstaj.model.GetTimeAgo;
import com.example.nazmi.mobilexstaj.model.NoteModel;
import com.example.nazmi.mobilexstaj.model.NoteViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MyNotesFragment extends Fragment implements View.OnClickListener {
    //Todo: Identify Fragment View
    View myNotesView;

    FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Button newNoteGate;

    FragmentManager fragNewNotes, fragNotes;

    Context context;

    private RecyclerView mNotesList;

    GridLayoutManager gridLayoutManager;

    private DatabaseReference fNotesDatabase;


    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myNotesView = inflater.inflate(R.layout.profileactivity_mynotes_fragment, container, false);


        context = getContext();

        mNotesList = myNotesView.findViewById(R.id.main_notes_list);
        gridLayoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        mNotesList.setHasFixedSize(true);
        mNotesList.setLayoutManager(gridLayoutManager);
//        mNotesList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseUser.getUid());
        }
        updateUI();
        loadData();
        newNoteGate = myNotesView.findViewById(R.id.new_note_gate);
        newNoteGate.setOnClickListener(this);
        return myNotesView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadData() {
//        Query query = fNotesDatabase.orderByValue();

        FirebaseRecyclerOptions<NoteModel> options = new FirebaseRecyclerOptions.Builder<NoteModel>()
                .setQuery(fNotesDatabase, NoteModel.class)
                .build();

        FirebaseRecyclerAdapter<NoteModel, NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NoteViewHolder holder, int position, @NonNull NoteModel model) {
                final String noteId = getRef(position).getKey();
                assert noteId != null;
                fNotesDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("timestamp")) {
                            String title = Objects.requireNonNull(dataSnapshot.child("title").getValue()).toString();
                            String timestamp = dataSnapshot.child("timestamp").getValue().toString();

                            holder.setNoteTitle(title);
//                            holder.setNoteTime(timestamp);
//                            GetTimeAgo getTimeAgo = new GetTimeAgo();
                            holder.setNoteTime(GetTimeAgo.getTimeAgo(Long.parseLong(timestamp), getContext()));
                        }
                        holder.noteCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragNotes = getFragmentManager();
                                MyNotesNewNotesFragment fragment = new MyNotesNewNotesFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("noteId", noteId);
                                fragment.setArguments(bundle);
                                fragNotes.beginTransaction().replace(R.id.profile_main, fragment).addToBackStack(null).commit();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(context).inflate(R.layout.display_note_layout, viewGroup, false);
                return new NoteViewHolder(view);
            }
        };

        mNotesList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void updateUI() {
        if (firebaseUser != null) {
            Log.i("MyNotesFragment", "firebaseUser != null");
        } else {
            Intent startIntent = new Intent(getContext(), MainActivity.class);
            startActivity(startIntent);
            Log.i("MyNotesFragment", "firebaseUser == null");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == newNoteGate.getId()) {
//            Intent newIntent = new Intent(contex,MyNotesNewNotesFragment.class);
//            startActivity(newIntent);
            assert getFragmentManager() != null;
            fragNewNotes = getFragmentManager();
            fragNewNotes.beginTransaction().replace(R.id.profile_main, new MyNotesNewNotesFragment()).addToBackStack(null).commit();
        }
    }


    /*
     * Converting dp to pixel
     */
//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }


}
