package com.stuffbox.yedivision.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.adapters.DocumentAdapter;
import com.stuffbox.yedivision.downloadtasks.AllDownloadTask;
import com.stuffbox.yedivision.models.Document;
import com.stuffbox.yedivision.models.Subject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class DocumentLister extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView ;
    String subjectname ;
    DatabaseReference reference;
    int flag=1;
    Subject subject;

    ValueEventListener valueEventListener;
    DocumentAdapter documentAdapter ;
    String  type;
    Toolbar toolbar;
    ArrayList<Document>  documents = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documentlist);
        type = getIntent().getStringExtra("type");
        subject = (Subject) getIntent().getSerializableExtra("subject");
        subjectname = subject.getSubjectname();
        recyclerView = findViewById(R.id.recycler_view_for_documents);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        if(documents.size()==0)
            menuItem.setEnabled(false);
        MenuItem menuItem1=menu.findItem(R.id.info);
        MenuItem menuItem2=menu.findItem(R.id.download);
        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //    Intent intent=new Intent(getApplicationContext(),about.class);
             //   flag=0;
                Intent intent = new Intent(DocumentLister.this,DocumentAdder.class);
                intent.putExtra("type",type);
                intent.putExtra("sem",String.valueOf(subject.getSem()));
                startActivity(intent);

                // startActivity(intent);
                return false;
            }
        });
        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
           //      AllDownloadTask.from=type;
                int k=0;
                for(int i=0;i<documents.size();i++)
                {
                    Document s=documents.get(i);
                    Log.i("names",s.getName());
                    File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yedivision/"+type+"/"+s.getName()+".pdf");
                    if(!file.exists())
                    k++;

                }
                int l=k;

                for(int i=0;i<documents.size();i++)
                {
                    Document s=documents.get(i);
                    Log.i("names",s.getName());
                    File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yedivision/"+type+"/"+s.getName()+".pdf");
                    if(!file.exists())
                    {                        k--;

                        new AllDownloadTask(getApplicationContext(),s.getName(),type,k).execute(s.getLink());

                    }

                }
                if(l!=0)
                    Toast.makeText(getApplicationContext(),"Downloading all pdf's.Please keep the app in background",Toast.LENGTH_SHORT).show();

                if(l==0)
                    Toast.makeText(getApplicationContext(),"All pdfs are already downloaded",Toast.LENGTH_SHORT).show();
                /*  */
                return false;
            }
        });
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return  true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Document> newlist=new ArrayList<>();

        for(Document asdata:  documents)

        {
            String name=asdata.getName().toLowerCase();
            if(name.contains(newText))
                newlist.add(asdata);

        }
        documentAdapter.setFilter(newlist);
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        String ref = type +"ofsem"+subject.getSem();
        Log.d("reference",ref);
        valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                documents.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                if(flag==1)
                    for(DataSnapshot child : children)
                    {
                        Document value = child.getValue(Document.class);
                        if(value.getSubjectname().equals(subjectname))
                            documents.add(value);
                    }
                Collections.reverse(documents);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

                documentAdapter = new DocumentAdapter( documents,DocumentLister.this,type);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(documentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference = FirebaseDatabase.getInstance().getReference(ref);
        reference.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null)
            reference.removeEventListener(valueEventListener);
    }
}
