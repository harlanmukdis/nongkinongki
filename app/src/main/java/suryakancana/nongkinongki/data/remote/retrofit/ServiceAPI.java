package suryakancana.nongkinongki.data.remote.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import suryakancana.nongkinongki.models.User;

public interface ServiceAPI {
    @GET
        //dynamic URL
    Maybe<JsonObject> dynamicRequest(@Url String url);

    /*
    * below are dummy URLs. Please change it into your API endpoints
    * TODO: replace below URLs with your own
    */

    @GET ("users/{id}")
    Maybe<JsonObject> login(@Path("id") String id);

    @GET ("users/{id}")
    Maybe<JsonObject> forgotPassword(@Path("id") String id);

    @GET ("users/")
    Maybe<List<User>> getUsers();

    @GET ("users/{id}")
    Maybe<User> getUser(@Path("id") String id);

    /* end dummy URLs */

}
