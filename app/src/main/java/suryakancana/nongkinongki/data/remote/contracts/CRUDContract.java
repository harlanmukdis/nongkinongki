package suryakancana.nongkinongki.data.remote.contracts;

import java.util.List;

import io.reactivex.Maybe;
import suryakancana.nongkinongki.models.User;

public interface CRUDContract<T, U> {
    Maybe<List<User>> getList();

    void create(T obj);

    Maybe<User> read(U id);

    void update(T obj, U id);

    void delete(U id);
}
