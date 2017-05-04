package com.leafcastlelabs.demoui.adaptors;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leafcastlelabs.demoui.R;

/**
 * Created by kasper on 17/04/17.
 */

/* Class code modified from code example at:
https://developer.android.com/training/material/lists-cards.html
 */
public class CardDisplayAdaptor extends RecyclerView.Adapter<CardDisplayAdaptor.ViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtCardtext;
        public CardView crdCard;
        public ViewHolder(View v) {
            super(v);
            crdCard = (CardView) v.findViewById(R.id.crdCard);
            txtCardtext = (TextView) v.findViewById(R.id.txtCardtext);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardDisplayAdaptor(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardDisplayAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlist_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtCardtext.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

