package com.stuffbox.yedivision.downloadtasks;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.stuffbox.yedivision.BuildConfig;
import com.stuffbox.yedivision.activities.DocumentAdder;
import com.stuffbox.yedivision.adapters.DocumentAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DocumentDownloadTask extends AsyncTask<String, Integer, String> {
    public  static String path;
    private Context context;
    public int flag=1;
    public static int  fileLength;
    public static  long total=0;
    private PowerManager.WakeLock mWakeLock;
    public static File file;
    String type;
    public DocumentDownloadTask(Context context,String type) {
        this.context = context;
        this.type= type;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            //  String filename= UUID.randomUUID().toString();
            String filename= DocumentAdapter.name;
            final File externalStoragePublicDirectory = Environment.getExternalStorageDirectory();

            File direct=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yedivision/");
            direct.mkdirs();
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yedivision/"+type+"/");
            directory.mkdirs();
            output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/yedivision/"+type+"/"+filename+".pdf");
            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yedivision/"+type+"/"+filename+".pdf");
            Log.i("soja", Uri.fromFile(file).toString());
            path= filename;
            byte data[] = new byte[4096];
            total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled())
                {
                    if(fileLength!=file.length())
                        file.delete();
                    file.delete();
                    flag=0;
                    input.close();
                    return null;
                }
                total += count;
                if(total * 100 / fileLength==100)
                    flag=2;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                Log.i("Soja",String.valueOf(total * 100 / fileLength));

                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the pofwer button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        DocumentAdapter.mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        DocumentAdapter.mProgressDialog.setIndeterminate(false);
        DocumentAdapter.mProgressDialog.setMax(100);
        DocumentAdapter.mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        DocumentAdapter.mProgressDialog.dismiss();

        if (result != null)

            Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
        else{


            if(flag==2){
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
                Intent i=new Intent();
                //         Log.i("soja",DownloadTask.path);
                //       Log.i("soja",Environment.getExternalStorageDirectory().getPath()+DownloadTask.path+".pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW);

                //    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setDataAndType(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider",file),"application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try
                {
                    context.startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(context, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
                }
            }}
    }}
