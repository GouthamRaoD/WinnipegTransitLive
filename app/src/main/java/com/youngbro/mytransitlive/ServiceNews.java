package com.youngbro.mytransitlive;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;


public class ServiceNews extends Fragment {


    private RequestQueue request;
    private ArrayList<News> news;
    ListView list;
    View v;


    public ServiceNews() {
        // Required empty public constructor
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_service_news, container, false);


        news = new ArrayList<>();
        request = Volley.newRequestQueue(getContext());
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,"https://api.winnipegtransit.com/v2/service-advisories.json?api-key=fbHIUejdXU0sRq6w9Nqy",null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray array = response.getJSONArray("service-advisories");
                                for(int i =0;i<array.length();i++)
                                {
                                    news.add(new News(array.getJSONObject(i).getString("title"),array.getJSONObject(i).getString("body")));
                                }
                                do {
                                    if(getActivity() != null) {
                                        Display dis = new Display(getActivity(), new String[news.size()], news);
                                        list = (ListView) v.findViewById(R.id.news_view);
                                        list.setAdapter(dis);
                                    }
                                }while(getActivity() == null);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        } else
                            Log.i("result", " The result is null");

                    }
                },    new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", error.toString());
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        request.add(strReq);
        return v;
    }
    public class Display extends ArrayAdapter<String> {
        Activity context;
        ArrayList<News> news;

        Display(Activity c, String[] s,ArrayList<News> n) {
            super(c, R.layout.news, s);
            this.context = c;
            this.news = n;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View around = layoutInflater.inflate(R.layout.news, null, true);
            TextView item = (TextView) around.findViewById(R.id.title);
            TextView sname = (TextView) around.findViewById(R.id.body);
            item.setText(news.get(position).getTitle());
            sname .setText(news.get(position).getBody());
            return around;
        }
    }

}
