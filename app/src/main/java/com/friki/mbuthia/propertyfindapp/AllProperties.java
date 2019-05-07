package com.friki.mbuthia.propertyfindapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.friki.mbuthia.propertyfindapp.database.ListingEntry;
import com.friki.mbuthia.propertyfindapp.database.LocationDatabase;
import com.friki.mbuthia.propertyfindapp.database.LocationEntry;
import com.friki.mbuthia.propertyfindapp.model.Property;
import com.friki.mbuthia.propertyfindapp.utils.AppExecutors;
import com.friki.mbuthia.propertyfindapp.utils.Listing;
import com.friki.mbuthia.propertyfindapp.utils.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static com.friki.mbuthia.propertyfindapp.utils.Listing.TAG_PROPERTIES;

public class AllProperties extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener,SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_URL = "propertyUrl";
    public static final String EXTRA_PROPERTY_NOTATION = "property_notation";
    public static final String EXTRA_PROPERTY_TYPE = "property_type";
    public static final String EXTRA_PROPERTY_TYPE_ID = "property_type_id";
    public static final String EXTRA_PROPERTY_LISTING = "property_listing";
    public static final String EXTRA_AMOUNT = "amount";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_GENERAL_LOCATION = "sub_county";
    public static final String EXTRA_BEDROOMS = "bedrooms";
    public static final String EXTRA_SHOWERS = "showers";
    public static final String EXTRA_CAR_PARKS = "carParks";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_MANAGER = "manager";
    public static final String EXTRA_PHONE = "manager_phone";
    public static final String EXTRA_EMAIL = "manager_email";
    public static final String EXTRA_PHOTO = "manager_photo";
    public static final String EXTRA_UPLOADS = "uploads";

    private static final String TAG = AllProperties.class.getSimpleName();
    private LocationDatabase mDb;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mViewAdapter;
    private ArrayList<Property> mPropertyList;
    private ArrayList<Property> mSortedList;
    private ArrayList<Location> mLocationList;

    private ArrayList<ListingEntry> mTypeList;
    private RequestQueue mRequestQueue;
    private ProgressBar mProgressBar;
    private Listing mItem;
    Property prop;

    private int listing;
    private String locationChoice;
    private String listingChoice;
    private String maxPrice;
    private String bedroomChoice;

    TextView mNumberOfItems;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_properties);

        mItem = new Listing();
        mDb = LocationDatabase.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setTitle("Toolbar example");
        toolbar.setSubtitle("Search using the icon on the right");

        mNumberOfItems = (TextView)findViewById(R.id.number_of_contents);

        listing= getIntent().getIntExtra(Listing.KEY_LISTING,0);

        mProgressBar = findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.VISIBLE);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPropertyList = new ArrayList<>();
        mSortedList = new ArrayList<>();
        mLocationList = new ArrayList<>();

        mTypeList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        setupSharedPreferences();

        parseJSON(listing);
        }



    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        locationChoice = sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_0_value));
        listingChoice = sharedPreferences.getString(getString(R.string.pref_listing_key), getString(R.string.pref_listing_value));
        maxPrice = sharedPreferences.getString(getString(R.string.pref_rent_key), "0");
        bedroomChoice = sharedPreferences.getString(getString(R.string.pref_bedroom_key), "0");
                       // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }



    private void parseJSON(int listing) {
        String url = Listing.KEY_URL + "fetchAllProperties.php?listing="+listing;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG_PROPERTIES, "onResponse: "+response.toString());

                        try {
                            // Checking for SUCCESS TAG
                            int success = response.getInt(Listing.TAG_SUCCESS);
                            if (success == 1) {
                                JSONArray jsonArray = response.getJSONArray(TAG_PROPERTIES);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject property = jsonArray.getJSONObject(i);

                                        int id = property.getInt("id");
                                        String imageUrl = Listing.KEY_URL + property.getString("propertyUrl");
                                        String propertyNotation = property.getString(EXTRA_PROPERTY_NOTATION);
                                        String propertyType = property.getString("property_type");
                                        int propertyTypeId = property.getInt(EXTRA_PROPERTY_TYPE_ID);
                                        String location = property.getString("location");
                                        long amount = property.getInt("amount");
                                        int bedrooms = property.getInt("bedrooms");
                                        int showers = property.getInt("showers");
                                        int carParks = property.getInt("carParks");
                                        String description = property.getString("description");
                                        int status = property.getInt("status");

                                        String manager = property.getString(EXTRA_MANAGER);
                                        String manager_phone = property.getString(EXTRA_PHONE);
                                        String manager_email = property.getString(EXTRA_EMAIL);
                                        String manager_photo = Listing.KEY_URL + property.getString(EXTRA_PHOTO);

                                        JSONArray uploads = property.getJSONArray("uploads");

                                    mPropertyList.add(new Property(id, propertyNotation, propertyTypeId, propertyType, location, amount,
                                            bedrooms, showers, carParks, description, status, manager,manager_phone,
                                            manager_email,manager_photo, imageUrl, uploads));
                                }
                                JSONArray jsonTypes = response.getJSONArray("types");
                                for (int j = 0; j < jsonTypes.length(); j++){
                                    JSONObject type = jsonTypes.getJSONObject(j);

                                    int typeId = type.getInt("typeId");
                                    String pType = type.getString("type");
                                    mTypeList.add(new ListingEntry(typeId,pType));
                                }
                                sortList(mPropertyList);
                                saveLocations(mPropertyList);
                                saveTypes(mTypeList);

                                mViewAdapter.notifyDataSetChanged();

                            }else{
                                Toast.makeText(getApplicationContext(),"No Property for this listing yet", Toast.LENGTH_LONG).show();
                                mProgressBar.setVisibility(View.GONE);
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Oops! Please check your internet connection.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy( 6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);
    }



    private void sortList(ArrayList<Property> mOriginalList) {
        mSortedList = sortWithLocation(mOriginalList);
        mSortedList = sortWithType(mSortedList);
        mSortedList = sortWithBedrooms(mSortedList);
        mSortedList = sortWithAmount(mSortedList);

       // mSortedList = sortWithLocation(sortWithType(sortWithBedrooms(sortWithAmount(mOriginalList))));

        /*for(int j = 0; j < mPropertyList.size(); j++) {
            prop = mPropertyList.get(j);
            //All preferences set

            if (!maxPrice.equals("0") && !bedroomChoice.equals("0") && !locationChoice.equals(getString(R.string.pref_location_0_value)) && !listingChoice.equals(getString(R.string.pref_listing_value))) {
                if ((minAmount <= prop.getAmount() && prop.getAmount() <= maxAmount) && prop.getBedrooms() >= numberOfBedrooms && prop.getLocation().equals(locationChoice) && prop.getPropertyType().equals(listingChoice) )  {
                    addToList();
                }else{
                    if(j==mPropertyList.size()-1) {
                    Toast.makeText(getApplicationContext(), "No property found with these specifications", Toast.LENGTH_LONG).show();
                    addToList();
                        }
                }
            }
            //Rent and Bedroom set
            else if (!maxPrice.equals("0") && !bedroomChoice.equals("0") && locationChoice.equals(getString(R.string.pref_location_0_value))) {

                if ((minAmount <= prop.getAmount() && prop.getAmount() <= maxAmount) && prop.getBedrooms() >= numberOfBedrooms ) {
                   addToList();
                }else{
                    if(j==mPropertyList.size()-1) {
                        Toast.makeText(getApplicationContext(), "No property found with these specifications", Toast.LENGTH_LONG).show();
                        addToList();
                    }
                }
            } else if (!maxPrice.equals("0") && bedroomChoice.equals("0") && locationChoice.equals(getString(R.string.pref_location_0_value))) {
                //Rent only set
                if ((minAmount <= prop.getAmount() && prop.getAmount() <= maxAmount) ) {
                    addToList();
                }else{
                    if(j==mPropertyList.size()-1) {
                        Toast.makeText(getApplicationContext(), "No property found with these specifications", Toast.LENGTH_LONG).show();
                        addToList();
                    }
                }
            } else if (maxPrice.equals("0") && !bedroomChoice.equals("0") && locationChoice.equals(getString(R.string.pref_location_0_value))) {
                //Bedroom only set
                if (prop.getBedrooms() >= numberOfBedrooms ) {
                    addToList();
                }else{
                    if(j==mPropertyList.size()-1) {
                        Toast.makeText(getApplicationContext(), "No property found with these specifications", Toast.LENGTH_LONG).show();
                        addToList();
                    }
                }
            } else if (maxPrice.equals("0") && bedroomChoice.equals("0") && !locationChoice.equals(getString(R.string.pref_location_0_value))) {
                //Location only set
                if (prop.getGeneralLocation().equals(locationChoice) ) {
                    addToList();
                }else{
                    if(j==mPropertyList.size()-1) {
                        Toast.makeText(getApplicationContext(), "No property found with these specifications", Toast.LENGTH_LONG).show();
                        addToList();
                    }
                }
            } else if (maxPrice.equals("0") && !bedroomChoice.equals("0") && !locationChoice.equals(getString(R.string.pref_location_0_value))) {
                //Bedroom and location set
                if (prop.getBedrooms() >= numberOfBedrooms && prop.getGeneralLocation().equals(locationChoice) ) {
                    addToList();
                }else{
                    if(j==mPropertyList.size()-1) {
                        Toast.makeText(getApplicationContext(), "No property found with these specifications", Toast.LENGTH_LONG).show();
                        addToList();
                    }
                }

            } else if (!maxPrice.equals("0") && bedroomChoice.equals("0") && !locationChoice.equals(getString(R.string.pref_location_0_value))) {
                //Rent and location set
                if ((minAmount <= prop.getAmount() && prop.getAmount() <= maxAmount) && prop.getGeneralLocation().equals(locationChoice) ) {
                    addToList();
                }else{
                    if(j==mPropertyList.size()-1) {
                        Toast.makeText(getApplicationContext(), "No property found with these specifications", Toast.LENGTH_LONG).show();
                        addToList();
                    }
                }
            } else if (maxPrice.equals("0") && bedroomChoice.equals("0") && locationChoice.equals(getString(R.string.pref_location_0_value))) {
                // None set
                    addToList();
            }else{
                Toast.makeText(getApplicationContext(),"No property found with these specifications",Toast.LENGTH_LONG).show();
                addToList();
            }
        }*/
        mViewAdapter = new RecyclerViewAdapter(AllProperties.this, mSortedList);
        mRecyclerView.setAdapter(mViewAdapter);
        mViewAdapter.setOnItemClickListener(AllProperties.this);
        mNumberOfItems.setText(getAdpterSize()+" result(s) found");
        mProgressBar.setVisibility(View.GONE);
    }

    private ArrayList<Property> sortWithLocation(ArrayList<Property> listToSort){
        ArrayList<Property>mListAfterSort = new ArrayList<>();
        if (!locationChoice.equals(getString(R.string.pref_location_0_value))) {
            if(!mListAfterSort.isEmpty()){
                mListAfterSort.clear();
            }
            for(int j = 0; j < listToSort.size(); j++) {
                prop = listToSort.get(j);
                if (prop.getLocation().equals(locationChoice)) {
                    //property pass the test
                    addToList(prop,mListAfterSort);
                    /*int id = prop.getId();
                    String imageUrl = prop.getmImageUrl();
                    String propertyType = prop.getPropertyType();
                    String location =prop.getLocation();
                    long amount = prop.getAmount();
                    int bedrooms = prop.getBedrooms();
                    int showers = prop.getShowers();
                    int carParks = prop.getCar_parks();
                    String description = prop.getDescription();
                    int status = prop.getStatus();
                    String manager = prop.getManager();
                    String manager_phone = prop.getManager_phone();
                    String manager_email = prop.getManager_email();
                    String manager_photo = prop.getManager_photo();
                    JSONArray uploads = prop.getUploads();

                    mListAfterSort.add(new Property(id, propertyType, location, amount,
                            bedrooms, showers, carParks, description, status, manager,manager_phone,
                            manager_email,manager_photo, imageUrl, uploads));*/
                } else {
                    //property fails the test
                }
            }
            return mListAfterSort;
        }
        // Location not set
        else{
            return listToSort;
        }
    }

    private ArrayList<Property> sortWithType(ArrayList<Property> listToSort){
        ArrayList<Property>mListAfterSort = new ArrayList<>();
        int typeId = mItem.getSelectedType(listingChoice);
        if (!listingChoice.equals("0")) {
            if(!mListAfterSort.isEmpty()){
                mListAfterSort.clear();
            }
            for(int j = 0; j < listToSort.size(); j++) {
                prop = listToSort.get(j);
                if (prop.getPropertyTypeId() == typeId) {
                    //property pass the test
                    addToList(prop,mListAfterSort);
                } else {
                    //property fails the test
                }
            }
            return mListAfterSort;
        }
        //  not set
        else{
            return listToSort;
        }
    }

    private ArrayList<Property> sortWithBedrooms(ArrayList<Property> listToSort){
        ArrayList<Property>mListAfterSort = new ArrayList<>();
        int numberOfBedrooms = mItem.getSelectedBedRooms(bedroomChoice);
        if (!bedroomChoice.equals("0")) {
            if(!mListAfterSort.isEmpty()){
                mListAfterSort.clear();
            }
            for(int j = 0; j < listToSort.size(); j++) {
                prop = listToSort.get(j);
                if (prop.getBedrooms() >= numberOfBedrooms) {
                    //property pass the test
                    addToList(prop,mListAfterSort);
                } else {
                    //property fails the test
                }
            }
            return mListAfterSort;
        }
        // not set
        else{
            return listToSort;
        }
    }

    private ArrayList<Property> sortWithAmount(ArrayList<Property> listToSort){
        ArrayList<Property>mListAfterSort = new ArrayList<>();
        long maxAmount = mItem.getMaxAmount(maxPrice);
        long minAmount = mItem.getMinAmount(maxPrice);

        if (!maxPrice.equals("0")) {
            if(!mListAfterSort.isEmpty()){
                mListAfterSort.clear();
            }
            for(int j = 0; j < listToSort.size(); j++) {
                prop = listToSort.get(j);
                if (minAmount <= prop.getAmount() && prop.getAmount() <= maxAmount    ) {
                    //property pass the test
                    addToList(prop,mListAfterSort);
                } else {
                    //property fails the test
                }
            }
            return mListAfterSort;
        }
        // not set
        else{
            return listToSort;
        }
    }

    private int getAdpterSize() {
        int count = 0;
        if (mViewAdapter != null) {
            count = mViewAdapter.getItemCount();
        }
        return count;    }


    private void addToList(Property property,ArrayList<Property>mListAfterSort) {
        int id = property.getId();
        String imageUrl = property.getmImageUrl();
        String propertyNotation = property.getPropertyNotation();
        int propertyTypeId = property.getPropertyTypeId();
        String propertyType = property.getPropertyType();
        String location =property.getLocation();
        long amount = property.getAmount();
        int bedrooms = property.getBedrooms();
        int showers = property.getShowers();
        int carParks = property.getCar_parks();
        String description = property.getDescription();
        int status = property.getStatus();
        String manager = property.getManager();
        String manager_phone = property.getManager_phone();
        String manager_email = property.getManager_email();
        String manager_photo = property.getManager_photo();
        JSONArray uploads = property.getUploads();

        mListAfterSort.add(new Property(id, propertyNotation, propertyTypeId, propertyType, location, amount,
                bedrooms, showers, carParks, description, status, manager,manager_phone,
                manager_email,manager_photo, imageUrl, uploads));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_location_key))) {
           locationChoice = sharedPreferences.getString(key, getResources().getString(R.string.pref_location_0_value));
        }else if(key.equals(getString(R.string.pref_rent_key))){
            maxPrice = sharedPreferences.getString(key, "0");
        }else if(key.equals(getString(R.string.pref_bedroom_key))){
            bedroomChoice = sharedPreferences.getString(key, "0");
        }else if(key.equals(getString(R.string.pref_listing_key))){
            listingChoice = sharedPreferences.getString(key, "0");
        }
        sortList(mPropertyList);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, PropertyView.class);
        Property clickedItem = mSortedList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_PROPERTY_NOTATION, clickedItem.getPropertyNotation());
        detailIntent.putExtra(EXTRA_PROPERTY_TYPE, clickedItem.getPropertyType());
        detailIntent.putExtra(EXTRA_PROPERTY_LISTING, clickedItem.getPropertyListing());
        detailIntent.putExtra(EXTRA_AMOUNT, clickedItem.getAmount());
        detailIntent.putExtra(EXTRA_LOCATION, clickedItem.getLocation());
        detailIntent.putExtra(EXTRA_BEDROOMS, clickedItem.getBedrooms());
        detailIntent.putExtra(EXTRA_SHOWERS, clickedItem.getShowers());
        detailIntent.putExtra(EXTRA_CAR_PARKS, clickedItem.getCar_parks());
        detailIntent.putExtra(EXTRA_STATUS, clickedItem.getStatus());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getDescription());

        detailIntent.putExtra(EXTRA_MANAGER, clickedItem.getManager());
        detailIntent.putExtra(EXTRA_PHONE, clickedItem.getManager_phone());
        detailIntent.putExtra(EXTRA_EMAIL, clickedItem.getManager_email());
        detailIntent.putExtra(EXTRA_PHOTO, clickedItem.getManager_photo());

        detailIntent.putExtra(EXTRA_UPLOADS, clickedItem.getUploads().toString());
        startActivity(detailIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tune:
                Intent startSettingsActivity = new Intent(this, PreferencesSettings.class);
                startActivity(startSettingsActivity);
                return true;
            case R.id.services:
                Intent intent = new Intent(this, ServicesSettings.class);
                startActivity(intent);
                return true;

            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);


    }

    public void saveLocations(final ArrayList<Property>... locationList) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.locationDao().nukeEntries();
                for (ArrayList<Property> params : locationList) {
                    String[] locs = new String[params.size()+1];
                    if(params.size() > 0) {
                        for (int j = 0; j < params.size(); j++) {
                            prop = params.get(j);
                            String location = prop.getLocation();
                            locs[j] = location;
                        }
                        String[] array = new HashSet<String>(Arrays.asList(locs)).toArray(new String[0]);
                        int currentSize = array.length;
                        int newSize = currentSize + 1;
                        String[] newArray = new String[ newSize ];
                        for (int i=0; i < currentSize; i++)
                        {
                            newArray[i] = array[i];
                        }
                        for(String locationString: newArray){
                            if(locationString != null && !locationString.isEmpty()){
                                final LocationEntry entry = new LocationEntry(locationString);
                                // insert new entry
                                mDb.locationDao().insertEntry(entry);
                            }
                        }
                    }
                }
            }
        });
    }
    private void saveTypes(final ArrayList<ListingEntry>...typesList) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.listingDao().nukeEntries();
                for (ArrayList<ListingEntry> type : typesList) {
                    if (type.size() > 0) {
                        for (int k = 0; k < type.size(); k++) {
                            ListingEntry tp = type.get(k);
                            int id = tp.getListingId();
                            String typeListing = tp.getListing();
                            // insert type
                            mDb.listingDao().insertEntry(new ListingEntry(id,typeListing));
                        }

                    }
                }
            }
        });
    }
}
