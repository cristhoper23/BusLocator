package com.cristhoper.buslocator.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.adapters.RouteAdapter;
import com.cristhoper.buslocator.models.Avenida;
import com.cristhoper.buslocator.models.BusStop;
import com.cristhoper.buslocator.services.ApiService;
import com.cristhoper.buslocator.services.ApiServiceGenerator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = RouteActivity.class.getSimpleName();

    GoogleMap gMap;
    MapView mapView;

    String id_ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        id_ruta = getIntent().getExtras().getString("id_ruta");

        //Log.d("ID_RUTAAAAA", "el id_ruta de un transporte   " + id_ruta);
        mapView = findViewById(R.id.route_map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        initialize();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);
    }

    public void initialize(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<BusStop>> call = service.showRouteAndBustop(id_ruta);

        call.enqueue(new Callback<List<BusStop>>() {
            @Override
            public void onResponse(Call<List<BusStop>> call, Response<List<BusStop>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<BusStop> paraderos = response.body();
                        Log.d(TAG, "productos: " + paraderos);

                        RouteAdapter routeAdapter = new RouteAdapter(RouteActivity.this, gMap);
                        routeAdapter.setParaderos(paraderos);
                        routeAdapter.getMarker();
                        //adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(RouteActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<BusStop>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RouteActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<List<Avenida>> call_2 = service.showAvenueforRoute(id_ruta);

        call_2.enqueue(new Callback<List<Avenida>>() {
            @Override
            public void onResponse(Call<List<Avenida>> call, Response<List<Avenida>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Avenida> avenidas = response.body();
                        Log.d(TAG, "avenidas: " + avenidas);

                        RouteAdapter routeAdapter = new RouteAdapter(RouteActivity.this, gMap);
                        routeAdapter.setAvenidas(avenidas);
                        routeAdapter.getAvenida();
                        //adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(RouteActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Avenida>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RouteActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
