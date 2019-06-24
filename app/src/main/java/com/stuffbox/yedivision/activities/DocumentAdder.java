package com.stuffbox.yedivision.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.models.Document;
import com.stuffbox.yedivision.models.Subject;

import java.util.ArrayList;
import java.util.UUID;

public class DocumentAdder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editText,editText1,editText2;
    private DatabaseReference databaseReference;
    ArrayList<String> subjectname = new ArrayList<>();

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    private FirebaseStorage storage;
    private FirebaseDatabase firebaseDatabase;
    Uri uri;
    int subjectcode=-1;
    String type;
    int k=1;
    String toasttype;
    String sem ;
    Spinner spinner;DatabaseReference mDatabaseReference;
    StorageReference storageReference;    Button b,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_document);
        spinner=findViewById(R.id.spinner);
        editText1=findViewById(R.id.nameofassignment);
        b=findViewById(R.id.save);
        type=getIntent().getStringExtra("type");
        toasttype=type.substring(0,type.length()-1);
        sem = getIntent().getStringExtra("sem");
        databaseReference= FirebaseDatabase.getInstance().getReference("subjects");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                subjectname.clear();
                subjectname.add("");

                for(DataSnapshot child : children)
                {
                    Subject currentsubject = child.getValue(Subject.class);
                    Log.i("subjectname",currentsubject.getSubjectname());
                    subjectname.add(currentsubject.getSubjectname());

                }
                ArrayAdapter aa=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,subjectname.toArray());
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(aa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinner.setOnItemSelectedListener(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        c=findViewById(R.id.upload);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText1.getText().toString();
                String link;
                if(k!=1) {
                    if(uri==null)
                        Toast.makeText(DocumentAdder.this,"upload first",Toast.LENGTH_SHORT).show();
                    else
                    {   link = uri.toString();

                        if (name .equals( ""))
                            Toast.makeText(DocumentAdder.this, "enter name of "+toasttype, Toast.LENGTH_SHORT).show();
                        else
                        if(subjectcode==0)
                            Toast.makeText(DocumentAdder.this,"select subject first",Toast.LENGTH_SHORT).show();

                        else {

                            Document data = new Document(name, link,subjectname.get(subjectcode));
                            String ref= type+"ofsem"+sem;
                            databaseReference.child(ref).push().setValue(data);
                            editText1.setText("");
                        }

                    }}

                else Toast.makeText(DocumentAdder.this,"upload first",Toast.LENGTH_SHORT).show();

            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent=Intent.createChooser(intent,"choose a file");
                startActivityForResult(intent,PICK_CONTACT_REQUEST);
                k=0;
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        subjectcode=position;
        ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    protected  void onActivityResult(int requestcode,int resultcode,Intent data)
    {  super.onActivityResult(requestcode, resultcode, data);

        if(requestcode==PICK_CONTACT_REQUEST) {
            if(resultcode==RESULT_OK)
            {
                Uri link=data.getData();

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.show();
                StorageReference ref = storageReference.child( UUID.randomUUID().toString());
                ref.putFile(link).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(DocumentAdder.this,"UPLOADED", Toast.LENGTH_SHORT).show();

                        uri=taskSnapshot.getDownloadUrl();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(DocumentAdder.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
        }



    }





}
