package com.youngbro.mytransitlive;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NearByMap extends Fragment implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    MapView mMapView;
    View view;
    private String name[];
    private String stop[];
    private String latitude[];
    private String longitude[];
    boolean locationb = false;
    private String lat = "";
    private String lon = "";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private RequestQueue request;
    private ClusterManager<LatiLongi> mClusterManager;

    public NearByMap() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nearby_map, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        if (Build.VERSION.SDK_INT > 28) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    updateLocationInfo(location);
                    locationb = true;
                } else {
                    locationb = false;
                }
            }
        }
        refreshLayout();
        mMapView = (MapView) view.findViewById(R.id.mapView);

        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
            mMapView.invalidate();
        }
        return view;
    }

    public void refreshLayout() {

        name = new String[5188];
        stop = new String[5188];
        latitude = new String[5188];
        longitude = new String[5188];
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.stops);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            int i =0;
            while (line != null) {
                String[] tokens= line.split(",");
                name[i] = tokens[2];
                stop[i] = tokens[3];
                latitude[i] = tokens[0];
                longitude[i] = tokens[1];
                line = reader.readLine();
                i++;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        locationManager.removeUpdates(locationListener);
    }

    public void updateLocationInfo(Location location) {
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mGoogleMap = googleMap;
        mGoogleMap.clear();
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<LatiLongi>(getContext(), mGoogleMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mGoogleMap.setOnCameraIdleListener(mClusterManager);
        mGoogleMap.setOnMarkerClickListener(mClusterManager);
        mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.night_theme));
        CameraPosition cameraPosition1 = new CameraPosition.Builder().target(new LatLng(49.8951, -97.1384)).zoom(10).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
        //Toast.makeText(getContext(),latitude.length+"",Toast.LENGTH_LONG).show();
        final LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if(stop != null) {
            for (int i = 0; i < stop.length; i++) {
                LatiLongi offsetItem = new LatiLongi(new LatLng(Float.parseFloat(latitude[i]), Float.parseFloat(longitude[i])), name[i], stop[i]);
                mClusterManager.addItem(offsetItem);
            }
            mClusterManager.setAnimation(false);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                    {
                        refreshLayout();
                        mMapView.onResume();

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon))).zoom(15).build();
                        //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        mMapView.onResume();
                    }
                    return true;
                }
            });
            mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getContext(), Stops.class);
                    intent.putExtra("stop", marker.getSnippet());
                    intent.putExtra("name", marker.getTitle());
                    startActivity(intent);
                }
            });
        }
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            refreshLayout();
            mMapView.onResume();

            // For zooming automatically to the location of the marker
//            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon))).zoom(15).build();
            //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMapView.onResume();
        }
    }
}
