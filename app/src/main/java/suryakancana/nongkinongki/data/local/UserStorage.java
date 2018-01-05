package suryakancana.nongkinongki.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import suryakancana.nongkinongki.data.local.contracts.AGERContract;
import suryakancana.nongkinongki.data.local.contracts.CacheContract;
import suryakancana.nongkinongki.models.User;
import suryakancana.nongkinongki.utils.constants.K;

public class UserStorage implements AGERContract<User, Integer>, CacheContract {
    @Override
    public boolean isCacheValid () {
        // TODO: 7/28/17 define your own cache validity
        return true;
    }

    @Override
    public Maybe<List<User>> getList () {
        List<User> users = isCacheValid() ? Hawk.get(K.USER_LIST, new ArrayList<User>()) : null;
        return Maybe.just(users).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<User> get (Integer id) {
        return Maybe.just(isCacheValid() ? Hawk.get(String.format(K.USER_DETAIL, id), new User(0, "", "")) : null);
    }

    @Override
    public void add (User obj)
    {
        Hawk.put(String.format(K.USER_DETAIL, obj.getId()), obj);
    }

    @Override
    public void addAll (List<User> objs)
    {
        Hawk.put(K.USER_LIST, objs);
    }

    @Override
    public void edit (User obj, Integer id)
    {
        Hawk.put(String.format(K.USER_DETAIL, id), obj);
    }

    @Override
    public void delete (Integer id)
    {
        Hawk.delete(String.format(K.USER_DETAIL, id));
    }

    public String getToken ()
    {
        return "";
    }
}
