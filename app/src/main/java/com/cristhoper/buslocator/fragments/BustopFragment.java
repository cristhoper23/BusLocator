package com.cristhoper.buslocator.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.models.BusStop;
import com.cristhoper.buslocator.repositories.BusStopRepository;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BustopFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mapView;
    View mView;

    private Marker markerMyPosition;
    private double lat = 0.0, lng = 0.0;

    public BustopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bustop, container, false);
        getActivity().setTitle("Paraderos");

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

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //googleMap.addMarker(new MarkerOptions().position(new LatLng(-12.2407346, -76.9229523)).title("no vengan!!").snippet("Hola mundo"));

        myLocation();
        getMarkers(googleMap);
    }

    public void getMarkers(GoogleMap googleMap) {

        List<BusStop> busStopList = BusStopRepository.getBusStopList();

        for (BusStop busStop : busStopList) {
            double lat = busStop.getLatitude();
            double lon = busStop.getLongitude();
            String name = busStop.getName();

            BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.bg_etuchisa);
            MarkerOptions customMarker = new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(name);
            //.icon(markerIcon);

            googleMap.addMarker(customMarker);
            //googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(name));
        }
    }

    private void myLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

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
        CameraUpdate myPosition = CameraUpdateFactory.newLatLngZoom(coordinates, 16);

        if (markerMyPosition != null)
            markerMyPosition.remove();

        markerMyPosition = mGoogleMap.addMarker(new MarkerOptions()
                .position(coordinates)
                .title("Mi posición actual"));
        mGoogleMap.animateCamera(myPosition);
    }

}
