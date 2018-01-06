package suryakancana.nongkinongki.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import suryakancana.nongkinongki.R;

/**
 * Created by LIMS on 06/01/2018.
 */

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private LinearLayout llDots;
    private TextView[] dots;
    private Button btnSkip, btnNext;
    private LoginButton btnSignFb;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;

    private int[] layouts;
    private boolean loggedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_walkthrough);

        initializeLayout();
        assignValue();
        assignListener();
        setupViewPager();
        initializeLoginFbButton(btnSignFb);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        mCallbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    private void initializeLayout() {
        viewPager = (ViewPager) findViewById(R.id.vp_walkthrough_container);
        llDots = (LinearLayout) findViewById(R.id.dots_walkthrough);
        btnSkip = (Button) findViewById(R.id.btn_walkthrough_skip);
        btnNext = (Button) findViewById(R.id.btn_walkthrough_next);
        btnSignFb = (LoginButton) findViewById(R.id.btn_walkthrough_facebook);
    }

    private void assignListener() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
//                    launchHomeScreen();
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                launchHomeScreen();
            }
        });
    }

    private void assignValue() {
        layouts = new int[]{
                R.layout.walkthrough_first,
                R.layout.walkthrough_second,
                R.layout.walkthrough_third};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        loggedIn = AccessToken.getCurrentAccessToken() == null;
        if (loggedIn) {
            Toast.makeText(getApplicationContext(), "Not login ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Is login ", Toast.LENGTH_LONG).show();
        }
    }

    private void setupViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        llDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            llDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void initializeLoginFbButton(LoginButton loginButton) {
        mCallbackManager = CallbackManager.Factory.create();

//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                mAccessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
//                        if (newToken != null) {
//                            fbToken = newToken.getToken();
//                            SharedPreferences prefs = getSharedPreferences(Globalvars.PREFS_ADSVOKAT, Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = prefs.edit();
//                            editor.putString(Globalvars.PREFS_TAG_FB_ACCESS_TOKEN, newToken.getToken());
//                            editor.apply();
//                            if (profile == null) {
//                                profile = new User();
//                                profile.setFbAccessToken(fbToken);
//                            } else {
//                                profile.setFbAccessToken(fbToken);
//                            }
//                        } else {
//                            fbToken = null;
//                        }
                    }
                };
                mAccessTokenTracker.startTracking();

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile old, Profile newProfile) {
                            Profile fbProfile = newProfile;

//                            if (fbProfile != null) {
//                                profile = new User();
//                                profile.setFbId(fbProfile.getId());
//                                profile.setFirstName(fbProfile.getFirstName());
//                                profile.setLastName(fbProfile.getLastName());
//                                profile.setMiddleName(fbProfile.getLastName());
//                                profile.setName(fbProfile.getName());
//                                profile.setLinkUri(fbProfile.getLinkUri());
//
//                                profile.setFbAccessToken(loginResult.getAccessToken().getToken());
//                                getFbProfileData(profile, loginResult);
//                            }
                            mProfileTracker.stopTracking();
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    Profile fbProfile = Profile.getCurrentProfile();
//                    if (fbProfile != null) {
//                        profile = new User();
//                        profile.setFbId(fbProfile.getId());
//                        profile.setFirstName(fbProfile.getFirstName());
//                        profile.setLastName(fbProfile.getLastName());
//                        profile.setMiddleName(fbProfile.getLastName());
//                        profile.setName(fbProfile.getName());
//                        profile.setLinkUri(fbProfile.getLinkUri());
//
//                        profile.setFbAccessToken(loginResult.getAccessToken().getToken());
//                        getFbProfileData(profile, loginResult);
//                    }
                    Toast.makeText(getApplicationContext(), "Login success : "+fbProfile, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancel() {
                //canceled
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        };

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(mCallbackManager, callback);
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    /**
     * View pager adapter
     */
    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
