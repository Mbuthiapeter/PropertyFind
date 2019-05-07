package com.friki.mbuthia.propertyfindapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.friki.mbuthia.propertyfindapp.model.Property;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Property> mSortedList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecyclerViewAdapter(Context context, ArrayList <Property> propertyList) {
        this.mContext = context;
        this.mSortedList = propertyList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item, null);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Property currentItem = mSortedList.get(position);
        NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);

        String propertyType = currentItem.getPropertyType() +" in " +currentItem.getLocation();
        String price ="Ksh " +nf.format(currentItem.getAmount());
        String imageUrl = currentItem.getmImageUrl();

        Picasso.get().load(imageUrl).fit().centerInside().into(holder.propertyImage);
        holder.locality.setText(propertyType);
        holder.price.setText(price);
        holder.bedroom.setText(String.valueOf(currentItem.getBedrooms()));
        holder.bathroom.setText(String.valueOf(currentItem.getBedrooms()));
        holder.parking.setText(String.valueOf(currentItem.getCar_parks()));

    }

    @Override
    public int getItemCount() {
        return mSortedList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView propertyImage;
        TextView locality;
        TextView price;
        TextView bedroom;
        TextView bathroom;
        TextView parking;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            propertyImage = (ImageView) itemView.findViewById(R.id.property_photo);
            locality = (TextView) itemView.findViewById(R.id.property_type);
            price = (TextView)itemView.findViewById(R.id.property_price);
            bedroom = (TextView)itemView.findViewById(R.id.bedrooms);
            bathroom = (TextView)itemView.findViewById(R.id.bathrooms);
            parking = (TextView)itemView.findViewById(R.id.car_parks);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
