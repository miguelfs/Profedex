package com.partiufast.profedex.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.partiufast.profedex.R;
import com.partiufast.profedex.app.AppController;
import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.data.User;
import com.partiufast.profedex.fragments.ProfileFragment;
import com.partiufast.profedex.fragments.TeacherInfoFragment;
import com.partiufast.profedex.fragments.TeacherListFragment;
import com.partiufast.profedex.helper.SQLiteHandler;
import com.partiufast.profedex.helper.SessionManager;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        TeacherListFragment.OnFragmentInteractionListener,
        TeacherInfoFragment.OnFragmentInteractionListener,
        ProfileFragment.OnLogoutClickedListener {

    static final int LOGIN_REQUEST = 0;
    static final int CHANGE_CENTER = 1;
    static final int NEW_TEACHER_INTENT = 2;
    static final String profileTag = "PROFILE_FRAGMENT";
    static final String professorListTag = "TEACHER_LIST_FRAGMENT";
    static final String professorInfoTag = "PROFESSOR_INFO_FRAGMENT";

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private SQLiteHandler db;
    private SessionManager session;

    private ProfileFragment mProfileFragment;
    private TeacherListFragment mTeacherFragment;
    private FloatingActionButton mFloatingButton;

    private TextView mUserNameNav;
    private TextView mEmailNav;

    public String mName;
    public String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFloatingButton = (FloatingActionButton) findViewById(R.id.fab);
        if (mFloatingButton != null) {
            mFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFloatingButtonClick();
                }
            });
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavDrawer = (NavigationView) findViewById(R.id.nav_view);
        if (mNavDrawer != null)
            mNavDrawer.setNavigationItemSelectedListener(this);

        mProfileFragment = new ProfileFragment();
        mTeacherFragment = new TeacherListFragment();

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        mNavDrawer.addHeaderView(header);
        mUserNameNav = (TextView)header.findViewById(R.id.user_name_nav);
        mEmailNav = (TextView)header.findViewById(R.id.email_nav);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        mName = user.get("name");
        mEmail = user.get("email");

        // Start with teacher list
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, mTeacherFragment).commit();
        // Show fab only on list fragment
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() != 0) {
                    FragmentManager.BackStackEntry bk = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
                    String name = bk.getName();
                    Fragment fragment = fragmentManager.findFragmentByTag(name);
                    if (fragment instanceof TeacherListFragment)
                        mFloatingButton.show();
                    else
                        mFloatingButton.hide();
                }
                else
                    mFloatingButton.show();
            }
        });

        Intent i = getIntent();
        Professor mProfessor = (Professor) i.getSerializableExtra(AddTeacherActivity.NEW_TEACHER_DATA_INTENT);
        if (mProfessor != null){
            mTeacherFragment.setNewTeacherToList(mProfessor);
        }
    }


    public void hideFAB(boolean hide) {
        if(hide)
            mFloatingButton.hide();
        else
            mFloatingButton.show();
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        AppController.getInstance().user = new User();
        mProfileFragment.updateUserInfo();
    }

    private void loginUser(){
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent,LOGIN_REQUEST);
        mProfileFragment.updateUserInfo();
    }

    public void loggedUser(){
        /*
        HashMap<String, String> user = db.getUserDetails();
        mName = user.get("name");
        mEmail = user.get("email");*/
        if (AppController.getInstance().user.isError() == false) {
            mProfileFragment.updateUserInfo();
            mUserNameNav.setText(AppController.getInstance().user.getName());
            mEmailNav.setText(AppController.getInstance().user.getEmail());

            session.setLogin(true);
        }
        else {
            mProfileFragment.setUserInfo("", "", false);
        }
    }

    public boolean logged() {
        return session.isLoggedIn();
    }

    //When clicking back returns to the last fragment on the stack.
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            FragmentManager.BackStackEntry bk = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
            String name = bk.getName();
            Fragment fragment = fragmentManager.findFragmentByTag(name);
            if (fragment.getClass().equals(TeacherInfoFragment.class))
                mFloatingButton.show();
        }
        else{
            mFloatingButton.show();
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*if (id == R.id.select_campus){
            Intent intent = new Intent(MainActivity.this, CentroActivity.class);
            startActivityForResult(intent, CHANGE_CENTER);
            return true;
        }*/
        item.setChecked(true);
        setTitle(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();

        int id = item.getItemId();

        switch(id) {
            case R.id.nav_camera:
                fragmentManager.beginTransaction().replace(R.id.flContent, mProfileFragment, profileTag)
                        .addToBackStack("fragment").commit();
                mFloatingButton.hide();
                break;
            case R.id.nav_gallery:
                fragmentManager.beginTransaction().replace(R.id.flContent, mTeacherFragment, professorListTag)
                        .addToBackStack("fragment").commit();
                mFloatingButton.show();
                break;
            /*case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;*/
            case R.id.nav_rate_play_store:
                Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=partiufast.com.euetilico&hl=pt_BR" + this.getPackageName())));
                }
                break;
            case R.id.nav_info:
                break;
            default:
                fragmentManager.beginTransaction().replace(R.id.flContent, mProfileFragment, profileTag)
                        .addToBackStack("fragment").commit();
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
        return true;
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                loggedUser();
            }
        }
        else if (requestCode == CHANGE_CENTER) {
            /**
             * Do something with the result
             */
        }
        else if (requestCode == NEW_TEACHER_INTENT) {
            mTeacherFragment.refreshData();
        }
    }

    /*
    Fragment Interaction
     */
    public void onFragmentInteractionTeacher(Uri uri){
        //you can leave it empty
    }

    public void onFragmentInteractionProfile(Uri uri){
        //you can leave it empty
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onLogoutClicked() {
        if (!session.isLoggedIn()) {
            loginUser();
        } else {
            logoutUser();
        }
    }
    public void onFloatingButtonClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible()) {
                    if (fragment instanceof TeacherInfoFragment) {
                        ((TeacherInfoFragment) fragment).sendCommentData();
                        break;
                    }
                    if (fragment instanceof TeacherListFragment) {
                        Intent intent = new Intent(MainActivity.this, AddTeacherActivity.class);
                        startActivityForResult(intent,NEW_TEACHER_INTENT);
                        break;
                    }
                }
            }
        }
    }

}
