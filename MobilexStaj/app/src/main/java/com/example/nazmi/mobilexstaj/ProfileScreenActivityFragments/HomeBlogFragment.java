package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.nazmi.mobilexstaj.MainActivity;
import com.example.nazmi.mobilexstaj.R;

import com.example.nazmi.mobilexstaj.model.BlogModel;
import com.example.nazmi.mobilexstaj.model.BlogViewHolder;
import com.example.nazmi.mobilexstaj.model.GetTimeAgo;
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


public class HomeBlogFragment extends Fragment {

    private View homeBaseView;

    private FloatingActionButton btAddPost;

    FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Button newBlogGate;

    FragmentManager fragNewNotes, fragNotes;

    Context context;

    private RecyclerView mBlogList;

    LinearLayoutManager linearLayoutManager;

    private DatabaseReference fNotesDatabase;

    private ProgressBar progressBar;

    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBaseView = inflater.inflate(R.layout.profileactivity_blog_fragment, container, false);
        progressBar = homeBaseView.findViewById(R.id.fragBlog_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //Todo: Container Parent on Create
        btAddPost = homeBaseView.findViewById(R.id.bt_add_post);
        context = getContext();
        mBlogList = homeBaseView.findViewById(R.id.fragBlog_blogList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);


//        mBlogList.setHasFixedSize(true);
//        mBlogList.setLayoutManager(new LinearLayoutManager(context));
//        mNotesList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        }
        updateUI();
        loadData();
        btAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.profile_main,new HomeBlogAddFragment()).addToBackStack(null).commit();
            }
        });

        return homeBaseView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadData() {
//        Query query = fNotesDatabase.orderByValue();

        FirebaseRecyclerOptions<BlogModel> options = new FirebaseRecyclerOptions.Builder<BlogModel>()
                .setQuery(fNotesDatabase, BlogModel.class)
                .build();

        FirebaseRecyclerAdapter<BlogModel, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogModel, BlogViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final BlogViewHolder holder, int position, @NonNull BlogModel model) {
                final String blogId = getRef(position).getKey();
                assert blogId != null;
                fNotesDatabase.child(blogId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("timestamp")) {
                            String title = Objects.requireNonNull(dataSnapshot.child("title").getValue()).toString();
                            String content = dataSnapshot.child("content").getValue().toString();
                            String link = dataSnapshot.child("link").getValue().toString();
                            String userName = dataSnapshot.child("userName").getValue().toString();
                            String timestamp = dataSnapshot.child("timestamp").getValue().toString();

                            holder.setTextTitle(title);
                            holder.setTextContent(content);
                            holder.setTextLink(link);
                            holder.setTextUser(userName);
//                            holder.setNoteTime(timestamp);
//                            GetTimeAgo getTimeAgo = new GetTimeAgo();
                            holder.setTextTime(GetTimeAgo.getTimeAgo(Long.parseLong(timestamp), getContext()));
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        holder.blogCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragNotes = getFragmentManager();
                                HomeBlogAddFragment fragment = new HomeBlogAddFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("blogId", blogId);
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
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(context).inflate(R.layout.display_blog_layout, viewGroup, false);

                return new BlogViewHolder(view);
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
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



}
