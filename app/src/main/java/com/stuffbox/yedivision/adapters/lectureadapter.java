package com.stuffbox.yedivision.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.models.lecture;

import java.util.ArrayList;
import java.util.List;

public class lectureadapter extends RecyclerView.Adapter<lectureadapter.MyViewHolder> {
    private List<lecture>  lecturelist;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameofteacher,subject,room,time;
        public CardView cardView;
        public MyViewHolder(View view)
        {
            super(view);
            nameofteacher=view.findViewById(R.id.nameofteacher);
            room=view.findViewById(R.id.room);
            subject=view.findViewById(R.id.subject);
            time=view.findViewById(R.id.time);
            cardView=view.findViewById(R.id.card_view);
        }

    }
    public lectureadapter(List lecturelist,Context c)
    {
        this.lecturelist=lecturelist;
        context=c;
    }
    @Override
    public lectureadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerfortimetable, parent, false);

        return new lectureadapter.MyViewHolder(itemView);
    }
@Override
    public void onBindViewHolder(MyViewHolder holder,int position)
{
    lecture l=lecturelist.get(position);
    holder.nameofteacher.setText("Name : "+ l.getTeacher());
    holder.room.setText("Room No : "+l.getRoom());
    holder.time.setText("Time : "+l.getTime());
    holder.subject.setText("Subject : "+l.getSubject());

}
@Override
    public int getItemCount()
{
    return lecturelist.size();
}
    public void setFilter(ArrayList<lecture> newlist )
    {
        lecturelist=new ArrayList<>();
        lecturelist.addAll(newlist);
        notifyDataSetChanged();
    }

}
