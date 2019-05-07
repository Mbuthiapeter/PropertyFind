package com.friki.mbuthia.propertyfindapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.friki.mbuthia.propertyfindapp.utils.Listing;

public class MainActivity extends AppCompatActivity {
    //ViewPager viewPager;
    //SwipeAdapter adapter;
    //private int[] imageResources = {R.drawable.home2,R.drawable.home1,R.drawable.home3,R.drawable.home4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_lesama);

        /*Drawable drawable = getResources().getDrawable(R.mipmap.ic_services_general_round);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.75),
                (int)(drawable.getIntrinsicHeight()*0.75));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 10f, 10f);
        Button btn = (Button)findViewById(R.id.btnServices);
        btn.setCompoundDrawables(sd.getDrawable(), null, null, null);*/

        /*Resources res = getResources();
        ScaleDrawable sd = (ScaleDrawable) res.getDrawable(R.mipmap.ic_services_general_round);
        Drawable d = sd.getDrawable();

        d.setLevel(1);*/

        /*ImageView iv = new ImageView(this);
        iv.setImageDrawable(sd);
        iv.setAdjustViewBounds(true);
        iv.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        setContentView(iv);*/
    }
    public void toLet(View view) {
        if(isConnected()) {
            writeSharedPreferences();
            Intent intent = new Intent(this, AllProperties.class);
            intent.putExtra(Listing.KEY_LISTING, 1);
            startActivity(intent);
        }else
            Toast.makeText(this,"Oops! No internet connection", Toast.LENGTH_LONG).show();
    }



    public void forSale(View view) {
        if(isConnected()) {
        writeSharedPreferences();
        Intent intent = new Intent(this,AllProperties.class);
        intent.putExtra(Listing.KEY_LISTING,2);
        startActivity(intent);
        }else
            Toast.makeText(this,"Oops! No internet connection", Toast.LENGTH_LONG).show();
    }

    public void toLease(View view) {
        if(isConnected()) {
            writeSharedPreferences();
            Intent intent = new Intent(this,AllProperties.class);
            intent.putExtra(Listing.KEY_LISTING,3);
            startActivity(intent);
        }else
            Toast.makeText(this,"Oops! No internet connection", Toast.LENGTH_LONG).show();
    }

    public void services(View view) {
        if(isConnected()) {
        writeSharedPreferences();
        Intent intent = new Intent(this,ServicesSettings.class);
        intent.putExtra(Listing.KEY_LISTING,1);
        startActivity(intent);
        }else
            Toast.makeText(this,"Oops! No internet connection", Toast.LENGTH_LONG).show();
    }

    private void writeSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_location_key), getString(R.string.pref_location_0_value));
        editor.putString(getString(R.string.pref_listing_key), "0");
        editor.putString(getString(R.string.pref_rent_key), "0");
        editor.putString(getString(R.string.pref_bedroom_key),"0");
        editor.apply();
    }

    private boolean isConnected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }

}
