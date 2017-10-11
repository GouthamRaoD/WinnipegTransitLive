package com.youngbro.mytransitlive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;


public class Favourite extends Fragment {
    DataBase db;
    ArrayList<DbData> data;


    public Favourite() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        refreshFavourite(getView());
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View refreshFavourite(View v){
        View r =v;
        db = new DataBase(getContext(), null);
        data = db.getData();
        db.close();
        Collections.sort(data);
        TextView text = (TextView) r.findViewById(R.id.noData);
        ListView list = (ListView) r.findViewById(R.id.favour);
        if(data.size() == 0)
        {
            list.setAdapter(null);
            text.setText("No Items in Favorites");
        }
        else {
            text.setText(null);
            Query query = new Query(getActivity(), new String[data.size()], data);
            list.setAdapter(query);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), Stops.class);
                    intent.putExtra("stop", data.get(position).get_stopNo());
                    intent.putExtra("name", data.get(position).get_stopName());
                    startActivity(intent);
                }
            });
        }
        return r;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_favourite, container, false);


        return refreshFavourite(v);
    }

    public class Query extends ArrayAdapter<String> {
        Activity context;
        String nameN[];
        ArrayList<DbData> data;

        Query(Activity c, String[] n, ArrayList<DbData> d) {
            super(c, R.layout.sstops, n);
            this.context = c;
            this.nameN = n;
            data = d;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View around = layoutInflater.inflate(R.layout.sstops, null, true);
            TextView stop = (TextView) around.findViewById(R.id.stopNum);
            TextView sname = (TextView) around.findViewById(R.id.stopNa);
            stop.setText("#" + data.get(position).get_stopNo());
            sname.setText(data.get(position).get_stopName());
            return around;
        }
    }
}
