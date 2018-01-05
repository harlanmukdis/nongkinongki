package suryakancana.nongkinongki.data;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Maybe;
import suryakancana.nongkinongki.models.User;

/**
 * Created by bukhoriaqid on 5/27/17.
 */

public interface DataManagerType
{
    /**
     * IMPORTANT !
     * ALL METHODS INSIDE DATAMANAGER MUST BE DEFINED HERE.
     * GROUP THE METHODS BASED ON THE MODULE.
     */

    Maybe<JsonObject> login(String id, String password);

    void logout();

    Maybe<JsonObject> forgotPassword(String id);

    Maybe<List<User>> getUserList();

    Maybe<User> getUser(Integer id);

    String getUserToken();
}
