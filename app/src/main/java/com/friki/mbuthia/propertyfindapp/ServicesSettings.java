package com.friki.mbuthia.propertyfindapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.friki.mbuthia.propertyfindapp.model.ServiceItem;
import com.friki.mbuthia.propertyfindapp.utils.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ServicesSettings extends AppCompatActivity implements ServicesAdapter.OnItemClickListener {

    public static final String EXTRA_SERVICE = "service";

    private RecyclerView mRecyclerView;
    private ServicesAdapter mAdapter;
    private LinearLayoutManager mLayout;
    private List<ServiceItem> mListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_settings);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListItem = getAllItemList();
        mLayout = new LinearLayoutManager(ServicesSettings.this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayout);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 78));

        mAdapter = new ServicesAdapter(ServicesSettings.this, mListItem);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        ServiceItem clickedItem = mListItem.get(position);
        Intent intent = new Intent(this, Services.class);
        intent.putExtra(EXTRA_SERVICE,clickedItem.getServiceId());
        startActivity(intent);
    }

    private List<ServiceItem> getAllItemList(){
        List<ServiceItem> services = new ArrayList<ServiceItem>();
        services.add(new ServiceItem(R.mipmap.ic_plumbing_round,1,getString(R.string.electricians)));
        services.add(new ServiceItem(R.mipmap.ic_electricians_round,2,getString(R.string.plumbers)));
        services.add(new ServiceItem(R.mipmap.ic_painters_round,3,getString(R.string.painters)));
        services.add(new ServiceItem(R.mipmap.ic_carpenter,4,getString(R.string.carpenters)));
        services.add(new ServiceItem(R.mipmap.ic_security_round,5,getString(R.string.security_firms)));
        services.add(new ServiceItem(R.mipmap.ic_waste_mgt_round,6,getString(R.string.waste_mgt_companies)));
        services.add(new ServiceItem(R.mipmap.ic_cleaning_round,7,getString(R.string.cleaning_companies)));
        services.add(new ServiceItem(R.mipmap.ic_movers_round,8,getString(R.string.movers)));
        return services;
    }
}
