package com.stuffbox.yedivision.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stuffbox.yedivision.R;

import com.stuffbox.yedivision.models.lecture;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
public static  int i=0;
public static int cd=0;
public static int login=0;
TextView signin,text;
Toolbar toolbar;
public static int logincode=0;
    int soja=5;
    public static String name;
    public  static Context context;
int c=1;
int nomorenewlines=0;
Scene scene;
Scene scene1;
    public  static  long k;

    FirebaseDatabase firebaseDatabase,firebaseDatabase1;
public int l=-1;
    DatabaseReference databaseReference,databaseReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        l = 0;
toolbar=findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED )|| ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE )!=PackageManager.PERMISSION_GRANTED )  {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);            // Permission is not granted
        }

            //    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
Button Button1=findViewById(R.id.x);
        Button Button2=findViewById(R.id.y);
        Button Button3=findViewById(R.id.z);
if(Build.VERSION.SDK_INT> Build.VERSION_CODES.KITKAT)
{
    Button1.setBackground(ContextCompat.getDrawable(this,R.drawable.test2));
    Button2.setBackground(ContextCompat.getDrawable(this,R.drawable.test2));
    Button3.setBackground(ContextCompat.getDrawable(this,R.drawable.test2));

}


        databaseReference.child("Timetable_SE_A_SEM4").child("Monday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childrens=dataSnapshot.getChildren();
                for(DataSnapshot child:childrens)
                {
                    lecture value=child.getValue(lecture.class);
                    if(value.subject.contains(" A2"))
                    {
                        value.subject=value.subject.replace(" A","\n                A");
                        value.teacher=value.teacher.replace(" A","\n              A");
                        value.room=value.room.replace(" A","\n                    A");
                    }
                    Timetable.monday.add(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("Timetable_SE_A_SEM4").child("Tuesday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childrens=dataSnapshot.getChildren();
                for(DataSnapshot child:childrens)
                {
                    lecture value=child.getValue(lecture.class);
                    if(value.subject.contains(" A2"))
                    {
                        value.subject=value.subject.replace(" A","\n                A");
                        value.teacher=value.teacher.replace(" A","\n              A");
                        value.room=value.room.replace(" A","\n                    A");
                    }
                    Timetable.tuesday.add(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("Timetable_SE_A_SEM4").child("Wednesday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childrens=dataSnapshot.getChildren();
                for(DataSnapshot child:childrens)
                {
                    lecture value=child.getValue(lecture.class);
                    if(value.subject.contains(" A2"))
                    {
                        value.subject=value.subject.replace(" A","\n                A");
                        value.teacher=value.teacher.replace(" A","\n              A");
                        value.room=value.room.replace(" A","\n                    A");
                    }
                    Timetable.wednesday.add(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("Timetable_SE_A_SEM4").child("Thursday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childrens=dataSnapshot.getChildren();
                for(DataSnapshot child:childrens)
                {
                    lecture value=child.getValue(lecture.class);
                    if(value.subject.contains(" A2"))
                    {
                        value.subject=value.subject.replace(" A","\n                A");
                        value.teacher=value.teacher.replace(" A","\n              A");
                        value.room=value.room.replace(" A","\n                    A");
                    }
                    Timetable.thursday.add(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("Timetable_SE_A_SEM4").child("Friday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childrens=dataSnapshot.getChildren();
                for(DataSnapshot child:childrens)
                {
                    lecture value=child.getValue(lecture.class);
                    if(value.subject.contains(" A2"))
                    {
                        value.subject=value.subject.replace(" A","\n                A");
                        value.teacher=value.teacher.replace(" A","\n              A");
                        value.room=value.room.replace(" A","\n                    A");
                    }
                    Timetable.friday.add(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu)
    {        getMenuInflater().inflate(R.menu.home,menu);

        MenuItem menuItem=menu.findItem(R.id.log);
        if(logincode==1)
            menuItem.setIcon(R.drawable.logout);
menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(logincode==1)
        {
            logincode=0;
            menuItem.setIcon(R.drawable.login);
            Toast.makeText(getApplicationContext(),"Signed Out Successfully",Toast.LENGTH_SHORT).show();

        }
        else {
            Intent intent = new Intent(getApplicationContext(), com.stuffbox.yedivision.activities.signin.class);
            startActivity(intent);
        }
        return false;
    }
});
final MenuItem timetable=menu.findItem(R.id.timetable);
timetable.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

            Intent intent=new Intent(getApplicationContext(),Timetable.class);
            startActivity(intent);
        return false;
    }
});
MenuItem web=menu.findItem(R.id.website);
web.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://yedivision.herokuapp.com/"));
        startActivity(intent);        return false;
    }
});
return  true;

    }
    public void onDestroy() {
i=0;
cd=0;
        super.onDestroy();
    }
    public void change1(View v)
    {

       change4("assignments");

    }

    public void change2(View v)
    {

        change4("papers");

    }
    public void change3(View v)
    {

       change4("studymaterials");

    }

public  void signin(View v)
{
    if(logincode==1)
    {
        logincode=0;
        text.setText("SIGN IN");
        Toast.makeText(getApplicationContext(),"Signed Out Successfully",Toast.LENGTH_SHORT).show();
    }
    else {
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
        overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);

    }
}
public void change4(String type)
{
    Intent intent=new Intent(this, SubjectSelector.class);
    intent.putExtra("type",type);
    startActivity(intent);
    overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);

}

}
