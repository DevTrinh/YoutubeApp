package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickWithPosition;

import java.util.ArrayList;

public class AdapterListHotKeys extends
        RecyclerView.Adapter<AdapterListHotKeys.ItemHotKeyViewHolder> {

    private ArrayList<String> listKey;
    private InterfaceClickWithPosition interfaceClickWithPosition;

    public AdapterListHotKeys(ArrayList<String> listKey, InterfaceClickWithPosition interfaceClickWithPosition) {
        this.listKey = listKey;
        this.interfaceClickWithPosition = interfaceClickWithPosition;
    }

    @NonNull
    @Override
    public ItemHotKeyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hot_keywords,
                        parent, false);
        return new ItemHotKeyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHotKeyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        String value = listKey.get(position);
        if (position == 0){
            holder.tvKeyWords.setBackgroundResource(R.drawable.bg_on_selected_key);
            holder.tvKeyWords.setTextColor(Color.WHITE);
        }
        holder.tvKeyWords.setText(value + "");

        holder.tvKeyWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickWithPosition.onClickWithPosition(value);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listKey != null) {
            return listKey.size();
        }
        return 0;
    }

    public class ItemHotKeyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvKeyWords;

        public ItemHotKeyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKeyWords = itemView.findViewById(R.id.tv_key_words);
        }
    }
}