package com.example.namnguyen.demogooglemap;

import com.example.namnguyen.demogooglemap.models.Route;

import java.util.List;



/**
 * Created by Nam Nguyen on 12/07/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
