package com.cristhoper.buslocator.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.models.Transport;
import com.cristhoper.buslocator.services.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 2/10/2017.
 */

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.ViewHolder>{

    private List<Transport> transports;

    private Activity activity;

    public TransportAdapter(Activity activity){
        this.transports = new ArrayList<>();
        this.activity = activity;
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView empresa;
        public TextView id_ruta;
        public TextView desc_ruta;
        public ImageView icon_bus;

        public ViewHolder(View itemView) {
            super(itemView);

            empresa = itemView.findViewById(R.id.tvEmpresa);
            id_ruta = itemView.findViewById(R.id.tvRuta);
            desc_ruta = itemView.findViewById(R.id.tvDescRuta);
            icon_bus = itemView.findViewById(R.id.ivIconBus);
        }
    }

    @Override
    public void onBindViewHolder(TransportAdapter.ViewHolder holder, int position) {
        Transport transp = this.transports.get(position);

        holder.empresa.setText(transp.getNombre());
        holder.id_ruta.setText(transp.getRuta());
        holder.desc_ruta.setText(transp.getDescripcion());

        String url = ApiService.API_BASE_URL + "/images/transportes/" + transp.getImagen();
        Picasso.with(holder.itemView.getContext()).load(url).into(holder.icon_bus);
    }

    @Override
    public int getItemCount() {
        return this.transports.size();
    }
}
