package com.stuffbox.yedivision.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.adapters.lectureadapter;
import com.stuffbox.yedivision.models.lecture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Timetable extends AppCompatActivity{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
  public static  ArrayList<lecture> monday=new ArrayList<>();
   public static ArrayList<lecture> tuesday=new ArrayList<>();
    public static ArrayList<lecture> wednesday=new ArrayList<>();
   public static ArrayList<lecture> thursday=new ArrayList<>();
   ArrayList<lecture> loadtimetable=new ArrayList<>();
   ArrayList<ArrayList<lecture>> done=new ArrayList<>();
    private lectureadapter mAdapter;
Spinner spinner;
    String[] weekofdays={
            "Monday","Tuesday","Wednesday","Thursday","Friday" };
    public static ArrayList<lecture> friday=new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.timetable);


                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
databaseReference=firebaseDatabase.getReference();
     //   TextView textView=findViewById(R.id.date);
    //    Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
        Date now =new Date();
        String weekday=simpleDateFormat.format(now);
        spinner=findViewById(R.id.spin);
        ArrayAdapter aa=new ArrayAdapter(this,R.layout.spinner_item,weekofdays);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Toolbar toolbar=findViewById(R.id.lecturetool);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));

        spinner.setAdapter(aa);
        spinner.setSelection(3);
        if(weekday.equals("Monday")) {
            loadtimetable = monday;
            spinner.setSelection(0);
        }
        else
            if(weekday.equals("Tuesday"))
            {   loadtimetable=tuesday;
        spinner.setSelection(1);
    }
        else
            if(weekday.equals("Wednesday"))
            {   loadtimetable=wednesday;
                spinner.setSelection(2);
            }
                else
                    if(weekday.equals("Thursday"))
                    { loadtimetable=thursday;
                        spinner.setSelection(3);
                    }
                else
                    if(weekday.equals("Friday"))
                    {    loadtimetable=friday;
                        spinner.setSelection(4);
                    }
                else
                    {
                        loadtimetable=monday;
                        spinner.setSelection(0);
                    }
      //  textView.setText(weekday);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int z=  spinner.getSelectedItemPosition();
        if(z==0)
            loadtimetable=monday;
        else
        if(z==1)
            loadtimetable=tuesday;
        else
        if(z==2)
            loadtimetable=wednesday;
        else
        if(z==3)
            loadtimetable=thursday;
        else
        if(z==4)
            loadtimetable=friday;
        mAdapter.setFilter(loadtimetable);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});


        /* for(int i=0;i<monday.size();i++)
            Log.i("loggingasda",monday.get(i).getSubject());
        for(int i=0;i<tuesday.size();i++)
            Log.i("loggingasda",tuesday.get(i).getSubject());
        for(int i=0;i<wednesday.size();i++)
            Log.i("loggingasda",wednesday.get(i).getSubject());
        for(int i=0;i<thursday.size();i++)
            Log.i("loggingasda",thursday.get(i).getSubject());
        for(int i=0;i<friday.size();i++)
            Log.i("loggingasda",friday.get(i).getSubject()); */
        RecyclerView recyclerView=findViewById(R.id.recycle);
     //   loadtimetable=newLines(loadtimetable);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter=new lectureadapter(loadtimetable,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
    }

}
