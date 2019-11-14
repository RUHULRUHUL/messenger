package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Gmailaccountactivity extends AppCompatActivity {
    private SignInButton gmaillogging;
    private GoogleSignInClient mGoogleSignInClient;

    private int RC_SIGN_IN=1;
    private FirebaseAuth mauth;
    private DatabaseReference rotref;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmailaccountactivity);

        progressDialog=new ProgressDialog(Gmailaccountactivity.this);

        mauth=FirebaseAuth.getInstance();
        rotref= FirebaseDatabase.getInstance().getReference().child("persionacount");

        gmaillogging=findViewById(R.id.gmailbuttonid);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        gmaillogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        progressDialog.setTitle("gmail account logging");
        progressDialog.setMessage("please wait some time logging...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
               // String email=account.getEmail().toString();
                //progressDialog.setMessage(""+email);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e);
                progressDialog.dismiss();

                Toast.makeText(this, "error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();

                            Intent intent=new Intent(Gmailaccountactivity.this,MainActivity.class);
                            startActivity(intent);


                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                           // FirebaseUser user = auth.getCurrentUser();
                            //                            String name=user.getDisplayName();
//                            String email=user.getEmail();
//
//                            String photourl=user.getPhotoUrl().toString();
//
//                            HashMap<String,Object>gmailinfo=new HashMap<>();
//                            gmailinfo.put("username",name);
//                            gmailinfo.put("imageurl",photourl);
//
//
//                            String uid=auth.getCurrentUser().getUid();
//
//                            rotref.child(uid).setValue(gmailinfo).addOnCompleteListener(new OnCompleteListener<Void>()
//                            {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful())
//                                    {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(Gmailaccountactivity.this, "successfully logging", Toast.LENGTH_SHORT).show();
//                                        Intent intent=new Intent(Gmailaccountactivity.this,MainActivity.class);
//                                        startActivity(intent);
//                                    }
//                                    else {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(Gmailaccountactivity.this, "error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });


                           // updateUI(user);
                        } else {

                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
