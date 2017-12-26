package com.trannguyentanthuan2903.yourfood.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfood.R;
import com.trannguyentanthuan2903.yourfood.Utils.FirebaseMethods;
import com.trannguyentanthuan2903.yourfood.models.User;

/**
 * Created by Administrator on 9/20/2017.
 */

public class RegisterActivity extends AppCompatActivity {
    private static String TAG = "RegisterActivity";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods fireBaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    private Context mContext;
    private String email, username, password;
    private EditText mEmail, mUsername, mPassword;
    private TextView loadingPleaseWait;
    private Button btnRegister;
    private ProgressBar mProgressBar;

    private String append = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;
        fireBaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG, "onCreate: started");

        initWidget();
        setupFirebaseAuth();
        init();
    }

    private void init() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                username = mUsername.getText().toString().trim();

                if (checkInputs(email, username, password))
                    mProgressBar.setVisibility(View.VISIBLE);
                loadingPleaseWait.setVisibility(View.VISIBLE);

                fireBaseMethods.registerNewEmail(email, password, username);
            }
        });
    }

    private boolean checkInputs(String email, String username, String password) {
        Log.d(TAG, "checkInputs: check input register");
        if (email.equals("") || username.equals("") || password.equals("")) {
            Toast.makeText(mContext, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*
    *  Initialize the activity widgets
    * */
    private void initWidget() {
        Log.d(TAG, "initWidget: started");
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mUsername = (EditText) findViewById(R.id.input_username);
        loadingPleaseWait = (TextView) findViewById(R.id.loadingPleaseWait);
        btnRegister = (Button) findViewById(R.id.btn_register);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mProgressBar.setVisibility(View.GONE);
        loadingPleaseWait.setVisibility(View.GONE);
    }

    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: check null ");
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /*-----------------------Firebase----------------------------*/
    private void checkIfUsernameExist(final String username) {
        Log.d(TAG, "checkIfUsernameExist: checking if " + username + "already exists");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapShot : dataSnapshot.getChildren()) {
                    if (singleSnapShot.exists()) {
                        Log.d(TAG, "onDataChange: FOUND MATCH: " + singleSnapShot.getValue(User.class).getUsername());
                        append = mRef.push().getKey().substring(3, 10);
                        Log.d(TAG, "onDataChange: username already exists. Appending Random String to name" + append);
                    }
                }
                String mUserName = "";
                mUserName = username + append;

                fireBaseMethods.addNewUser(email, mUserName, "", "", "");

                Toast.makeText(mContext, getString(R.string.cofirm_sign_up), Toast.LENGTH_SHORT).show();

                mAuth.signOut();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
   * Set up Firebase Auth
   * */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: start firebase auth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkIfUsernameExist(username);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
