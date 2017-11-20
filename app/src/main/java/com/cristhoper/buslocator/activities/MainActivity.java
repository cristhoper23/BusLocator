package com.cristhoper.buslocator.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.fragments.BustopFragment;
import com.cristhoper.buslocator.fragments.RouteFragment;
import com.cristhoper.buslocator.fragments.TransportFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    DrawerLayout drawerLayout;
    FragmentManager fragmentManager = getSupportFragmentManager();
    TextView menu_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu_email = (TextView) findViewById(R.id.menu_email);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*This class provides a handy way to tie together the functionality of DrawerLayout and the framework ActionBar
        to implement the recommended design for navigation drawers.*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.cancel);
        drawerLayout.addDrawerListener(toggle); //Adds the specified listener to the list of listeners that will be notified of drawer events.
        toggle.syncState(); //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout.

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        getTransportFragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_transp:
                        getTransportFragment();
                        break;
                    case R.id.nav_routes:
                        getRouteFragment();
                        break;
                    case R.id.nav_bustop:
                        getBustopFragment();
                        break;
                    case R.id.nav_logout:
                        finish();
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getTransportFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Option open drawer
                if(!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);   // Open drawer
                else
                    drawerLayout.closeDrawer(GravityCompat.START);    // Close drawer
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //--- Métodos para el buscador
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_buscador,menu);
        MenuItem item = menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        //searchView.setOnQueryTextListener(this);
        return true;
    }
    //---

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    //--- Funciones para obtener los fragments
    public void getTransportFragment(){
        Fragment fragTransp = new TransportFragment();
        // Replace content
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.main_content, fragTransp).addToBackStack("tagTransport").commit();
    }

    public void getRouteFragment(){
        Fragment fragRoutes = new RouteFragment();
        // Replace content
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.main_content, fragRoutes).commit();
    }

    public void getBustopFragment(){
        Fragment fragBustop = new BustopFragment();
        // Replace content
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.main_content, fragBustop).commit();
    }
    //---

    public void registrarCuenta(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }
}
