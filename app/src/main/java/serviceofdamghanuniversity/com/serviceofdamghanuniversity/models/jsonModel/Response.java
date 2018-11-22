package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * create with mahdi gadget 11/2018
 * baraye moshakhas kardane model ha
 */
public class Response {


    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
