package com.example.namnguyen.demogooglemap.events;

/**
 * Created by Nam Nguyen on 05/07/2016.
 */
public class KeyWordSubmitEvent {

    private String mQuery;

    public KeyWordSubmitEvent(String query) {
        this.mQuery = query;
    }

    public String getmQuery() {
        return mQuery;
    }
}
