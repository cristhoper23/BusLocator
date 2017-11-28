package com.cristhoper.buslocator.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.adapters.TransportAdapter;
import com.cristhoper.buslocator.models.Transport;
import com.cristhoper.buslocator.repositories.TransportRepository;
import com.cristhoper.buslocator.services.ApiService;
import com.cristhoper.buslocator.services.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransportFragment extends Fragment {

    private static final String TAG = TransportFragment.class.getSimpleName();

    RecyclerView transportesList;

    public TransportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_transport, container, false);

        getActivity().setTitle("Transportes");

        //List<Transport> transports = TransportRepository.getTransports();

        transportesList = vista.findViewById(R.id.recycler_transp);
        transportesList.setLayoutManager(new LinearLayoutManager(getContext()));

        /*TransportAdapter adapter = new TransportAdapter();
        adapter.setTransports(transports);*/

        transportesList.setAdapter(new TransportAdapter(getActivity()));

        initialize();

        return vista;
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Transport>> call = service.getAllTransportes();
        //Call<List<Denuncia>> call = service.getDenuncias(usuario_id);

        call.enqueue(new Callback<List<Transport>>() {
            @Override
            public void onResponse(Call<List<Transport>> call, Response<List<Transport>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Transport> transportes = response.body();
                        Log.d(TAG, "transportes: " + transportes);

                        TransportAdapter adapter = (TransportAdapter) transportesList.getAdapter();
                        adapter.setTransports(transportes);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Transport>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
