package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;

public class MyNotesNewNotesFragment extends Fragment implements View.OnClickListener {
    private Button btnCreate, deleteNote;
    private EditText etTitle, etContent;

    private FirebaseAuth fAuth;
    private DatabaseReference fNotesDatabase;

    private String noteID;

    private boolean isExist;
    View newNoteView;

    private Context context;

    public MyNotesNewNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newNoteView = inflater.inflate(R.layout.profileactivity_mynotes_newnote_fragment, container, false);
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                noteID = bundle.getString("noteId", "");
            }
//            noteID = ((Activity) context).getIntent().getStringExtra("noteId");
//            Intent ıntent = ((Activity) context).getIntent();
//            Toast.makeText(this, noteID, Toast.LENGTH_SHORT).show();
//            if (!noteID.trim().equals("")) {
//                isExist = true;
//            } else {
//                isExist = false;
//            }

            isExist = !noteID.trim().equals("");

        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteNote = newNoteView.findViewById(R.id.new_note_delete_btn);
        btnCreate = newNoteView.findViewById(R.id.new_note_btn);
        etTitle = newNoteView.findViewById(R.id.new_note_title);
        etContent = newNoteView.findViewById(R.id.new_note_content);

        deleteNote.setOnClickListener(this);
        fAuth = FirebaseAuth.getInstance();
        fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                String username = fAuth.getCurrentUser().getDisplayName();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
                    createNote(title, content,username);
                    getActivity().getSupportFragmentManager().popBackStack();
                    ((Activity) context).isFinishing();
                } else {
                    Snackbar.make(view, "Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        putData();
        return newNoteView;
    }

    private void putData() {

        if (isExist) {
            fNotesDatabase.child(noteID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("content")) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String content = dataSnapshot.child("content").getValue().toString();

                        etTitle.setText(title);
                        etContent.setText(content);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void createNote(String title, String content,String username) {

        if (fAuth.getCurrentUser() != null) {

            if (isExist) {
                // UPDATE A NOTE
                Map updateMap = new HashMap();
                updateMap.put("title", etTitle.getText().toString().trim());
                updateMap.put("content", etContent.getText().toString().trim());
                updateMap.put("timestamp", ServerValue.TIMESTAMP);

                fNotesDatabase.child(noteID).updateChildren(updateMap);

                Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show();
            } else {
                // CREATE A NEW NOTE
                final DatabaseReference newNoteRef = fNotesDatabase.push();

                final Map noteMap = new HashMap();
                noteMap.put("title", title);
                noteMap.put("content", content);
                noteMap.put("username", username);
                noteMap.put("timestamp", ServerValue.TIMESTAMP);

                Thread mainThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        newNoteRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Note added to database", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                mainThread.start();
            }
        } else {
            Toast.makeText(context, "USERS IS NOT SIGNED IN", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == deleteNote.getId()) {
            if (isExist) {
                deleteNote();
            } else {
                Toast.makeText(context, "Nothing to delete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteNote() {

        fNotesDatabase.child(noteID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                    noteID = "";
                    getActivity().getSupportFragmentManager().popBackStack();
                    ((Activity) context).isFinishing();

                } else {
                    Log.e("NewNoteActivity", task.getException().toString());
                    Toast.makeText(context, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
