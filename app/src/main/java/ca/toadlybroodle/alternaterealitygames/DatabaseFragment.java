package ca.toadlybroodle.alternaterealitygames;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DatabaseFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "FuckDbFrag";

    private FragmentActivity parActiv;

    // listener will notify main activity about things it needs to know about
    private FragmentListenerInterface listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // get reference to parent activity for later use
        parActiv = super.getActivity();

        return inflater.inflate(R.layout.fragment_database, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setup any handles to view objects here

        parActiv.findViewById(R.id.write_database_button).setOnClickListener(this);
        parActiv.findViewById(R.id.read_database_button).setOnClickListener(this);
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListenerInterface) {
            listener = (FragmentListenerInterface) context;
            Log.d(TAG, "listener attached");
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListenerInterface");
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick()");
        listener.onFragmentButtonPushed(v);
    }
}
