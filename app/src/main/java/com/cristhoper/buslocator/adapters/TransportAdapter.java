package com.cristhoper.buslocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.models.Transport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 2/10/2017.
 */

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.ViewHolder>{

    private List<Transport> transports;

    public TransportAdapter(){
        this.transports = new ArrayList<>();
    }

    public void setTransports(List<Transport> transports) {
        this.transports = transports;
    }

    @Override
    public TransportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transport, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransportAdapter.ViewHolder holder, int position) {
        Transport transp = this.transports.get(position);

        holder.name_transp.setText(transp.getName());
        holder.img_transp.setImageResource(transp.getPicture());
    }

    @Override
    public int getItemCount() {
        return this.transports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_transp;
        public TextView name_transp;

        public ViewHolder(View itemView) {
            super(itemView);

            img_transp = (ImageView) itemView.findViewById(R.id.ivTransp);
            name_transp = (TextView) itemView.findViewById(R.id.tvEtuchisa);
        }
    }
}
