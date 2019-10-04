package com.stuffbox.yedivision.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

public class AddDocument extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    int subjectcode=1;
    String thisistype;
    private FirebaseStorage storage;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<String> subjectname = new ArrayList<>();
    private DatabaseReference databaseReference;
    private EditText editText,editText1,editText2;
    Spinner subjectSpinner,typeSpinner;
    ArrayList<String> types;
    Button b;
    Uri uri;
    StorageReference storageReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.externaluploader);
        subjectSpinner  = findViewById(R.id.spinner);
        typeSpinner = findViewById(R.id.typespinner);
        editText1=findViewById(R.id.nameofassignment);
        databaseReference= FirebaseDatabase.getInstance().getReference("subjects");
        b=findViewById(R.id.save);
        Intent intent = getIntent();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String action = intent.getAction();
        String type= intent.getType();
        if(Intent.ACTION_SEND.equals(action) && type!= null)
        {
            if("application/pdf".equals(type))
            {
                Uri fileURI = intent.getData();
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.show();
                StorageReference ref = storageReference.child( UUID.randomUUID().toString());
                ref.putFile(fileURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(AddDocument.this,"UPLOADED", Toast.LENGTH_SHORT).show();

                        uri=taskSnapshot.getDownloadUrl();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(AddDocument.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

                subjectSpinner.setAdapter(aa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        subjectSpinner.setOnItemSelectedListener(this);
        types = new ArrayList<>();
        types.add("Assignments");
        types.add("Papers");
        types.add("Study Material");
        ArrayAdapter aa=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,types.toArray());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(aa);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thisistype=  types.get(i);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText1.getText().toString();
                String link;
                    if(uri==null)
                        Toast.makeText(AddDocument.this,"upload first",Toast.LENGTH_SHORT).show();
                    else
                    {   link = uri.toString();

                        if (name .equals( ""))
                            Toast.makeText(AddDocument.this, "enter name of "+thisistype, Toast.LENGTH_SHORT).show();
                        else
                        if(subjectcode==0)
                            Toast.makeText(AddDocument.this,"select subject first",Toast.LENGTH_SHORT).show();
                        else
                            if(thisistype.equals(""))
                                Toast.makeText(AddDocument.this,"select type first",Toast.LENGTH_SHORT).show();

                            else {

                            Document data = new Document(name, link,subjectname.get(subjectcode));
                            String changedtype ="";
                            if(thisistype.equals("Assignments"))
                                changedtype="assignments";
                            else if(thisistype.equals("Study Material"))
                                changedtype = "studymaterials";
                            else
                                changedtype = "papers";
                            String ref= changedtype+"ofsem"+"5";
                            databaseReference.child(ref).push().setValue(data);
                            editText1.setText("");
                        }

                    }


            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        subjectcode=position;
        ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
