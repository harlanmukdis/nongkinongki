package suryakancana.nongkinongki.views.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import suryakancana.nongkinongki.R;
import suryakancana.nongkinongki.utils.HelperUtils;
import suryakancana.nongkinongki.utils.constants.I;
import suryakancana.nongkinongki.utils.constants.S;
import suryakancana.nongkinongki.views.fragments.BlankFragment;

/**
 * Created by LIMS on 16/01/2018.
 */

public class AuthActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private BottomBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        setupPermission();
        initUI();
        setupBottomBar();
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(I.LOCATION_REQUEST_CODE, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted (int requestCode, List<String> perms) {
        if (requestCode == I.LOCATION_REQUEST_CODE) {
            Toast.makeText(getApplicationContext(), R.string.permission_granted, Toast.LENGTH_LONG).show();
            super.initLocationDetection();
        }
    }

    @Override
    public void onPermissionsDenied (int requestCode, List<String> perms) {
        if (requestCode == I.LOCATION_REQUEST_CODE) {
            Toast.makeText(getApplicationContext(), R.string.permission_denied, Toast.LENGTH_LONG).show();
        }
    }

    private void setupPermission() {
        //request location permission early
        if (!EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions
                    .requestPermissions(AuthActivity.this, S.location_permission_message, I.LOCATION_REQUEST_CODE,
                            Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void initUI() {
        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
    }

    private void setupBottomBar() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Bundle bundle;
                switch (tabId) {
                    case R.id.action_home:
                        bundle = new Bundle();
                        HelperUtils.fragmentChanger(R.id.fl_fragment_container, getSupportFragmentManager(), new BlankFragment(), bundle);
                        break;
                    case R.id.action_history:
                        bundle = new Bundle();
                        HelperUtils.fragmentChanger(R.id.fl_fragment_container, getSupportFragmentManager(), new BlankFragment(), bundle);
                        break;
                    case R.id.action_my_account:
                        bundle = new Bundle();
                        HelperUtils.fragmentChanger(R.id.fl_fragment_container, getSupportFragmentManager(), new BlankFragment(), bundle);
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
