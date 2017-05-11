package com.youngbro.mytransitlive;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

public class Search extends AppCompatActivity {
    Toolbar toolbar;
    EditText searcher;
    RequestQueue request;
    private com.facebook.ads.AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
        adView = new com.facebook.ads.AdView(this, "", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searcher = (EditText) findViewById(R.id.searchEdit);
    }
    public void goSearch(View view)
    {
        String str = searcher.getText().toString();
        str = str.replace(" ","%20");
        if(str.equals("")){
            Toast.makeText(Search.this,"Please Fill the search box",Toast.LENGTH_LONG).show();}
        else{tester(str);}
    }
    private void tester2(final String text)
    {
        request = Volley.newRequestQueue(this);
        JsonObjectRequest strReq1 = new JsonObjectRequest(Request.Method.GET,"https://api.winnipegtransit.com/v2/locations:"+text+".json?api-key=<YOUR Api KEY>",null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response !=null)
                        {
                            try {
                                JSONArray arr = (JSONArray) response.get("locations");
                                if(arr.length() == 0)
                                {
                                    Toast.makeText(Search.this,"Please enter a valid address or location or intersection",Toast.LENGTH_LONG).show();
                                }
                                else if(arr.length() == 1){
                                    JSONObject jb = arr.getJSONObject(0).getJSONObject("centre");
                                    if(jb.getJSONObject("geographic").getString("latitude")!=null && jb.getJSONObject("geographic").getString("longitude")!=null)
                                    {
                                        if(jb!=null) {
                                            String lat = jb.getJSONObject("geographic").getString("latitude");
                                            String lon = jb.getJSONObject("geographic").getString("longitude");
                                            Intent intent = new Intent(Search.this, SearchResult.class);
                                            intent.putExtra("key", "");
                                            intent.putExtra("lat", lat);
                                            intent.putExtra("lon", lon);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                },    new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        request.add(strReq1);

    }
    private void tester(final String text){
        request = Volley.newRequestQueue(this);

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,"https://api.winnipegtransit.com/v2/stops:"+text+".json?api-key=<YOUR Api KEY>",null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response !=null)
                        {

                            try {
                                JSONArray arr = (JSONArray) response.get("stops");
                                if(arr.length() == 0)
                                {
                                    tester2(text);
                                }
                                else{
                                    Intent intent = new Intent(Search.this, SearchResult.class);
                                    intent.putExtra("key",text);
                                    intent.putExtra("lat","");
                                    intent.putExtra("lon","");
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },    new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        request.add(strReq);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}

