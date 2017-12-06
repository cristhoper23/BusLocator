package com.cristhoper.buslocator.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.activities.RouteActivity;
import com.cristhoper.buslocator.models.Transport;
import com.cristhoper.buslocator.services.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 2/10/2017.
 */

public class TransportAdapter extends BaseAdapter{

    private Context contexto;
    private List<Transport> listaTransportes;

    public TransportAdapter(Context contexto, List<Transport> listaTransportes) {
        this.contexto = contexto;
        this.listaTransportes = listaTransportes;
    }

    @Override
    public int getCount() {
        return listaTransportes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTransportes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaTransportes.get(position).getId_transp();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        view = inflater.inflate(R.layout.item_transport, null);

        final Transport transport = listaTransportes.get(position);

        TextView empresa = view.findViewById(R.id.tvEmpresa);
        TextView id_ruta = view.findViewById(R.id.tvRuta);
        TextView desc_ruta = view.findViewById(R.id.tvDescRuta);
        ImageView icon_bus = view.findViewById(R.id.ivIconBus);

        empresa.setText(transport.getNombre());
        id_ruta.setText(transport.getId_ruta());
        desc_ruta.setText(transport.getDescripcion());

        String url = ApiService.API_BASE_URL + "/images/transportes/" + transport.getImagen();
        Picasso.with(view.getContext()).load(url).into(icon_bus);

        return view;
    }
}
