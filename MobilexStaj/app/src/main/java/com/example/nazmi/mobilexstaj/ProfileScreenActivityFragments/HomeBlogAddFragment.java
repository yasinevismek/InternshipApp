package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nazmi.mobilexstaj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class HomeBlogAddFragment extends Fragment implements View.OnClickListener {
    //Todo: Identify Fragment View
    private View homeBlogAddView;

    private Button btAddBlog, btDelBlog;
    private EditText etTitleBlog, etContentBlog, etLinkBlog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private String blogID, userID;

    private boolean isExist;
    private Context contextBlog;

    public HomeBlogAddFragment() {
        //Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextBlog = getContext();
    }

    //Todo: Fragment View to On Create
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBlogAddView = inflater.inflate(R.layout.profileactivity_blog_add_fragment, container, false);

        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                blogID = bundle.getString("blogId", "");
            }
            if (!blogID.trim().equals("")) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btDelBlog = homeBlogAddView.findViewById(R.id.delete_blog_bt);
        btAddBlog = homeBlogAddView.findViewById(R.id.blog_create_bt);
        etTitleBlog = homeBlogAddView.findViewById(R.id.blog_title);
        etContentBlog = homeBlogAddView.findViewById(R.id.blog_content);
        etLinkBlog = homeBlogAddView.findViewById(R.id.blog_link);
        btDelBlog.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");

        btAddBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitleBlog.getText().toString().trim();
                String content = etContentBlog.getText().toString().trim();
                String link = etLinkBlog.getText().toString().trim();
                String userName = firebaseAuth.getCurrentUser().getDisplayName();
                String userId = firebaseAuth.getCurrentUser().getUid();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content) && !TextUtils.isEmpty(link)
                        || !TextUtils.isEmpty(title) && TextUtils.isEmpty(content) && !TextUtils.isEmpty(link)
                        || !TextUtils.isEmpty(title) && !TextUtils.isEmpty(content) && TextUtils.isEmpty(link)) {
                    createBlog(title, content, link, userName, userId);
                    getActivity().getSupportFragmentManager().popBackStack();
                    ((Activity) contextBlog).isFinishing();
                } else {
                    Snackbar.make(view, "Title is not empty, fill in at least one of the description and link sections", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        putData();

        return homeBlogAddView;
    }

    private void putData() {
        if (isExist) {
            databaseReference.child(blogID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("content")
                            && (dataSnapshot.hasChild("link") && dataSnapshot.hasChild("userId"))) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String content = dataSnapshot.child("content").getValue().toString();
                        String link = dataSnapshot.child("link").getValue().toString();
                        userID = dataSnapshot.child("userId").getValue().toString();

                        etTitleBlog.setText(title);
                        etContentBlog.setText(content);
                        etLinkBlog.setText(link);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void createBlog(String title, String content, String link, String userName, String userId) {
        if (firebaseUser != null) {
            if (isExist) {
                if (userID.trim().equals(firebaseUser.getUid())) {
                    // Update a Blog
                    Map updateMap = new HashMap();
                    updateMap.put("title", etTitleBlog.getText().toString().trim());
                    updateMap.put("content", etContentBlog.getText().toString().trim());
                    updateMap.put("link", etLinkBlog.getText().toString().trim());
                    updateMap.put("userName", firebaseUser.getDisplayName());
                    databaseReference.child(blogID).updateChildren(updateMap);
                    Toast.makeText(contextBlog, "Blog updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contextBlog, "You are not authorized to edit the blog", Toast.LENGTH_SHORT).show();
                }
            } else {

                final DatabaseReference newBlogRef = databaseReference.push();

                final Map blogMap = new HashMap();
                blogMap.put("title", title);
                blogMap.put("content", content);
                blogMap.put("link", link);
                blogMap.put("userName", userName);
                blogMap.put("userId", userId);
                blogMap.put("timestamp", ServerValue.TIMESTAMP);

                Thread mainThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        newBlogRef.setValue(blogMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@io.reactivex.annotations.NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(contextBlog, "Blog added to database", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(contextBlog, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                mainThread.start();
            }
        } else {
            Toast.makeText(contextBlog, "USERS IS NOT SIGNED IN", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == btDelBlog.getId()) {
            if (isExist) {
                if (userID.trim().equals(firebaseUser.getUid())) {
                    deleteBlog();
                } else {
                    Toast.makeText(contextBlog, "You are not authorized to edit the blog", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(contextBlog, "Nothing to delete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteBlog() {

        databaseReference.child(blogID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@io.reactivex.annotations.NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(contextBlog, "Blog Deleted", Toast.LENGTH_SHORT).show();
                    blogID = "";
                    getActivity().getSupportFragmentManager().popBackStack();
                    ((Activity) contextBlog).isFinishing();

                } else {
                    Log.e("HomeBlogAddFragment", task.getException().toString());
                    Toast.makeText(contextBlog, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
