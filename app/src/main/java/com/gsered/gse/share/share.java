package com.gsered.gse.share;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gsered.gse.R;

import org.json.JSONArray;
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

public class share extends android.support.v4.app.Fragment  {
    View view;
    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private RecyclerView recyclerView;
    private List<share_modal> sharelist;
    private FrameLayout progressbar;
    private FrameLayout textView;
    private shareadapter sadapter;

    public share() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.share, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.socialmediashare);
        progressbar = (FrameLayout) view.findViewById(R.id.progressbar);
        textView = (FrameLayout) view.findViewById(R.id.textView_noresult);

        sadapter = new shareadapter(getContext(), sharelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(sadapter);

        sadapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharelist = new ArrayList<>();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(APPCRED, MODE_PRIVATE);
        String ngoid = sharedPreferences.getString(NGO_ID, "");

        sharelist.add(new share_modal("https://www.gsered.com/seasonal_posts/POST%201%20-%20ARMY%20DAY.jpg", "Our armies stand as a wall of an iron defence on the border's of our nation. Alas their families suffer when they sacrifice their lives for their nation . We should do more for them . Let's come together to support this noble cause by just shopping gift's for your loved one's and we shall donate up-to 6% of the total expenditure.","","Our armies stand as a wall of an iron defence on the border's of our nation. Alas their families suffer when they sacrifice their lives for their nation . We should do more for them . Let's come together to support this noble cause by just shopping gift's for your loved one's and we shall donate up-to 6% of the total expenditure."));
        sharelist.add(new share_modal("https://www.gsered.com/seasonal_posts/POST%203%20-%20SPORTS%20DAY.jpg", "Just play. Have fun. Enjoy the game.","","Just play. Have fun. Enjoy the game."));
        sharelist.add(new share_modal("https://www.gsered.com/seasonal_posts/POST%203%20-%20LITERACY%20DAY.jpg", "Mission 100% Literacy rate. Join this revolution with me to change the future. #ngo #socialworker #socialmedia #social #fundraising #gse #gsered #fundraiser #charity #charities #shopping #ecommerce #groceryshopping #medical #mutualfunds #insurance #holidayplanning #smartshopping #wholesaleshopping #social #socialrevolution #socialchange #socialcharity #socialism #globalsocialwork #changemakers #changeyourlife #changetheworld","","Mission 100% Literacy rate. Join this revolution with me to change the future. #ngo #socialworker #socialmedia #social #fundraising #gse #gsered #fundraiser #charity #charities #shopping #ecommerce #groceryshopping #medical #mutualfunds #insurance #holidayplanning #smartshopping #wholesaleshopping #social #socialrevolution #socialchange #socialcharity #socialism #globalsocialwork #changemakers #changeyourlife #changetheworld"));

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/fetch_socialpost.php?id="+ngoid;
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
//                                    Toast.makeText(getActivity(), "caption : "+ r.getString("caption"), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
//                                    textView.setVisibility(View.VISIBLE);
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
