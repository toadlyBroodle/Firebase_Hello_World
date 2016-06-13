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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements FragmentListenerInterface {

    private static final String TAG = "FuckMnAct";
    private static final String MY_PREFS_NAME = "MyPrefsFile";

    private static FragmentManager mFragMan;
    private static SupportMapFragment mMapFragment;

    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference myRef;
    private static FirebaseUser mFirebaseUser;

    /** drawer implementation copied from example at https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** setup fragment management stuff */
        mFragMan = getSupportFragmentManager();

        /** navigation drawer stuff */
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


        /** firebase stuff */
        // get database instance
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        // check for signed in user
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // if user not signed in, then start base sign in activity
        if (mFirebaseUser == null) {
            replaceFragment(BaseSignInFragment.class);
        }
        myRef = mFirebaseDatabase.getReference("message");


    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

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
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.menu_profile:
                // show profile page
                fragmentClass = BaseSignInFragment.class;
                break;
            case R.id.menu_database:
                fragmentClass = DatabaseFragment.class;
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
            default:
                fragmentClass = MainActivity.class;
        }

        // replace fragment, if successful handle navigation stuff
        if (replaceFragment(fragmentClass)) {

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
        }

        mDrawer.closeDrawers();
    }

    private boolean replaceFragment(Class fragClass) {
        // try to replace fragment
        Fragment frag;
        try {
            frag = (Fragment) fragClass.newInstance();

            // Insert the fragment by replacing any existing fragment
            mFragMan.beginTransaction()
                    .replace(R.id.placeholder_for_fragments, frag)
                    //.addToBackStack(Integer.toString(fragment.getId()))
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
            // TODO do stuff here with user
        }
    }

    // placeholder for loading future shared preferences
    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String name = prefs.getString("alias", null);
            String idName = prefs.getString("firebase_id", null);
        }
    }

    // placeholder for saving future shared preferences
    private void savePreferences() {

        // in case firebase user is null
        final String fbId = (mFirebaseUser == null)? null: mFirebaseUser.getUid();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("alias", "Elena");
        editor.putString("firebase_id", fbId);
        editor.apply();
    }

    // fires when associated method from FragmentListenerInterface is called
    @Override
    public void onMapViewCreatedCalled() {
        if (mMapFragment != null && mMapFragment.isInLayout()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            mMapFragment = (SupportMapFragment) mFragMan.findFragmentById(R.id.map);
            mMapFragment.getMapAsync((OnMapReadyCallback) mMapFragment);
        }
    }

    // fires when associated method from FragmentListenerInterface is called
    @Override
    public void onFragmentButtonPushed(View view) {
        switch (view.getId()) {
            case R.id.base_google_sign_in_button:
                replaceFragment(GoogleSignInFragment.class);
                break;

            case R.id.base_email_sign_in_button:
                replaceFragment(EmailSignInFragment.class);
                break;

            case R.id.write_database_button:
                Log.d(TAG, "Write to Database called");
                // Write a message to the database
                myRef.setValue("Hello, World!");
                break;

            case R.id.read_database_button:
                Log.d(TAG, "Read from Database called");
                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                break;
        }
    }

}