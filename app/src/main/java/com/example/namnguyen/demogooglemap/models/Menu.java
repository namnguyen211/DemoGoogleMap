
package com.example.namnguyen.demogooglemap.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Menu {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("anchor")
    @Expose
    private String anchor;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("mobileUrl")
    @Expose
    private String mobileUrl;
    @SerializedName("externalUrl")
    @Expose
    private String externalUrl;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The anchor
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * 
     * @param anchor
     *     The anchor
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The mobileUrl
     */
    public String getMobileUrl() {
        return mobileUrl;
    }

    /**
     * 
     * @param mobileUrl
     *     The mobileUrl
     */
    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    /**
     * 
     * @return
     *     The externalUrl
     */
    public String getExternalUrl() {
        return externalUrl;
    }

    /**
     * 
     * @param externalUrl
     *     The externalUrl
     */
    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

}
