package com.abdymalikmulky.settingqueue.pond;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdymalikmulky.settingqueue.R;

import java.util.ArrayList;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/28/17.
 */

public class PondAdapter extends RecyclerView.Adapter<PondAdapter.ViewHolder> {
    private ArrayList<Pond> ponds;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public PondAdapter(ArrayList<Pond> ponds) {
        this.ponds = ponds;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PondAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pond, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPondName.setText(ponds.get(position).getName());
        holder.tvPondUser.setText(ponds.get(position).getUser());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ponds.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

