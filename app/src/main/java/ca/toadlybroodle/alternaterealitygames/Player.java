package ca.toadlybroodle.alternaterealitygames;

import android.location.Location;
import android.util.Log;

public class Player {

    private static final String TAG = "FuckinPlayr";

    private final String mFirebaseID;
    private final String mAlias;
    private int mReputationPts;
    private int mStreetCredPts;
    private Location mCurrentLocation;

    // null constructor
    public Player () {
        mFirebaseID = null;
        mAlias = null;
        mReputationPts = 0;
        mStreetCredPts = 0;
        mCurrentLocation = null;

    }

    // constructor with all parameters
    public Player(String id, String al, int rep, int stc, Location loc) {
        mFirebaseID = id;
        mAlias = al;
        mReputationPts = rep;
        mStreetCredPts = stc;
        mCurrentLocation = loc;
    }

}
