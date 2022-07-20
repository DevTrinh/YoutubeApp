package com.example.youtubeapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.item.ItemVideoInChannel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterVideoChannelTest extends RecyclerView.Adapter<AdapterVideoChannelTest.ItemVideoInChannelViewHolder> {

    ArrayList<ItemVideoInChannel> list;
    private Context context;

    public AdapterVideoChannelTest(ArrayList<ItemVideoInChannel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemVideoInChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos_channel, null);
        return new ItemVideoInChannelViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ItemVideoInChannelViewHolder holder, int position) {
        int width = (context.getResources().getDisplayMetrics().widthPixels);

        ItemVideoInChannel itemVideoInChannel = list.get(position);

        holder.tvTimeUp.setText(itemVideoInChannel.getTimeUpVideo());
        holder.tvTitleVideo.setText(itemVideoInChannel.getTitleVideo());
        holder.tvViewCount.setText("23 views");
        Picasso.get().load(itemVideoInChannel.getUrlImage()).into(holder.ivVideo);
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        else{
            return 0;
        }
    }

    public class ItemVideoInChannelViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivVideo;
        private TextView tvTitleVideo;
        private TextView tvTimeUp;
        private TextView tvViewCount;
        private ConstraintLayout clContainsVideoChannel;

        public ItemVideoInChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            ivVideo = itemView.findViewById(R.id.iv_videos_in_channel);
            tvTitleVideo = itemView.findViewById(R.id.tv_title_video_channel);
            tvTimeUp = itemView.findViewById(R.id.tv_time_up);
            tvViewCount = itemView.findViewById(R.id.tv_amount_view);
        }
    }
}
