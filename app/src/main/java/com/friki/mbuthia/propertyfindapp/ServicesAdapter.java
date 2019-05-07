package com.friki.mbuthia.propertyfindapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.friki.mbuthia.propertyfindapp.model.ServiceItem;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {
    private Context mContext;
    private List<ServiceItem> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ServicesAdapter(Context context, List <ServiceItem> servicesList) {
        this.mContext = context;
        this.mList = servicesList;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_item, null);
        ServicesViewHolder svh = new ServicesViewHolder(layoutView);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        holder.serviceIcon.setImageResource(mList.get(position).getPhoto());
        holder.service.setText(mList.get(position).getService());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder{
        ImageView serviceIcon;
        TextView service;

        public ServicesViewHolder(View itemView) {
            super(itemView);

            serviceIcon = (ImageView) itemView.findViewById(R.id.serviceIcon);
            service = (TextView) itemView.findViewById(R.id.tvService);

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
