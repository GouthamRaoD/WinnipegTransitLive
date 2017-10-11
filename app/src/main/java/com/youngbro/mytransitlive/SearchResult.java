package com.youngbro.mytransitlive;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
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

public class SearchResult extends AppCompatActivity {

    String name[];
    String stop[];
    RequestQueue request;
    SearchQuery dis;
    ListView list;

    TextView sr;
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
        setContentView(R.layout.activity_search_result);


        //getActionBar().setTitle("Around You");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search Results");
        sr = (TextView) findViewById(R.id.sr);
        Bundle bun = getIntent().getExtras();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dis = null;
        list = (ListView) findViewById(R.id.search_list);
        if (bun != null) {

            if(!bun.getString("key").equals("")) {
                request = Volley.newRequestQueue(SearchResult.this);
                Log.i("stop", bun.getString("key"));
                JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, "https://api.winnipegtransit.com/v2/stops:" + bun.getString("key") + ".json?api-key=fbHIUejdXU0sRq6w9Nqy", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (response != null) {

                                    try {
                                        JSONArray array = (JSONArray) response.get("stops");
                                        if(array.length() == 0)
                                        {
                                            Toast.makeText(SearchResult.this,"Please enter a valid search criteria.",Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                        else {
                                            sr.setText("");
                                            name = new String[array.length()];
                                            stop = new String[array.length()];
                                            for (int i = 0; i < array.length(); i++) {
                                                name[i] = array.getJSONObject(i).getString("name");
                                                stop[i] = array.getJSONObject(i).getString("key");
                                            }
                                            dis = new SearchQuery(SearchResult.this, name, stop);
                                            list.setAdapter(dis);
                                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent intent = new Intent(SearchResult.this, Stops.class);
                                                    intent.putExtra("stop", stop[position]);
                                                    intent.putExtra("name", name[position]);
                                                    startActivity(intent);
                                                }
                                            });
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        sr.setText("Please enter appropriate search criteria.");
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sr.setText("Please enter appropriate search criteria.");
                    }
                });
                request.add(strReq);
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint("StopNo/Street/Area");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(SearchResult.this, SearchResult.class);
                query.replace(" ", "%20");
                intent.putExtra("key",query);
                finish();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public class SearchQuery extends ArrayAdapter<String> {
        Activity context;
        String nameN[];
        String stopN[];

        SearchQuery(Activity c, String[] n, String[] s) {
            super(c, R.layout.around, n);
            this.context = c;
            this.nameN = n;
            this.stopN = s;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View around = layoutInflater.inflate(R.layout.sstops, null, true);
            TextView stop = (TextView) around.findViewById(R.id.stopNum);
            TextView sname = (TextView) around.findViewById(R.id.stopNa);

            stop.setText("#" + stopN[position]);
            sname.setText(nameN[position]);
            return around;
        }
    }
}
