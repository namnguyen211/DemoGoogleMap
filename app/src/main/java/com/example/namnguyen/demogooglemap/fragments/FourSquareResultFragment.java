package com.example.namnguyen.demogooglemap.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.namnguyen.demogooglemap.apis.FourSquareApi;
import com.example.namnguyen.demogooglemap.adapters.FourSquareResultRecyclerViewAdapter;
import com.example.namnguyen.demogooglemap.models.FoursquareResponse;
import com.example.namnguyen.demogooglemap.services.FourSquareServiceGenerator;
import com.example.namnguyen.demogooglemap.events.KeyWordSubmitEvent;
import com.example.namnguyen.demogooglemap.OnListFragmentInteractionListener;
import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.models.Venue;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FourSquareResultFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private FourSquareApi foursquareApi;
    public List<Venue> venueList = new ArrayList<>();
    private FourSquareResultRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FourSquareResultFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FourSquareResultFragment newInstance(int columnCount) {
        FourSquareResultFragment fragment = new FourSquareResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foursquareresult_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new FourSquareResultRecyclerViewAdapter(getActivity(),venueList, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @Subscribe
    public void onEvent(KeyWordSubmitEvent event) {
        foursquareApi = FourSquareServiceGenerator.createService(FourSquareApi.class);
        Call<FoursquareResponse> call = foursquareApi.searchVenue("20130815", "10.7960682,106.6760491",200, event.getmQuery());
        call.enqueue(new Callback<FoursquareResponse>() {
            @Override
            public void onResponse(Call<FoursquareResponse> call, Response<FoursquareResponse> response) {
                Log.d("Success" , String.valueOf(response.body().getResponse().getVenues().size()));
                venueList.clear();
                venueList.addAll(response.body().getResponse().getVenues());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FoursquareResponse> call, Throwable t) {
                Log.d("Failure", t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
