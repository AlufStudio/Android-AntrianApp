package com.abdymalikmulky.settingqueue.app.ui.pond;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdymalikmulky.settingqueue.R;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/28/17.
 */

public class PondAdapter extends RecyclerView.Adapter<PondAdapter.ViewHolder> {
    private List<Pond> ponds = new ArrayList<>();

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
        Pond pond = ponds.get(position);

        holder.tvPondName.setText(pond.getName());
        holder.tvPondUser.setText(String.valueOf(pond.getClientId()));

        if(pond.getSyncState().equals(AppUtils.STATE_SYNCED)){
            holder.imgPond.setImageResource(R.mipmap.sync);

        }else{
            holder.imgPond.setImageResource(R.mipmap.notsync);

        }
    }

    @Override
    public int getItemCount() {
        return ponds.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void replace(List<Pond> ponds){
        Timber.d("DataSuccess : %s",ponds.toString());
        this.ponds = ponds;
        notifyDataSetChanged();
    }

    public void add(Pond pond){
        ponds.add(0, pond);
        notifyDataSetChanged();
    }
}

