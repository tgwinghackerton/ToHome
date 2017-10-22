package b05studio.com.seeyouagain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import b05studio.com.seeyouagain.model.User;

/**
 * Created by mansu on 2017-07-05.
 */

public class LoginActivity extends DialogActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");

    private GoogleApiClient mGoogleApiClient;

    private BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("LOGIN_SUCCESS")) {
                moveToMissingPersonListActivity();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.sign_in_button).setOnClickListener(LoginActivity.this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        // [END initialize_auth]
        if(user != null) {
            loginUser(user);
            Log.d(TAG, "이미 인증정보가 있음!!");
        } else {
            Log.d(TAG, "현재 인증정보가 없음");
        }
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mAuth = firebaseAuth;
                if (mAuth.getCurrentUser() != null) { // 인증정보가 없는 상태에서 로그인한 경우
                    Log.d(TAG, mAuth.getCurrentUser().getUid());
                    Log.d(TAG, mAuth.getCurrentUser().getEmail());
                    loginUser(mAuth.getCurrentUser());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        IntentFilter loginFilter = new IntentFilter();
        loginFilter.addAction("LOGIN_SUCCESS");
        registerReceiver(loginReceiver,loginFilter);
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        unregisterReceiver(loginReceiver);
    }
    // [END on_stop_remove_listener]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {

                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                //  updateUI(null);// [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //    updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public void loginUser(final FirebaseUser firebaseUser) {
        //Log.i("user information", uid);
        User user = User.getUserInstance();
        user.setName(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());
        //User currentUser = new User(name, email, photoUrl);
        uploadUserDataToServer(user, firebaseUser.getUid(), getApplicationContext());
    }

    private void uploadUserDataToServer(final User user, final String uid, final Context googleSignInContext) {
        myRef.child(uid).addListenerForSingleValueEvent(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User getUserFromDB = dataSnapshot.getValue(User.class);
                        if (getUserFromDB == null) {
                            if(user.getToken() != null)
                                user.setToken(user.getToken());
                            else
                                user.setToken(FirebaseInstanceId.getInstance().getToken());
                            myRef.child(uid).setValue(user);
                            Log.i("신규 유저 정보", user.toString());

                        } else { // 존재할경우 -> 불러와야함
                            String token = FirebaseInstanceId.getInstance().getToken();
                            user.setToken(token);
                            user.setUserLikeList(getUserFromDB.getUserLikeList());
                            user.setAlarmInfos(getUserFromDB.getAlarmInfos());
                            user.setPoint(getUserFromDB.getPoint());
                            myRef.child(uid).setValue(user);
                            Log.i("기존 유저정보", user.toString());
                        }

                        final Intent loginReceiver = new Intent();
                        loginReceiver.setAction("LOGIN_SUCCESS");
                        googleSignInContext.sendBroadcast(loginReceiver);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }

    }

    private void moveToMissingPersonListActivity() {
        Intent intent = new Intent(LoginActivity.this, MissingPersonListActivity.class);
        if(getIntent().getExtras() != null)
            intent.putExtras(getIntent().getExtras());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}