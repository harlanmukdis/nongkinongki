package suryakancana.nongkinongki.data;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.functions.Consumer;
import suryakancana.nongkinongki.data.local.UserStorage;
import suryakancana.nongkinongki.data.remote.AuthAPI;
import suryakancana.nongkinongki.data.remote.UserAPI;
import suryakancana.nongkinongki.models.User;

public class DataManager implements DataManagerType {
    private static DataManager dm;

    public static DataManager can () {
        if (dm == null) {
            dm = new DataManager();
        }
        return dm;
    }

    private static AuthAPI sAuthAPI     = new AuthAPI();
    private static UserAPI sUserAPI     = new UserAPI();
    private static UserStorage sUserStorage = new UserStorage();

    @Override
    public Maybe<JsonObject> login (String id, String password) {
        return sAuthAPI.login(id, password);
    }

    @Override
    public void logout ()
    {
        sAuthAPI.logout();
    }

    @Override
    public Maybe<JsonObject> forgotPassword (String id) { return sAuthAPI.forgotPassword(id);}

    @Override
    public Maybe<List<User>> getUserList () {
        return Maybe.concat(sUserStorage.getList(), sUserAPI.getList().doOnSuccess(new Consumer<List<User>>()
        {
            @Override
            public void accept (List<User> users) throws Exception
            {
                sUserStorage.addAll(users);
            }
        })).firstElement();
    }

    @Override
    public Maybe<User> getUser (Integer id) {
        return Maybe.concat(sUserStorage.get(id), sUserStorage.get(id).doOnSuccess(new Consumer<User>()
        {
            @Override
            public void accept (User user) throws Exception
            {
                sUserStorage.add(user);
            }
        })).firstElement();
    }

    @Override
    public String getUserToken ()
    {
        return sUserStorage.getToken();
    }
}
