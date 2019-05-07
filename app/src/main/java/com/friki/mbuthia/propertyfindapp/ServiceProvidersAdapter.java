package com.friki.mbuthia.propertyfindapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.friki.mbuthia.propertyfindapp.model.ServiceItem;
import com.friki.mbuthia.propertyfindapp.model.Technician;
import com.friki.mbuthia.propertyfindapp.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServiceProvidersAdapter extends RecyclerView.Adapter<ServiceProvidersAdapter.ServiceProviderViewHolder> {
    private Context mContext;
    private ArrayList<Technician> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ServiceProvidersAdapter(Context context, ArrayList <Technician> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ServiceProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_providers_item, null);
        ServiceProviderViewHolder rcv = new ServiceProviderViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderViewHolder holder, int position) {
        Technician currentItem = mList.get(position);
        Picasso.get().load(currentItem.getPhotoUrl()).fit().centerInside()
                .transform(new RoundedTransformation())
                .into(holder.mImageView);
        holder.name.setText(currentItem.getName());
        holder.locality.setText(currentItem.getLocality());
        holder.rating.setText("Client Rating: "+currentItem.getRating());
        //holder.rating.setText(String.valueOf(currentItem.getRating()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ServiceProviderViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView name;
        TextView locality;
        TextView rating;

        public ServiceProviderViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
            name = (TextView) itemView.findViewById(R.id.tvName);
            locality = (TextView) itemView.findViewById(R.id.tvLocality);
            rating = (TextView) itemView.findViewById(R.id.tvRating);

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
