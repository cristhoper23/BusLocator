package com.cristhoper.buslocator.adapters;

import android.app.Activity;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.models.Avenida;
import com.cristhoper.buslocator.models.BusStop;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 26/11/2017.
 */

public class RouteAdapter implements RoutingListener{

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimary};

    private Activity activity;
    private GoogleMap googleMap;

    public RouteAdapter (Activity activity, GoogleMap googleMap){
        this.activity = activity;
        this.googleMap = googleMap;
        polylines = new ArrayList<>();
    }

    private List<BusStop> paraderos;

    public void setParaderos(List<BusStop> paraderos) {
        this.paraderos = paraderos;
    }

    private List<Avenida> avenidas;

    public void setAvenidas(List<Avenida> avenidas) {
        this.avenidas = avenidas;
    }

    /*private int getItemCount(){
        return paraderos.size();
    }*/

    public void getMarker(){

        for (BusStop paradero : paraderos) {
            double lat = paradero.getLatitud();
            double lon = paradero.getLongitud();
            String name = paradero.getNombre();

            BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_bustop);
            MarkerOptions customMarker = new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(name)
                    .icon(markerIcon);

            googleMap.addMarker(customMarker);
            //googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(name));
        }

        BusStop paradero_inicial = paraderos.get(0);
        double lat = paradero_inicial.getLatitud();
        double lon = paradero_inicial.getLongitud();

        LatLng coordinates = new LatLng(lat, lon);
        CameraUpdate myPosition = CameraUpdateFactory.newLatLngZoom(coordinates, 17);
        googleMap.animateCamera(myPosition);

        /*LatLng start = new LatLng(-12.235392, -76.911816);

        LatLng avMariaReiche= new LatLng(-12.243387, -76.918747);
        LatLng avForestal= new LatLng(-12.242024, -76.926204);
        LatLng avOasis= new LatLng(-12.237403, -76.932159);
        LatLng av200M= new LatLng(-12.234746, -76.934746);
        LatLng avPastorSevilla= new LatLng(-12.230434, -76.938431);
        LatLng avMateoPumacahua= new LatLng(-12.193120, -76.965775);
        LatLng CarrPanamericana= new LatLng(-12.104555, -76.978927);

        LatLng end = new LatLng(-11.923701, -77.073017);

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(start, avMariaReiche, avForestal, avOasis, av200M, avPastorSevilla, avMateoPumacahua, CarrPanamericana, end)
                .build();
        routing.execute();*/
    }

    public void getAvenida(){
        List<LatLng> waypoints = new ArrayList<>();

        for (Avenida avenida: avenidas){
            float lat = avenida.getLatitud();
            float lon = avenida.getLongitud();
            LatLng point = new LatLng(lat, lon);

            waypoints.add(point);
        }

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(waypoints)
                .build();
        routing.execute();
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(activity.getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = googleMap.addPolyline(polyOptions);
            polylines.add(polyline);

            //Toast.makeText(activity,"Avenida "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingCancelled() {

    }
}
