package suryakancana.nongkinongki.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by LIMS on 16/01/2018.
 */

public class HelperUtils {
    public static boolean isHome = true; //status fragment

    // For first fragment in every activity
    public static void fragmentInitializer(int resource,
                                           FragmentManager fragmentManager,
                                           Fragment fragment,
                                           Bundle bundle) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (bundle != null) fragment.setArguments(bundle);

        fragmentTransaction.add(resource, fragment);
        fragmentTransaction.commit();
    }

    public static void fragmentChanger(int resource,
                                       FragmentManager fragmentManager,
                                       Fragment fragment,
                                       Bundle bundle) {
        if (fragment != null) {
            if (bundle != null) fragment.setArguments(bundle);
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            transaction.replace(resource, fragment);
            int i = fragmentManager.getBackStackEntryCount();
            if (fragmentManager.getBackStackEntryCount() < 1) //container isi 1 fragment
                transaction.addToBackStack(null); //container isi > 1 fragment
            if (!isHome)
                transaction.addToBackStack("NotHome"); //TAG fragment
//            if (resource != R.id.layout_verify_container)
//                transaction.addToBackStack(null); //container isi > 1 fragment
            /*if (resource != R.id.fl_login_frame) {
                if (fragment instanceof HomepageFragment) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    transaction.addToBackStack(null);
                    if (fragmentManager.getBackStackEntryCount() > 0) {
                        fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            }*/
            transaction.commit();
        }
    }
}
