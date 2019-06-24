package com.stuffbox.yedivision.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stuffbox.yedivision.R;

/**
 * Created by jasbe on 05-04-2018.
 */

public class about extends AppCompatActivity {
    protected  void onCreate(Bundle savedinstancestate)
    {        setContentView(R.layout.about);

        super.onCreate(savedinstancestate);
    }
    public void fb(View view)
    {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }
    public void insta(View view)
    {
        Uri uri = Uri.parse("http://instagram.com/_u/just_a_tired_human_being");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/just_a_tired_human_being")));
        }
    }
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + "https://www.facebook.com/balvinder12";
            } else { //older versions of fb app
                return "fb://page/" + "balvinder12";
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "https://www.facebook.com/balvinder12"; //normal web url
        }
    }
    public void sayat(View view)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://sayat.me/bsingh"));
        startActivity(intent);
    }
}
