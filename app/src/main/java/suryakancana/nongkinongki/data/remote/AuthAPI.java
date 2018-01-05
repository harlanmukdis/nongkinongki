package suryakancana.nongkinongki.data.remote;

import com.google.gson.JsonObject;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import suryakancana.nongkinongki.data.remote.contracts.Authentication;
import suryakancana.nongkinongki.utils.constants.K;

public class AuthAPI extends BaseAPI implements Authentication {

    @Override
    public Maybe<JsonObject> login (String id, String password) {
        // TODO: define your own API URL
        return app.mAPIService.login(id)
                              .retry(K.MAX_RETRIES)
                              .subscribeOn(Schedulers.io());

        // TODO: 7/28/17 find new helper for retryWhen ( rx 2.1.2 )
    }

    @Override
    public void logout () {
    }

    @Override
    public Maybe<JsonObject> forgotPassword (String id) {
        // TODO: define your own API URL
        return app.mAPIService.forgotPassword(id)
                              .retry(K.MAX_RETRIES)
                              .subscribeOn(Schedulers.io());

        // TODO: 7/28/17 find new helper for retryWhen ( rx 2.1.2 )
    }
}
