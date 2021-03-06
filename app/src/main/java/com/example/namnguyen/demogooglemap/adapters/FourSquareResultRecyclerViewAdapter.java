package com.example.namnguyen.demogooglemap.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.namnguyen.demogooglemap.OnListFragmentInteractionListener;
import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.activities.DetailActivity;
import com.example.namnguyen.demogooglemap.dummy.DummyContent.DummyItem;
import com.example.namnguyen.demogooglemap.models.foursquare.Venue;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FourSquareResultRecyclerViewAdapter extends RecyclerView.Adapter<FourSquareResultRecyclerViewAdapter.ViewHolder> {

    private final List<Venue> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context context;

    public FourSquareResultRecyclerViewAdapter(Context context,List<Venue> items, OnListFragmentInteractionListener listener) {
       this.context = context;
        mValues = items;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_foursquareresult, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getLocation().getFormattedAddress().get(0).toString());
        if(mValues.get(position).getPhotos() != null){
            if(mValues.get(position).getPhotos().getItems() != null){
                if(mValues.get(position).getPhotos().getItems().size() != 0){
                    String a = mValues.get(position).getPhotos().getItems().get(0).getUrl();
                    Glide.with(context).load(a).into(((ViewHolder) holder).imageView);
                }
            }
        }else {
            Glide.with(context).load(GooglePlaceResultFragmentRecyclerViewAdapter.PHOTO).into(((ViewHolder) holder).imageView);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("TitleFS",mValues.get(position).getName());
                intent.putExtra("IdFS",mValues.get(position).getId());
                intent.putExtra("LatFS",mValues.get(position).getLocation().getLat().toString());
                intent.putExtra("LngFS",mValues.get(position).getLocation().getLng().toString());
                intent.putExtra("DetailActivity","SearchFourSquare");
                context.startActivity(intent);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ImageView imageView;
        public Venue mItem;

        public ViewHolder(View view) {
            super(view);
            mView = (CardView) view.findViewById(R.id.view);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            imageView = (ImageView) view.findViewById(R.id.imv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
