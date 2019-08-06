package com.stuffbox.yedivision.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.activities.DocumentLister;
import com.stuffbox.yedivision.models.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {
    Context context;
    ArrayList<Subject> subjectList;
    String  type;
   public  SubjectAdapter(Context context, ArrayList<Subject> subjectList,String type)
    {
        this.context=context;
        this.subjectList=subjectList;
        this.type=type;
    }
    public  SubjectAdapter()
    {}

    public class MyViewHolder extends  RecyclerView.ViewHolder
    {
        TextView subject ;
        public MyViewHolder(@NonNull View view) {
            super(view);
            subject= view.findViewById(R.id.currentsubject);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subjectrecylerviewlayout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,  int position) {
        holder.subject.setText(subjectList.get(position).getSubjectname());
        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DocumentLister.class);
                intent.putExtra("subject",subjectList.get(holder.getAdapterPosition()));
                intent.putExtra("type",type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
