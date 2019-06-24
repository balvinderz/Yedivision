package com.stuffbox.yedivision.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.models.Userid;

/**
 * Created by jasbe on 25-07-2018.
 */

public class createid extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    int batchcode=0;
    Button create;
    EditText password,repassword,name,user;
    private DatabaseReference databaseReference;
    StorageReference storageReference;
    private FirebaseStorage storage;
     int validid=1;
     int check;

    private FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabaseReference;
    String pas,repas,nam,use;
    String[] batch={"","A1","A2","A3","A4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
password=findViewById(R.id.input_password);
user=findViewById(R.id.input_username);
repassword=findViewById(R.id.reEnterPassword);
name=findViewById(R.id.input_name);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
create=findViewById(R.id.btn_signup);
        spinner=findViewById(R.id.spine);
        ArrayAdapter aa=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,batch);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(this);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pas=password.getText().toString();
                use=user.getText().toString();
Log.i("username",use);
                repas=repassword.getText().toString();
                nam=name.getText().toString();
                if(use.equals(""))
                    Toast.makeText(getApplicationContext(),"Username cannot be empty",Toast.LENGTH_SHORT).show();

else
                if(nam.equals(""))
                    Toast.makeText(getApplicationContext(),"enter name",Toast.LENGTH_SHORT).show();
                else
                    if(batchcode==0)
                        Toast.makeText(getApplicationContext(),"Enter Batch",Toast.LENGTH_SHORT).show();
else
    if(pas.equals("")||pas.length()<8)
        Toast.makeText(getApplicationContext(),"password's length should be greater or equal to 8",Toast.LENGTH_SHORT).show();
else
    if (repas.equals(""))
        Toast.makeText(getApplicationContext(),"re enter password cannot be empty",Toast.LENGTH_SHORT).show();
else
    if(!(pas.equals(repas)))
                Toast.makeText(getApplicationContext(),"Both Password should match",Toast.LENGTH_SHORT).show();
else
    if(isvalid(use))
        Toast.makeText(getApplicationContext(),"Username Already Exists",Toast.LENGTH_SHORT).show();

    else
    {
        Userid userid=new Userid(use,pas,nam,batchcode);
        databaseReference.child("users").push().setValue(userid);
        Toast.makeText(getApplicationContext(),"Id created Successfully",Toast.LENGTH_SHORT).show();
Intent intent=new Intent(getApplicationContext(), MainActivity.class);
MainActivity.logincode=1;
startActivity(intent);
    }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        batchcode=position;
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
    public void loginpage(View view)
    {
        Intent intent=new Intent(getApplicationContext(), signin.class);
        startActivity(intent);
    }
boolean isvalid(final String username)
{
    databaseReference.child("users").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
            for (DataSnapshot child : children) {
                Userid value = child.getValue(Userid.class);
                if(value.getUserid().equals(username))
                {
                    check=0;
                    Log.i("check",""+username+check);

                }



            }




        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    if(check==0) {
        check=-1;
        return true;
    }
    else
        return false;
}
}
