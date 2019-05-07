package com.friki.mbuthia.propertyfindapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.friki.mbuthia.propertyfindapp.databinding.ActivityPropertyViewBinding;
import com.friki.mbuthia.propertyfindapp.utils.RoundedTransformation;
import com.sembozdemir.viewpagerarrowindicator.library.ViewPagerArrowIndicator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_AMOUNT;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_BEDROOMS;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_CAR_PARKS;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_DESCRIPTION;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_EMAIL;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_ID;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_LOCATION;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_MANAGER;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_PHONE;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_PHOTO;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_PROPERTY_LISTING;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_PROPERTY_NOTATION;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_PROPERTY_TYPE;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_SHOWERS;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_STATUS;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_UPLOADS;
import static com.friki.mbuthia.propertyfindapp.AllProperties.EXTRA_URL;
import static com.friki.mbuthia.propertyfindapp.utils.Listing.KEY_URL;

public class PropertyView extends AppCompatActivity {
    ActivityPropertyViewBinding mBinding;
    Button call, email;
    ViewPager viewPager;
    SwipeAdapter adapter;
    ViewPagerArrowIndicator viewPagerArrowIndicator;

    String manager_phone;
    String manager_email;
    String property_id;

    ImageView manager_photo;
    String manager_photo_url;

    ImageButton leftNav, rightNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_view);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_property_view);
        manager_photo = (ImageView)findViewById(R.id.owner_photo);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String uploads = extras.getString(EXTRA_UPLOADS);
        String propertyNotation = extras.getString(EXTRA_PROPERTY_NOTATION);
        property_id = propertyNotation+extras.getInt(EXTRA_ID);
        String property_listing = extras.getString(EXTRA_PROPERTY_LISTING);
        int status = extras.getInt(EXTRA_STATUS);

        String url = extras.getString(EXTRA_URL);
        String property_type = extras.getString(EXTRA_PROPERTY_TYPE);
        long amount = extras.getLong(EXTRA_AMOUNT);
        final String location = extras.getString(EXTRA_LOCATION);
        int bedrooms = extras.getInt(EXTRA_BEDROOMS);
        int showers = extras.getInt(EXTRA_SHOWERS);
        int car_parks = extras.getInt(EXTRA_CAR_PARKS);
        String description = extras.getString(EXTRA_DESCRIPTION);

        String manager = extras.getString(EXTRA_MANAGER);
        manager_phone = extras.getString(EXTRA_PHONE);
        manager_email = extras.getString(EXTRA_EMAIL);
        manager_photo_url = extras.getString(EXTRA_PHOTO);


        JSONArray newJArray =null;
        try {
            newJArray = new JSONArray(uploads);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> imageResources = new ArrayList<String>();
        imageResources.add(url);
        for(int i =0; i < newJArray.length(); i++){
            try {
                imageResources.add(KEY_URL+newJArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        leftNav = (ImageButton)findViewById(R.id.left_nav);
        rightNav = (ImageButton)findViewById(R.id.right_nav);



        viewPager = (ViewPager)findViewById(R.id.viewPager);

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }*/
                viewPager.arrowScroll(ViewPager.FOCUS_LEFT);
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);*/
                viewPager.arrowScroll(ViewPager.FOCUS_RIGHT);
            }
        });

       // viewPagerArrowIndicator = (ViewPagerArrowIndicator) findViewById(R.id.viewPagerArrowIndicator);
       // viewPagerArrowIndicator.setArrowIndicatorRes(R.mipmap.ic_left_indicator,R.mipmap.ic_right_indicator);
        adapter = new SwipeAdapter(this,imageResources);
        viewPager.setAdapter(adapter);
       // viewPagerArrowIndicator.bind(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Propertyfind");
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.background_theme));

        NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
        String price ="Ksh " +nf.format(amount);

        mBinding.propertyType.setText(property_type+ " in "+ location);
        mBinding.propertyPrice.setText(price);
        mBinding.bedrooms.setText(bedrooms+"");
        mBinding.bathrooms.setText(showers+"");
        mBinding.carParks.setText(car_parks+"");
        mBinding.description.setText(description);
        mBinding.ownerName.setText("Manager: " +manager );

        Picasso.get().load(manager_photo_url).fit().centerInside()
                .transform(new RoundedTransformation())
                .into(manager_photo);

        call = (Button)findViewById(R.id.btn_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", manager_phone, null));
                startActivity(intent);
            }
        });

        email = (Button)findViewById(R.id.btn_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = "Propertyfind Listing "+property_id;
                String message = "Please send more information about the property reference " +property_id+ " in "+location+".";

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ manager_email});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

    }

}
