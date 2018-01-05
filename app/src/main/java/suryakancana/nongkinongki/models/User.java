package suryakancana.nongkinongki.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIMS on 05/01/2018.
 */

public class User {
    @SerializedName("id")
    public int    id;
    @SerializedName ("name")
    public String name;
    @SerializedName ("email")
    public String email;

    public User (int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId ()
    {
        return id;
    }

    public String getName ()
    {
        return name;
    }

    public String getEmail () {
        return email;
    }
}
