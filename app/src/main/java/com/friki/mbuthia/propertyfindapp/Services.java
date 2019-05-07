package com.friki.mbuthia.propertyfindapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.friki.mbuthia.propertyfindapp.model.Technician;
import com.friki.mbuthia.propertyfindapp.utils.Listing;
import com.friki.mbuthia.propertyfindapp.utils.MyDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.friki.mbuthia.propertyfindapp.ServicesSettings.EXTRA_SERVICE;

public class Services extends AppCompatActivity implements ServiceProvidersAdapter.OnItemClickListener {
    private static final String TAG = Services.class.getSimpleName();
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_PHONE_NUMBER = "phone";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_LOCALITY = "locality";
    public static final String EXTRA_INFO = "info";
    public static final String EXTRA_EXPERIENCE = "experience";
    public static final String EXTRA_CLIENTS = "clients";
    public static final String EXTRA_PHOTO = "photo";

    private RecyclerView mRecyclerView;
    private ServiceProvidersAdapter mViewAdapter;
    private ArrayList<Technician> mTechnicianList;
    private RequestQueue mRequestQueue;
    private ProgressBar mProgressBar;

    TextView mNumberOfProviders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressBar = findViewById(R.id.pbService);
        mProgressBar.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        int serviceId = extras.getInt(EXTRA_SERVICE);
        mNumberOfProviders = (TextView)findViewById(R.id.tvTechnicians);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 72));

        mTechnicianList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(serviceId);
    }

    private void parseJSON(int serviceId) {
        String url = Listing.KEY_URL + "fetchTechnicians.php?serviceId=" +serviceId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       Log.d(TAG, "onResponse: "+response.toString());
                        try {
                            // Checking for SUCCESS TAG
                            int success = response.getInt(Listing.TAG_SUCCESS);
                            if (success == 1) {
                                JSONArray jsonArray = response.getJSONArray(Listing.TAG_TECHNICIANS);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject serviceProvider = jsonArray.getJSONObject(i);

                                    int id = serviceProvider.getInt("id");
                                    String imageUrl = Listing.KEY_URL + serviceProvider.getString(EXTRA_PHOTO);
                                    String name = serviceProvider.getString(EXTRA_NAME);
                                    String phone = serviceProvider.getString(EXTRA_PHONE_NUMBER);
                                    String providerInfo = serviceProvider.getString(EXTRA_INFO);
                                    String locality = serviceProvider.getString(EXTRA_LOCALITY);
                                    int rating = serviceProvider.getInt(EXTRA_RATING);
                                    String experience = serviceProvider.getString(EXTRA_EXPERIENCE);
                                    String clients = serviceProvider.getString(EXTRA_CLIENTS);

                                    mTechnicianList.add(new Technician(id, name, phone, providerInfo, rating, imageUrl, locality, experience, clients));
                                }

                                mViewAdapter = new ServiceProvidersAdapter(getApplicationContext(), mTechnicianList);
                                mRecyclerView.setAdapter(mViewAdapter);
                                mViewAdapter.setOnItemClickListener(Services.this);
                                mNumberOfProviders.setText(getAdpterSize()+" result(s) found");
                                mProgressBar.setVisibility(View.GONE);

                                mViewAdapter.notifyDataSetChanged();

                            }else{
                                //Toast.makeText(getApplicationContext(),"No persons found", Toast.LENGTH_LONG).show();
                                mNumberOfProviders.setText("This service is coming soon" );
                                mProgressBar.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Oops! Something weird happened", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),ServicesSettings.class);
                startActivity(i);
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy( 6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);


    }

    private int getAdpterSize() {
        int count = 0;
        if (mViewAdapter != null) {
            count = mViewAdapter.getItemCount();
        }
        return count;    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.service_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                //Intent startSettingsActivity = new Intent(this, ServicePreferenceSetting.class);
                //startActivity(startSettingsActivity);
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Technician clickedItem = mTechnicianList.get(position);
        Intent i = new Intent(this,ServiceProviderInfoActivity.class);
        i.putExtra(EXTRA_NAME,clickedItem.getName());
        i.putExtra(EXTRA_PHONE_NUMBER,clickedItem.getPhone());
        i.putExtra(EXTRA_RATING,clickedItem.getRating());
        i.putExtra(EXTRA_LOCALITY,clickedItem.getLocality());
        i.putExtra(EXTRA_INFO,clickedItem.getProviderInfo());
        i.putExtra(EXTRA_EXPERIENCE,clickedItem.getExperience());
        i.putExtra(EXTRA_CLIENTS,clickedItem.getClients());
        i.putExtra(EXTRA_PHOTO,clickedItem.getPhotoUrl());

        startActivity(i);


    }
}
