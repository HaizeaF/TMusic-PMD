package com.example.tmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterSong extends RecyclerView.Adapter<CustomAdapterSong.MyViewHolder> implements  View.OnClickListener{
    private Context context;
    private ArrayList arrayListId, arrayListAuthorName, arrayListSongName;
    private View.OnClickListener listener;

    public CustomAdapterSong(Context context, ArrayList arrayListId, ArrayList arrayListAuthorName, ArrayList arrayListSongName, View.OnClickListener listener) {
        this.context = context;
        this.arrayListId = arrayListId;
        this.arrayListAuthorName = arrayListAuthorName;
        this.arrayListSongName = arrayListSongName;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.song_recycle_view_template, parent, false);

        view.setOnClickListener(this);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewId.setText(String.valueOf(arrayListId.get(position)));
        holder.textViewSongName.setText(String.valueOf(arrayListSongName.get(position)));
        holder.textViewAuthorName.setText(String.valueOf(arrayListAuthorName.get(position)));
    }

    @Override
    public int getItemCount() { return arrayListId.size(); }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewId, textViewAuthorName, textViewSongName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewAuthorName = itemView.findViewById(R.id.textViewAuthorName);
            textViewSongName = itemView.findViewById(R.id.textViewSongName);
        }
    }
}
