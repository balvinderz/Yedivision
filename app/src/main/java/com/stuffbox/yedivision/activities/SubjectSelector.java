package com.stuffbox.yedivision.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.adapters.SubjectAdapter;
import com.stuffbox.yedivision.models.Subject;

import java.util.ArrayList;

public class SubjectSelector extends AppCompatActivity {
    ArrayList<Subject> subjectList= new ArrayList<>();
    SubjectAdapter subjectAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjectsnew);
        final String type = getIntent().getStringExtra("type");
        TextView TypeofContent = findViewById(R.id.xz);
        TypeofContent.setText(type);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("subjects");
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_for_subjects);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              Iterable<DataSnapshot>  children =dataSnapshot.getChildren();
              for(DataSnapshot child : children)
              {
                  Subject subject = child.getValue(Subject.class);
                  subjectList.add(subject);

              }
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

                subjectAdapter = new SubjectAdapter(getApplicationContext(), subjectList,type);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(subjectAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
