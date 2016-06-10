package ca.toadlybroodle.alternaterealitygames;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class BaseSignInActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "BaseSignInActivity";

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_signin);


        findViewById(R.id.base_google_sign_in_button).setOnClickListener(this);
        findViewById(R.id.base_email_sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_google_sign_in_button:
                // Log.d(TAG, "Sign in button clicked");
                Intent _GoogleSignInActivity = new Intent(getApplicationContext(), GoogleSignInActivity.class);
                startActivity(_GoogleSignInActivity);
                break;
            case R.id.base_email_sign_in_button:
                // Log.d(TAG, "Sign in button clicked");
                Intent _EmailPasswordActivity = new Intent(getApplicationContext(), EmailPasswordActivity.class);
                startActivity(_EmailPasswordActivity);
                break;
        }

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }

}
