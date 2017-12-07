package com.cristhoper.buslocator.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.adapters.TransportAdapter;
import com.cristhoper.buslocator.models.BusStop;
import com.cristhoper.buslocator.models.Transport;
import com.cristhoper.buslocator.repositories.BusStopRepository;
import com.cristhoper.buslocator.services.ApiService;
import com.cristhoper.buslocator.services.ApiServiceGenerator;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BustopFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = BustopFragment.class.getSimpleName();

    GoogleMap gMap;
    MapView mapView;
    View mView;
    FloatingActionButton myLocationButton; //setBackgroundTi...

    private Marker markerMyPosition;
    double lat = 0.0, lng = 0.0;

    public BustopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bustop, container, false);
        getActivity().setTitle("Paraderos");


        myLocationButton = mView.findViewById(R.id.btnFAB);
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng coordinates = new LatLng(lat,lng);
                CameraUpdate myPosition = CameraUpdateFactory.newLatLngZoom(coordinates, 20);
                gMap.animateCamera(myPosition);
            }
        });

        initialize();

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //---- Instancia un objeto GoogleMap e inicializa el mapa para ser usado
        mapView = mView.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        //----

    }

    @Override
    //Called when the map is ready to be used.
    public void onMapReady(GoogleMap googleMap) {

        //MapsInitializer.initialize(getContext());

        gMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //googleMap.addMarker(new MarkerOptions().position(new LatLng(-12.2407346, -76.9229523)).title("no vengan!!").snippet("Hola mundo"));
        myLocation();
        cameraPosition(lat,lng);
    }

    public void initialize(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<BusStop>> call = service.getAllBusStops();

        call.enqueue(new Callback<List<BusStop>>() {
            @Override
            public void onResponse(Call<List<BusStop>> call, Response<List<BusStop>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<BusStop> paraderos = response.body();
                        Log.d(TAG, "paraderos: " + paraderos);

                        //Método para obtener todos los paraderos
                        getMarkers(paraderos);

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
            public void onFailure(Call<List<BusStop>> call, Throwable t) {

            }
        });
    }

    public void getMarkers(List<BusStop> paraderos) {

        for (BusStop paradero : paraderos) {
            double lat = paradero.getLatitud();
            double lon = paradero.getLongitud();
            String name = paradero.getNombre();

            BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_bustop);
            MarkerOptions customMarker = new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(name)
                    .icon(markerIcon);

            gMap.addMarker(customMarker);
            //googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(name));
        }
    }

    private void myLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateLocation(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,0,locListener);
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void updateLocation(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            addMarker(lat, lng);
        }
    }

    private void addMarker(double latitude, double longitude) {
        LatLng coordinates = new LatLng(latitude, longitude);

        if (markerMyPosition != null)
            markerMyPosition.remove();

        BitmapDescriptor iconMyPosition = BitmapDescriptorFactory.fromResource(R.mipmap.ic_my_position);

        markerMyPosition = gMap.addMarker(new MarkerOptions()
                .position(coordinates)
                .title("Mi posición actual")
                .icon(iconMyPosition));
    }

    public void cameraPosition(double lat, double lon){
        LatLng coordinates = new LatLng(lat, lon);
        CameraUpdate myPosition = CameraUpdateFactory.newLatLngZoom(coordinates, 17);
        gMap.animateCamera(myPosition);
    }
}
