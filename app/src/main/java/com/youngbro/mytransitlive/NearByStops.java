package com.youngbro.mytransitlive;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NearByStops extends Fragment {
    private String name[];
    private String stop[];
    private String distance[];
    private String lat;
    private String lon;
    private ListView list;
    private Display dis;
    //private TextView txt;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private RequestQueue request;
    private com.facebook.ads.AdView adView;
    SwipeRefreshLayout mySwipeRefreshLayout;
    TextView behind;

    public NearByStops() {
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
        View v = inflater.inflate(R.layout.fragment_near_by_stops, container, false);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mySwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        behind = (TextView) v.findViewById(R.id.behind);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.i("lat" , String.valueOf(location.getLatitude()));
                updateLocationInfo(location);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };
        if (Build.VERSION.SDK_INT > 25) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        else
        {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    updateLocationInfo(location);
                }
                else{
                    behind.setText("To view the stops Nearby please turn on your location and refresh the page again!");
                }
            }
        }
        RelativeLayout adViewContainer = (RelativeLayout) v.findViewById(R.id.adViewContainer);
        adView = new com.facebook.ads.AdView(getContext(), "", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();
        list = (ListView) v.findViewById(R.id.aroundYou);
        refreshLayout();
        mySwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshLayout();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return v;
    }
    public void refreshLayout(){

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                updateLocationInfo(location);
            }
            else{
                behind.setText("To view the stops Nearby please turn on your location and refresh the page again!");
            }
        }
        //txt = (TextView) findViewById(R.id.noStops);
        if(lat != null && lon != null) {
            behind.setText("");
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
                                        distance = new String[array.length()];
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject jb1 = (JSONObject) array.get(i);
                                            //JSONArray array1 = (JSONArray) jb1.get("distance");
                                            name[i] = array.getJSONObject(i).getString("name");
                                            stop[i] = array.getJSONObject(i).getString("key");
                                            distance[i] = jb1.getJSONObject("distances").getString("direct");
                                        }
                                        dis = new Display(getActivity(), name, stop, distance);
                                        list.setAdapter(dis);
                                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                // Getting the Container Layout of the ListView
                                                Intent intent = new Intent(getContext(), Stops.class);
                                                intent.putExtra("stop", stop[position]);
                                                intent.putExtra("name", name[position]);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {
                                        behind.setText("No Stops Nearby");
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
        else
        {

            //txt.setText("To view the stops Nearby please turn on your location and refresh the page again!");
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
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    public void updateLocationInfo(Location location) {
        //Log.i("LocationInfo", location.toString());
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
    }
    private class Display extends ArrayAdapter<String> {
        Activity context;
        String nameN[];
        String stopN[];
        String distanceN[];

        Display(Activity c, String[] n, String[] s, String[] d) {
            super(c, R.layout.around, n);
            this.context = c;
            this.nameN = n;
            this.stopN = s;
            this.distanceN = d;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View around = layoutInflater.inflate(R.layout.around, null, true);
            TextView stop = (TextView) around.findViewById(R.id.stopNo);
            TextView sname = (TextView) around.findViewById(R.id.stopName);
            TextView sdistance = (TextView) around.findViewById(R.id.distance);

            stop.setText("#" + stopN[position]);
            sname.setText(nameN[position]);
            sdistance.setText(String.format("%.0fm from you", Double.parseDouble(distanceN[position])));
            return around;
        }
    }

}
