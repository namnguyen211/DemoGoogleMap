
package com.example.namnguyen.demogooglemap.models.foursquare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("venues")
    @Expose
    private List<Venue> venues = new ArrayList<Venue>();

    /**
     * 
     * @return
     *     The venues
     */
    public List<Venue> getVenues() {
        return venues;
    }

    /**
     * 
     * @param venues
     *     The venues
     */
    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}
