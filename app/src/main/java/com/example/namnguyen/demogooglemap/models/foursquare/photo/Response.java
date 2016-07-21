
package com.example.namnguyen.demogooglemap.models.foursquare.photo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Response {

    @SerializedName("photos")
    @Expose
    private Photos photos;

    /**
     * 
     * @return
     *     The photos
     */
    public Photos getPhotos() {
        return photos;
    }

    /**
     * 
     * @param photos
     *     The photos
     */
    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

}
