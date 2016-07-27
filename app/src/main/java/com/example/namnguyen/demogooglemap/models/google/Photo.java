
package com.example.namnguyen.demogooglemap.models.google;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.example.namnguyen.demogooglemap.DirectionFinder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo  {

    public static final  String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?";

    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("html_attributions")
    @Expose
    private List<String> htmlAttributions = new ArrayList<String>();
    @SerializedName("photo_reference")
    @Expose
    private String photoReference;
    @SerializedName("width")
    @Expose
    private Integer width;


    /**
     * 
     * @return
     *     The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The htmlAttributions
     */
    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    /**
     * 
     * @param htmlAttributions
     *     The html_attributions
     */
    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    /**
     * 
     * @return
     *     The photoReference
     */
    public String getPhotoReference() {
        return photoReference;
    }

    /**
     * 
     * @param photoReference
     *     The photo_reference
     */
    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    /**
     * 
     * @return
     *     The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getUrl(){
        return PHOTO_URL +"maxwidth="+getWidth()+"&photoreference="+getPhotoReference()+"&key="+ DirectionFinder.GOOGLE_API_KEY;
    }
}
