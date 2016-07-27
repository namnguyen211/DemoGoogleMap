package com.example.namnguyen.demogooglemap.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.namnguyen.demogooglemap.models.google.Result;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class GooglePlaceResultFragmentRecyclerViewAdapter extends RecyclerView.Adapter<GooglePlaceResultFragmentRecyclerViewAdapter.ViewHolder> {

    private final List<Result> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context context;
    public static final String PHOTO="http://www.freeiconspng.com/uploads/warning-icon-5.png";

    public GooglePlaceResultFragmentRecyclerViewAdapter(Context context,List<Result> items, OnListFragmentInteractionListener listener) {
        this.context =context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_googleplaceresult, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getVicinity());
        if(mValues.get(position).getPhotos()!=null){
            if(mValues.get(position).getPhotos().size() != 0){
              String   a = mValues.get(position).getPhotos().get(0).getUrl();
                Glide.with(context).load(a).into(((ViewHolder) holder).imageView);
            }
        }else {
            Glide.with(context).load(PHOTO).into(((ViewHolder) holder).imageView);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("TitleGG",mValues.get(position).getName());
                if(mValues.get(position).getPhotos()!=null){
                    if(mValues.get(position).getPhotos().size() != 0){
                        intent.putExtra("photo1GG",mValues.get(position).getPhotos().get(0).getUrl());
                    }
                }else {
                    intent.putExtra("photo1GG",PHOTO);
                }

//                intent.putExtra("photo2GG",a);
                intent.putExtra("LatGG",mValues.get(position).getGeometry().getLocation().getLat().toString());
                intent.putExtra("LngGG",mValues.get(position).getGeometry().getLocation().getLng().toString());
                intent.putExtra("DetailActivity","SearchGoogle");
                context.startActivity(intent);
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
        public Result mItem;
        public ImageView imageView;

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
