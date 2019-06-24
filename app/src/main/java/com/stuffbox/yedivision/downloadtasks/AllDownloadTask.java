package com.stuffbox.yedivision.downloadtasks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.stuffbox.yedivision.R;
import com.stuffbox.yedivision.activities.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jasbe on 26-03-2018.
 */

public class AllDownloadTask extends AsyncTask<String, Integer, String> {
    public static String path;
    public static int wait = 1;
    private Context context;
    public int flag = 1;
    public static int fileLength;
    public String from;
    public static long total = 0;
    private PowerManager.WakeLock mWakeLock;
    public static File file;
    public int counter;
    public String blabla;

    public AllDownloadTask(Context context, String file, String x, int a) {
        Log.i("xyzz", "" + a);
        this.context = context;
        blabla = file;
        counter = a;
        from = x;
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
//Log.i("xyz",material.names[i]);
            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            fileLength = connection.getContentLength();
            Log.i("filename", blabla);
            // download the file
            input = connection.getInputStream();
            //  String filename= UUID.randomUUID().toString();
            File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yedivision");
            direct.mkdir();
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yedivision/" + from);
            directory.mkdir();
            Log.i("sojai", blabla);

            output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/yedivision/" + from + "/" + blabla + ".pdf");
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yedivision/" + from + "/" + blabla + ".pdf");
            byte data[] = new byte[4096];
            total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    if (fileLength != file.length())
                        file.delete();
                    file.delete();
                    flag = 0;
                    input.close();
                    return null;
                }
                total += count;
                if (total * 100 / fileLength == 100)
                {if(counter==0)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = "soja";
                        String description = "Download Complete";
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel channel = new NotificationChannel("soja", name, importance);
                        channel.setDescription(description);
                        // Register the channel with the system; you can't change the importance
                        // or other notification behaviors after this
                        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                    }
                    Intent x = new Intent(context, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                    stackBuilder.addNextIntentWithParentStack(x);
                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "soja")
                            .setSmallIcon(R.drawable.ic_stat_yedivision)
                            .setContentTitle("Download")
                            .setContentText("Download Complete")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                                    + "://" + "com.stuffbox.yedivision" + "/raw/notification1"))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(1, mBuilder.build());
                }
                 }
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
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
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false

    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();

    }
}

