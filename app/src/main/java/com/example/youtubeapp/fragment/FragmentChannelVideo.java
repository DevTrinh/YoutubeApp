package com.example.youtubeapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.adapter.AdapterVideoChannelTest;
import com.example.youtubeapp.adapter.AdapterVideoInChannel;
import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemInfoChannel;
import com.example.youtubeapp.item.ItemVideoInChannel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentChannelVideo extends Fragment implements InterfaceDefaultValue {

    private TextView tvSortBy;
    private TextView tvVideos;
    private TextView tvLive;
    private RecyclerView rvVideos;
    private AdapterVideoInChannel adapterVideoInChannel;
    private AdapterVideoChannelTest adapterVideoChannelTest;

    private ItemInfoChannel itemInfoChannel;

    private ArrayList<ItemVideoInChannel> listVideo = new ArrayList<>();

    private int positionStartLoad = 0, positionEndLoad = 8;

    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage = 5;
    private int currentPage = 1;


    public FragmentChannelVideo(ItemInfoChannel itemInfoChannel) {
        this.itemInfoChannel = itemInfoChannel;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_video, container, false);
        mapping(view);

        final String url = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&channelId="
                + itemInfoChannel.getIdChannel()
                + "&maxResults=50&order=rating&type=video&key="
                + API_KEY;
        getJsonData(url,positionStartLoad, positionEndLoad);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvVideos.setLayoutManager(linearLayoutManager);
        adapterVideoChannelTest = new AdapterVideoChannelTest(listVideo, getActivity());
        rvVideos.setAdapter(adapterVideoChannelTest);

//        if (currentPage <  totalPage){
//            adapterVideoInChannel.addFooterLoading();
//        } else{
//            isLastPage = true;
//        }

//        rvVideos.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
//            @Override
//            public void loadMoreItem() {
//                isLoading = true;
//                currentPage +=1;
//                loadNextPage();
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//        });
        return view;
    }

    private void loadNextPage(){

    }

    private void getJsonData(String url, int start, int end){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String titleVideo;
                    String timeUpVideo;
                    String viewCount;
                    String urlImage;
                    String idVideo;
                    String linkStatistic = "";
                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    for (int i = start; i<end; i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonId = jsonItem.getJSONObject(ID);
                        idVideo = jsonId.getString(ID_VIDEO);
//                        Log.d("Video ID: ", idVideo);
                        JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                        titleVideo = jsonSnippet.getString(TITLE);;
//                        Log.d("TITLE: ", titleVideo);
                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject(THUMBNAIL);
                        JSONObject jsonHigh = jsonThumbnail.getJSONObject(HIGH);
                        urlImage = jsonHigh.getString(URL);
//                        Log.d("URL IMAGE: ", urlImage);
                        timeUpVideo = jsonSnippet.getString(PUBLISHED_AT);
//                        Log.d("TIMEUP VIDEO: ", timeUpVideo);
                        linkStatistic = "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&id="
                                + idVideo+ "&key="
                                +API_KEY;
                        Log.d("LINK STATIC VIDEO: "+i, linkStatistic);
                        listVideo.add(new ItemVideoInChannel(titleVideo, timeUpVideo,
                                urlImage, idVideo));
                        getStatisticsVideo(linkStatistic, i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error
                        +" FALSE GET DATA VIDEO CHANNEL",
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getStatisticsVideo(String url, int position){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray(ITEMS);
//                    Log.d("JSON ITEM: ", jsonItem.length()+"");
                    JSONObject jsonItem = jsonItems.getJSONObject(0);
                    JSONObject jsonStatistic = jsonItem.getJSONObject(STATISTICS);
                    listVideo.get(position).setViewCount(jsonStatistic.getString(VIEW_COUNT));
                    Log.d("VIEW COUNT: "+ position, ItemVideoInChannel.formatData(Integer.parseInt(jsonStatistic.getString(VIEW_COUNT))));
                    adapterVideoChannelTest.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error+"FALSE GET LINK STATISTICS", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void mapping(@NonNull View view){
        tvLive = view.findViewById(R.id.tv_live_channel);
        tvSortBy = view.findViewById(R.id.tv_sort_video);
        tvVideos = view.findViewById(R.id.tv_video_channel);
        rvVideos = view.findViewById(R.id.rv_contains_video_channel);
    }
}
