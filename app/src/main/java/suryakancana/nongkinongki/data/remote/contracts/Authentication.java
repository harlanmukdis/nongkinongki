package suryakancana.nongkinongki.data.remote.contracts;

import com.google.gson.JsonObject;

import io.reactivex.Maybe;

public interface Authentication {
    Maybe<JsonObject> login(String id, String password);

    void logout();

    Maybe<JsonObject> forgotPassword(String id);
}
