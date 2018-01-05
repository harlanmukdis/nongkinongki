package suryakancana.nongkinongki.data.remote;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import suryakancana.nongkinongki.data.remote.contracts.CRUDContract;
import suryakancana.nongkinongki.models.User;
import suryakancana.nongkinongki.utils.constants.K;

public class UserAPI extends BaseAPI implements CRUDContract<User, Integer> {

    @Override
    public Maybe<List<User>> getList () {
        return app.mAPIService.getUsers().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public void create (User obj) {
        // TODO: implement your own code 
    }

    @Override
    public Maybe<User> read (Integer id) {
        return app.mAPIService.getUser(id.toString()).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public void update (User obj, Integer id) {
        // TODO: implement your own code 
    }

    @Override
    public void delete (Integer id) {
        // TODO: implement your own code
    }
}
