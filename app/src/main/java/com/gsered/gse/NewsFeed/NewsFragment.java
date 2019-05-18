package com.gsered.gse.NewsFeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gsered.gse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private RecyclerView news_recyclerView;
    private String responseStr;
    private ArrayList newsList;
    private NewsFeedAdapter newsFeedAdapter;
    private Context context;


    public NewsFragment() {
        // Required empty public constructor
    }
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        newsList = new ArrayList<>();
        NewsItemClickListener itemClickListener = new NewsItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        };
        news_recyclerView = (RecyclerView) view.findViewById(R.id.news_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        news_recyclerView.setLayoutManager(mLayoutManager);
        newsFeedAdapter = new NewsFeedAdapter(getActivity(), newsList, itemClickListener);

        news_recyclerView.setAdapter(newsFeedAdapter);

        new NewsServiceHandler().post(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
                Toast.makeText(context, "SSUP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseStr = response.body().string();
                    System.out.println(responseStr);
                    if(getActivity() == null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                getNewsFromJson(responseStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    // Request not successful
                    System.out.println("fail");
                }
            }

        });
        newsFeedAdapter.notifyDataSetChanged();

        return view;
    }


    private void getNewsFromJson(String response) throws JSONException{
        JSONObject jsonObject = new JSONObject(response);
        JSONArray articles = jsonObject.getJSONArray("articles");
        for(int i = 0; i < articles.length(); i++){
            NewsList news = new NewsList();
            JSONObject object = articles.getJSONObject(i);
            news.setCompName(object.getJSONObject("source").getString("name"));;
            news.setHeadline(object.getString("title"));
            news.setRedUrl(object.getString("url"));
            news.setThumb_url(object.getString("urlToImage"));
            newsList.add(news);
        }
        newsFeedAdapter.notifyDataSetChanged();

    }
}
