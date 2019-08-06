package com.stuffbox.yedivision.adapters;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stuffbox.yedivision.BuildConfig;
import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.downloadtasks.DocumentDownloadTask;
import com.stuffbox.yedivision.models.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.MyViewHolder> {
    private  String type;
    private List<Document> documents;
    private final Context context;
    public static String name;

    public static ProgressDialog mProgressDialog;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        Button open;
        public MyViewHolder(View view) {
            super(view);
           description = view.findViewById(R.id.description);
           open = view.findViewById(R.id.but);
        }
    }


    public DocumentAdapter(List<Document> documents, Context context,String type) {
        this.documents = documents;
        this.context = context;
        this.type =type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

       holder.description.setText(documents.get(position).getName());
       holder.open.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Document document = documents.get(holder.getAdapterPosition());
               File direct=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yedivision");
               direct.mkdir();
               final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yedivision/"+type+"/" + document.getName() + ".pdf");
               if (!file.exists()) {
                   mProgressDialog = new ProgressDialog(context);
                   mProgressDialog.setMessage("Downloading");
                   mProgressDialog.setIndeterminate(true);
                   mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                   mProgressDialog.setCancelable(false );
                    name = documents.get(holder.getAdapterPosition()).getName();

                   final DocumentDownloadTask downloadTask = new DocumentDownloadTask(context,type);
                   downloadTask.execute(document.getLink());

                   mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                       @Override
                       public void onCancel(DialogInterface dialog) {
                           downloadTask.cancel(true);
                           file.delete();
                       }
                   });
               } else {
                   Intent i = new Intent();
                   //         Log.i("soja",DownloadTask.path);
                   //       Log.i("soja",Environment.getExternalStorageDirectory().getPath()+DownloadTask.path+".pdf");
                   Intent intent = new Intent(Intent.ACTION_VIEW);

                   intent.setDataAndType(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider",file),"application/pdf");
                   intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                   try {
                       context.startActivity(intent);
                   } catch (ActivityNotFoundException e) {
                       Toast.makeText(context, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }
    public void setFilter(ArrayList<Document> newlist )
    {
        documents=new ArrayList<>();
        documents.addAll(newlist);
        notifyDataSetChanged();
    }
}