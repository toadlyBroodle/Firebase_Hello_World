package ca.toadlybroodle.alternaterealitygames;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnItemSelectedListener {

    private static final String TAG = "FuckMnAct";
    private static final String MY_PREFS_NAME = "MyPrefsFile";

    private static FragmentManager mFragMan;
    private static SupportMapFragment mMapFragment;

    private static FirebaseUser mFirebaseUser;
    private static Player mPlayer;

    /** drawer implementation copied from example at https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragMan = getSupportFragmentManager();

        mMapFragment = (SupportMapFragment) mFragMan.findFragmentById(R.id.placeholder_for_fragments);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // There is usually only 1 header view.
        // Multiple header views can technically be added at runtime.
        // We can use navigationView.getHeaderCount() to determine the total number.
        // get handel to header layout
        //View headerLayout = navigationView.getHeaderView(0);

        // check for signed in user
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // if user not signed in, then start base sign in activity
        if (mFirebaseUser == null) {
            Intent _BaseSignInActivity = new Intent(getApplicationContext(), BaseSignInActivity.class);
            startActivity(_BaseSignInActivity);
        }


        // TODO load player here

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // ensure
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.menu_account:
                // TODO show account page
                break;
            case R.id.menu_location:
                // take care of location permissions, settings, and updating
                fragmentClass = LocationFragment.class;
                break;
            case R.id.menu_map:
                fragmentClass = MapsFragment.class;
                break;
            case R.id.menu_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_share:
                // TODO send to share page
                break;
            case R.id.menu_rate:
                // TODO send to ratings page
                break;
            default:
                fragmentClass = MainActivity.class;
        }

        // if fragment is null, null pointer exception will be caught
        try {
            fragment = (Fragment) fragmentClass.newInstance();

            // Insert the fragment by replacing any existing fragment
            mFragMan.beginTransaction()
                    .replace(R.id.placeholder_for_fragments, fragment)
                    //.addToBackStack(Integer.toString(fragment.getId()))
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (fragment != null) {

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
        }

        mDrawer.closeDrawers();
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPreferences();


    }


    @Override
    protected void onPause() {
        super.onPause();

        savePreferences();
    }

    public static void setFireBaseUser(FirebaseUser user) {
        mFirebaseUser = user;

        if (mFirebaseUser != null) {
            // TODO try loading player, if no player found then create a new one
        }
    }

    public void statusClicked(View view) {
        Intent _BaseSignInActivity = new Intent(getApplicationContext(), BaseSignInActivity.class);
        startActivity(_BaseSignInActivity);
    }

    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String name = prefs.getString("alias", null);
            String idName = prefs.getString("firebase_id", null);
        }
    }

    private void savePreferences() {

        // in case firebase user is null
        final String fbId = (mFirebaseUser == null)? null: mFirebaseUser.getUid();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("alias", "Elena");
        editor.putString("firebase_id", fbId);
        editor.apply();
    }

    // Now we can define the action to take in the activity when the map fragment event fires
    // This is implementing the `OnItemSelectedListener` interface methods
    @Override
    public void onViewCreatedCalled() {
        if (mMapFragment != null && mMapFragment.isInLayout()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            mMapFragment = (SupportMapFragment) mFragMan.findFragmentById(R.id.map);
            mMapFragment.getMapAsync((OnMapReadyCallback) mMapFragment);
        }
    }

}