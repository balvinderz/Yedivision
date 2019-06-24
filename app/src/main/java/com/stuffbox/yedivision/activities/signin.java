package com.stuffbox.yedivision.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.activities.MainActivity;
import com.stuffbox.yedivision.activities.createid;
import com.stuffbox.yedivision.models.Userid;

/**
 * Created by jasbe on 18-07-2018.
 */

public class signin  extends AppCompatActivity{
    Button  login,signup;
    int p=1;
    EditText username,password;
    FirebaseDatabase firebaseDatabase,firebaseDatabase1;
    DatabaseReference databaseReference,databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signin);
        signup=findViewById(R.id.sign);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        login=findViewById(R.id.login);
username=findViewById(R.id.username);
password=findViewById(R.id.input_password);
        signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getApplicationContext(), createid.class);
        startActivity(intent);
    }
});
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final    String use=username.getText().toString();
                final String pas=password.getText().toString();
                if(use.equals(""))
                    Toast.makeText(getApplicationContext(),"Enter username",Toast.LENGTH_SHORT).show();
                else
                    if(pas.equals(""))
                        Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
                else
                    {
                        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child : children) {
                                    Userid value = child.getValue(Userid.class);
if(value.getUserid().equals(use))
{p=0;
    if(value.getPassword().equals(pas)) {
        MainActivity.logincode=1;
p=2;
        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
startActivity(intent);
break;
    }
    else
        Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_SHORT).show();

}



                                }
                                if(p==1) {


                                    Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
            }
        });
    }

}
