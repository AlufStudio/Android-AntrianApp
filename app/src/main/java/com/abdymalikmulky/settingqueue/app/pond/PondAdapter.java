package com.abdymalikmulky.settingqueue.app.pond;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond;

import java.util.ArrayList;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/28/17.
 */

public class PondAdapter extends RecyclerView.Adapter<PondAdapter.ViewHolder> {
    private ArrayList<Pond> ponds;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPond;
        public TextView tvPondName;
        public TextView tvPondUser;


        public ViewHolder(View v) {
            super(v);
            imgPond = (ImageView) v.findViewById(R.id.pond_img);
            tvPondName = (TextView)v.findViewById(R.id.pond_name);
            tvPondUser = (TextView)v.findViewById(R.id.pond_user);
        }
    }

    public PondAdapter(ArrayList<Pond> ponds) {
        this.ponds = ponds;
    }

    @Override
    public PondAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pond, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPondName.setText(ponds.get(position).getName());
        holder.tvPondUser.setText(String.valueOf(ponds.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return ponds.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

