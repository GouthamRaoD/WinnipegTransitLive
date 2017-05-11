package com.youngbro.mytransitlive;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
        view = inflater.inflate(R.layout.fragment_near_by_map, container, false);

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
        if (Build.VERSION.SDK_INT > 25) {
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
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location !=null) {
                lat = String.valueOf(location.getLatitude());
                lon = String.valueOf(location.getLongitude());

            }//txt = (TextView) findViewById(R.id.noStops);
            request = Volley.newRequestQueue(getContext());
            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, "https://api.winnipegtransit.com/v2/stops.json?distance=1000&lat=" + lat + "&lon=" + lon + "&api-key=<YOUR Api KEY>", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response != null) {

                                try {
                                    JSONArray array = (JSONArray) response.get("stops");
                                    if (array.length() != 0) {

                                        name = new String[array.length()];
                                        stop = new String[array.length()];
                                        latitude = new String[array.length()];
                                        longitude = new String[array.length()];
                                        //Toast.makeText(getContext(), array.length() + " Arrau " + name.length + " " + stop.length + " " + latitude.length + " " + longitude.length, Toast.LENGTH_LONG).show();
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject jb1 = (JSONObject) array.get(i);
                                            //JSONArray array1 = (JSONArray) jb1.get("distance");
                                            name[i] = array.getJSONObject(i).getString("name");
                                            stop[i] = array.getJSONObject(i).getString("key");
                                            latitude[i] = jb1.getJSONObject("centre").getJSONObject("geographic").getString("latitude");
                                            longitude[i] = jb1.getJSONObject("centre").getJSONObject("geographic").getString("longitude");
                                        }

                                    } else {
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            request.add(strReq);
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
        //mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.night_theme));
        CameraPosition cameraPosition1 = new CameraPosition.Builder().target(new LatLng(49.8951, -97.1384)).zoom(12).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
        //Toast.makeText(getContext(),latitude.length+"",Toast.LENGTH_LONG).show();
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            refreshLayout();
            mMapView.onResume();
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
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon))).zoom(15).build();
                //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
            mMapView.onResume();
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("To use this Feature you need to enable your location service.\nWould you like to enable the location service?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
        }
    }

