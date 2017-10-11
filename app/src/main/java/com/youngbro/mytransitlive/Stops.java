package com.youngbro.mytransitlive;

import android.app.Activity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Stops extends AppCompatActivity {
    static ArrayList<Bus> bus = new ArrayList<>();
    RequestQueue request;
    String route;
    String stop;
    TextView text;
    Toolbar toolbar;
    String res;
    String stopNum;
    DataBase db;
    String strl;
    SwipeRefreshLayout mySwipeRefreshLayout;
    Menu m;


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);

        toolbar = (Toolbar)  findViewById(R.id.toolbar);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        db = new DataBase(this, null);
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        strl = simple.format(dt);
        strl = strl.replace(" ","T");
        Bundle bun = getIntent().getExtras();
        if(bun !=null)
        {
            text = (TextView) findViewById(R.id.stopN);
            stop = bun.getString("name");
            text.setText(bun.getString("name"));
            res = bun.getString("stop");
            Log.i(res,stop);
            toolbar.setTitle("#"+res);

            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            assert res != null;
            stopNum = res;
            refreshLayout();
            mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
            mySwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            refreshLayout();
                            mySwipeRefreshLayout.setRefreshing(false);
                        }
                    }
            );
        }
    }
    public void refreshLayout(){
        request = Volley.newRequestQueue(this);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,"https://api.winnipegtransit.com/v2/stops/"+ stopNum +"/schedule.json?end="+strl+"&api-key=fbHIUejdXU0sRq6w9Nqy",null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try
                            {
                                String qtime = response.getString("query-time");
                                JSONArray arra = (JSONArray) response.getJSONObject("stop-schedule").get("route-schedules");
                                for(int i =0;i<arra.length();i++)
                                {

                                    route = arra.getJSONObject(i).getJSONObject("route").getString("key");
                                    JSONArray ss = (JSONArray) arra.getJSONObject(i).get("scheduled-stops");
                                    for(int k =0; k< ss.length();k++)
                                    {
                                        final JSONObject jb1 = (JSONObject) ss.get(k);
                                        bus.add(new Bus(jb1.getJSONObject("times").getJSONObject("departure").getString("scheduled").substring(11,19),
                                                jb1.getJSONObject("times").getJSONObject("departure").getString("estimated"),
                                                jb1.getJSONObject("variant").getString("key"), route,qtime));
                                    }
                                }
                                Collections.sort(bus);
                                Display dis = new Display(Stops.this, new String[bus.size()],bus);
                                ListView list = (ListView) findViewById(R.id.bus);
                                list.setAdapter(dis);
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    }
                                });
                                bus = new ArrayList<>();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else
                            Log.i("result", " The result is null");
                    }
                },    new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Stops.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        request.add(strReq);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        m = menu;
        getMenuInflater().inflate(R.menu.main1, menu);
        updateIcon(menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint("StopNo/Street/Area");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(Stops.this, SearchResult.class);
                query.replace(" ", "%20");
                intent.putExtra("key",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    public void updateIcon(Menu menu)
    {
        if(db.isPresent(stopNum)){menu.findItem(R.id.action_favorite).setIcon(R.drawable.favorite1);}
        else{menu.findItem(R.id.action_favorite).setIcon(R.drawable.favorite);}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_refresh)
        {
            mySwipeRefreshLayout.setRefreshing(true);
            refreshLayout();
            mySwipeRefreshLayout.setRefreshing(false);
            return true;
        }
        else if (id == R.id.action_favorite)
        {
            if(!db.isPresent(stopNum)) {
                db.addStop(stop, stopNum);
                updateIcon(m);
            }
            else {
                AlertDialog.Builder act = new AlertDialog.Builder(Stops.this);
                act.setMessage("Delete the stop from Favorite? ").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteStop(stopNum);
                        updateIcon(m);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog a = act.create();
                a.show();
            }
            db.close();
        }
        else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private class Display extends ArrayAdapter<String> {
        Activity context;
        ArrayList<Bus> busses;
        Display(Activity c, String[] n,ArrayList<Bus> b) {
            super(c, R.layout.around, n);
            this.context = c;
            busses = b;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View around = layoutInflater.inflate(R.layout.bus, null, true);
            TextView stop = (TextView) around.findViewById(R.id.number);
            final TextView snam = (TextView) around.findViewById(R.id.name);
            TextView sdistance = (TextView) around.findViewById(R.id.time);
            TextView status = (TextView) around.findViewById(R.id.status);
            stop.setText(busses.get(position).getRoute());
            sdistance.setText(busses.get(position).getEarrive());
            snam.setText(Name.getSName(busses.get(position).getName()));
            try {
                status.setText(busses.get(position).getStatus());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return around;
        }
    }
}
