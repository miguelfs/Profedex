package com.partiufast.profedex.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.partiufast.profedex.R;
import com.partiufast.profedex.fragments.ProfileFragment;
import com.partiufast.profedex.fragments.TeacherInfoFragment;
import com.partiufast.profedex.fragments.TeacherListFragment;
import com.partiufast.profedex.helper.SQLiteHandler;
import com.partiufast.profedex.helper.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        TeacherListFragment.OnFragmentInteractionListener,
        TeacherInfoFragment.OnFragmentInteractionListener,
        ProfileFragment.OnLogoutClickedListener {

    static final int LOGIN_REQUEST = 0;
    static final int CHANGE_CENTER = 1;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private SQLiteHandler db;
    private SessionManager session;

    private ProfileFragment mProfileFragment;
    private TeacherListFragment mTeacherFragment;

    public String mName;
    public String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddTeacherActivity.class);
                    startActivity(intent);
                }
            });
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavDrawer = (NavigationView) findViewById(R.id.nav_view);
        mNavDrawer.setNavigationItemSelectedListener(this);

        mProfileFragment = new ProfileFragment();
        mTeacherFragment = new TeacherListFragment();

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
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        //mProfileFragment.setUserInfo(getResources().getString(R.string.not_logged), "");
    }

    private void loginUser(){
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent,LOGIN_REQUEST);
        //finish();
        //mProfileFragment.setLogin(true);
    }

    public void loggedUser(){
        HashMap<String, String> user = db.getUserDetails();
        mName = user.get("name");
        mEmail = user.get("email");
        mProfileFragment.setUserInfo(mName, mEmail, true);
    }

    public boolean logged() {
        return session.isLoggedIn();
    }

   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/


    //permite que, ao clicar em voltar, retorne para a fragment anterior na pilha.
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.select_campus){
            Intent intent = new Intent(MainActivity.this, CentroActivity.class);
            startActivityForResult(intent, CHANGE_CENTER);
            return true;
        }
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
                fragmentManager.beginTransaction().replace(R.id.flContent, mProfileFragment).addToBackStack("fragment").commit();
                break;
            case R.id.nav_gallery:
                fragmentManager.beginTransaction().replace(R.id.flContent, mTeacherFragment).addToBackStack("fragment").commit();
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                //fragmentClass = ProfileFragment.class;
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
    public void onLogoutClicked() {
        if (!session.isLoggedIn()) {
            loginUser();
        } else {
            logoutUser();
            mProfileFragment.setUserInfo("Faça Login", "", false);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
