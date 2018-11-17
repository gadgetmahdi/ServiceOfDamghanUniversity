package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * create with mahdi gadget 11/2018
 * baraye moshakhas kardane model ha
 */
public class Jsonmodels {

    @SerializedName("mehdovjLat")
    @Expose
    private int lat;

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

}
