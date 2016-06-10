package ca.toadlybroodle.alternaterealitygames;

import android.util.Log;

public class Player {

    public String mFirebaseID;
    public String mAlias;
    public int mReputationPts;
    public int mStreetCredPts;

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
