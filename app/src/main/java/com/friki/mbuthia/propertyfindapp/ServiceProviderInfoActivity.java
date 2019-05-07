package com.friki.mbuthia.propertyfindapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.friki.mbuthia.propertyfindapp.databinding.ActivityServiceProviderInfoBinding;
import com.squareup.picasso.Picasso;

import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_CLIENTS;
import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_EXPERIENCE;
import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_INFO;
import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_LOCALITY;
import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_NAME;
import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_PHONE_NUMBER;
import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_PHOTO;
import static com.friki.mbuthia.propertyfindapp.Services.EXTRA_RATING;

public class ServiceProviderInfoActivity extends AppCompatActivity {
    ActivityServiceProviderInfoBinding mBinding;

    ImageView profileImage;

    String name, phone, info,photo, locality, experience, clients;
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_info);
       /* TextView tclients =(TextView) findViewById(R.id.tvClients);
        TextView texperience =(TextView) findViewById(R.id.tvExperience);*/

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_service_provider_info);
        profileImage = (ImageView)findViewById(R.id.providerImgView);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

         name = extras.getString(EXTRA_NAME);
         phone = extras.getString(EXTRA_PHONE_NUMBER);
         rating = extras.getInt(EXTRA_RATING);
         info = extras.getString(EXTRA_INFO);
         photo = extras.getString(EXTRA_PHOTO);
         locality = extras.getString(EXTRA_LOCALITY);
         experience = extras.getString(EXTRA_EXPERIENCE);
         clients = extras.getString(EXTRA_CLIENTS);

        //Log.d("ServiceProvider", "Marina: "+clients);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.get()
                .load(photo)
                .fit()
                .centerInside()
                .into(profileImage);


        mBinding.tvName.setText(name);
        mBinding.ratingBar.setRating(rating);
        mBinding.tvLocality.setText(locality);
        mBinding.tvExperience.setText(experience);
        mBinding.tvClients.setText(clients);
        mBinding.tvInfo.setText(info);
    }

    public void contact(View view){
        final String message = "Hello, I got this contact from Propertyfind App and I would like to have your services.";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Want to contact "+name);
                alertDialogBuilder.setPositiveButton("Call",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                                startActivity(intent);                            }
                        });
                alertDialogBuilder.setNegativeButton("Send Text",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                                smsIntent.setData(Uri.parse("smsto:"));
                                smsIntent.setType("vnd.android-dir/mms-sms");
                                smsIntent.putExtra("address"  , new String (phone));
                                smsIntent.putExtra("sms_body"  , message);

                                try {
                                    startActivity(smsIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(ServiceProviderInfoActivity.this,
                                            "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
                                }
                            }
                });
                alertDialogBuilder.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void feedback(View view){
        final String lessamaPhone = "+254700215848";
        final String feedbackMessage  = "Feedback to Propertyfind regarding "+name+"'s services:";
        final String emailSubject = "Feedback For "+name;
        final String managerEmail = "info@lesama.co.ke";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Choose text or email");
        alertDialogBuilder.setPositiveButton("Email",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ managerEmail});
                        email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                        email.putExtra(Intent.EXTRA_TEXT, feedbackMessage);

                        //need this to prompts email client only
                        email.setType("message/rfc822");

                        startActivity(Intent.createChooser(email, "Choose an Email client :"));
                    }
                });
        alertDialogBuilder.setNegativeButton("Text",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                        smsIntent.setData(Uri.parse("smsto:"));
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address"  , new String (lessamaPhone));
                        smsIntent.putExtra("sms_body"  , feedbackMessage);

                        try {
                            startActivity(smsIntent);
                            finish();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(ServiceProviderInfoActivity.this,
                                    "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialogBuilder.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
