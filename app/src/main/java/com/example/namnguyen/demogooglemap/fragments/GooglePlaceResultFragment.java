package com.example.namnguyen.demogooglemap.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.namnguyen.demogooglemap.DirectionFinder;
import com.example.namnguyen.demogooglemap.adapters.GooglePlaceResultFragmentRecyclerViewAdapter;
import com.example.namnguyen.demogooglemap.apis.GoogleApi;
import com.example.namnguyen.demogooglemap.events.KeyWordSubmitEvent;
import com.example.namnguyen.demogooglemap.OnListFragmentInteractionListener;
import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.dummy.DummyContent;
import com.example.namnguyen.demogooglemap.models.google.GoogleResponse;
import com.example.namnguyen.demogooglemap.models.google.Result;
import com.example.namnguyen.demogooglemap.services.GooglePlaceServiceGenerator;

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
public class GooglePlaceResultFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    GoogleApi googleApi;
    GooglePlaceResultFragmentRecyclerViewAdapter viewAdapter;
    List<Result> resultList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GooglePlaceResultFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GooglePlaceResultFragment newInstance(int columnCount) {
        GooglePlaceResultFragment fragment = new GooglePlaceResultFragment();
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
        View view = inflater.inflate(R.layout.fragment_googleplaceresult_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            viewAdapter = new GooglePlaceResultFragmentRecyclerViewAdapter(resultList,mListener);
            recyclerView.setAdapter(viewAdapter);
        }
        return view;
    }

    @Subscribe
    public void onEvent(KeyWordSubmitEvent event){

        googleApi = GooglePlaceServiceGenerator.createService(GoogleApi.class);
        Call<GoogleResponse> call = googleApi.searchPlace("10.796097,106.676170",event.getmQuery(), DirectionFinder.GOOGLE_API_KEY);
        call.enqueue(new Callback<GoogleResponse>() {
            @Override
            public void onResponse(Call<GoogleResponse> call, Response<GoogleResponse> response) {
                resultList.clear();;
                resultList.addAll(response.body().getResults());
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GoogleResponse> call, Throwable t) {

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
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
