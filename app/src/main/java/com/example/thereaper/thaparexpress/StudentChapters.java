package com.example.thereaper.thaparexpress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.thereaper.thaparexpress.model.Socs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentChapters extends Fragment {

    private int listSize,i;

    public StudentChapters(){}

    private String TAG = Department.class.getSimpleName();
    private static final String url = "http://api.androidhive.info/json/movies.json";
    private List<Socs> socList = new ArrayList<>();
    private ListView listView;
    private CustomListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.schpt,container,false);

        listView = (ListView) rootView.findViewById(R.id.lvSChpt);
        adapter = new CustomListAdapter(this.getActivity(),socList);
        listView.setAdapter(adapter);

        JsonArrayRequest socRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (i=0;i<response.length() ; i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Socs socs = new Socs();
                        socs.setDesc(obj.getString("title"));
                        socs.setName(obj.getString("rating"));

                        socList.add(socs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                listSize = i;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR" + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(socRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String socName = ((TextView) view.findViewById(R.id.socName)).getText().toString();
                String socDesc = ((TextView) view.findViewById(R.id.socDesc)).getText().toString();
                Intent intent = new Intent(StudentChapters.this.getActivity(),SocietiesDetails.class);
                intent.putExtra("socName",socName);
                intent.putExtra("socDesc",socDesc);

                startActivity(intent);
            }
        });

        return rootView;

    }

}
