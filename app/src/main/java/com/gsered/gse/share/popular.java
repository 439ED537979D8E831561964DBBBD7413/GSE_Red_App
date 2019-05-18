package com.gsered.gse.share;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gsered.gse.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class popular extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private List<share_modal> sharelist;
    private FrameLayout progressbar;
    private FrameLayout textView;

    public popular() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.popular, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.socialmediashare);
        progressbar = (FrameLayout) view.findViewById(R.id.progressbar);
        textView = (FrameLayout) view.findViewById(R.id.textView_noresult);

        shareadapter sadapter = new shareadapter(getContext(), sharelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(sadapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharelist = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/fetch_socialpost.php?id=0";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    final String posts = response.body().string();
                    if(getActivity() == null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.GONE);
                            JSONObject socialpost = null;
                            try {
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("post");
                                if(p.getString("error").equalsIgnoreCase("noerror")) {
                                    for(int i=0; i<p.length(); i++){
                                        String id = Integer.toString(i+1);
                                        JSONObject r = p.getJSONObject(id);
                                        sharelist.add(new share_modal(r.getString("image"), r.getString("caption"),"",r.getString("caption")));
                                    }
                                } else {
                                    textView.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
