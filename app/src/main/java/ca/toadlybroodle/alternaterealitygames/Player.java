package ca.toadlybroodle.alternaterealitygames;

import android.util.Log;

public class Player {

    private final String mFirebaseID;
    private final String mAlias;
    private int mReputationPts;
    private int mStreetCredPts;

    public Player(String id, String al, int rep, int stc) {
        mFirebaseID = id;
        mAlias = al;
        mReputationPts = rep;
        mStreetCredPts = stc;
    }

    public void DropTag() {
        mReputationPts++;
        Log.d(mAlias, "Tag dropped");
    }

    public void TakeDownTag() {
        mStreetCredPts++;
        Log.d(mAlias, "Tag removed");
    }

}
