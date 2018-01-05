package suryakancana.nongkinongki.viewmodels;

import suryakancana.nongkinongki.models.User;
import suryakancana.nongkinongki.viewmodels.inputs.UserViewModelInputs;
import suryakancana.nongkinongki.viewmodels.outputs.UserViewModelOutputs;

public class UserViewModel extends BaseViewModel implements UserViewModelInputs, UserViewModelOutputs {
    private User mUser;

    public UserViewModel (User user) {
        mUser = user;
        notifyChange();
    }

    @Override
    public void setUser (User user) {
        mUser = user;
        notifyChange();
    }

    @Override
    public String getId ()
    {
        return String.valueOf(mUser.getId());
    }

    @Override
    public String getName ()
    {
        return String.valueOf(mUser.getName());
    }

    @Override
    public String getEmail ()
    {
        return String.valueOf(mUser.getEmail());
    }


}
