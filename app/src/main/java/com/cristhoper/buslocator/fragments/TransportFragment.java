package com.cristhoper.buslocator.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.adapters.TransportAdapter;
import com.cristhoper.buslocator.models.Transport;
import com.cristhoper.buslocator.repositories.TransportRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransportFragment extends Fragment {

    private RecyclerView recyclerTransp;

    public TransportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_transport, container, false);

        getActivity().setTitle("Transportes");
        List<Transport> transports = TransportRepository.getTransports();

        recyclerTransp = vista.findViewById(R.id.recycler_transp);
        recyclerTransp.setLayoutManager(new LinearLayoutManager(getContext()));

        TransportAdapter adapter = new TransportAdapter();
        adapter.setTransports(transports);

        recyclerTransp.setAdapter(adapter);
        return vista;
    }

}
