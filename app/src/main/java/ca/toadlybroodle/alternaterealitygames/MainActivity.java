package ca.toadlybroodle.alternaterealitygames;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FuckMnAct";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private static FirebaseUser mFirebaseUser;
    private static Player mPlayer;

    private static LocationActivity mLocationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // check for signed in user
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // if user not signed in, then start base sign in activity
        if (mFirebaseUser == null) {
            Intent _BaseSignInActivity = new Intent(getApplicationContext(), BaseSignInActivity.class);
            startActivity(_BaseSignInActivity);
        }


        // TODO load player here

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_account:
                // TODO show account page
                return true;
            case R.id.menu_location:
                // take care of location permissions, settings, and updating
                if (mLocationActivity == null) {
                Intent _LocationActivity = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(_LocationActivity);
                }
                return true;
            case R.id.menu_map:
                // TODO this is a fragment NOT an activity, have to treat it differently or CRASH
                Intent _MapsActivity = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(_MapsActivity);
                return true;
            case R.id.menu_settings:
                Intent _SettingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(_SettingsActivity);
                return true;
            case R.id.menu_share:
                // TODO send to share page
                return true;
            case R.id.menu_rate:
                // TODO send to ratings page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String name = prefs.getString("alias", null);
            String idName = prefs.getString("firebase_id", null);
        }
    }

    public void savePreferences() {

        // in case firebase user is null
        final String fbId = (mFirebaseUser == null)? null: mFirebaseUser.getUid();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("alias", "Elena");
        editor.putString("firebase_id", fbId);
        editor.commit();
    }

}
