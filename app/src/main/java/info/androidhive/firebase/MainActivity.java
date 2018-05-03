package info.androidhive.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove, signOut, nextAct, webViewAct, viewListings,cancelEmailVerification;

    private EditText oldEmail, newEmail, password, newPassword;
    private TextView verifyEmailMessage;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    String email, userType;
    int removeAttempts=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };


        btnChangeEmail = (Button) findViewById(R.id.change_email_button);
        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnSendResetEmail = (Button) findViewById(R.id.sending_pass_reset_button);
        btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        nextAct= (Button) findViewById(R.id.nextAct);
        changeEmail = (Button) findViewById(R.id.changeEmail);
        changePassword = (Button) findViewById(R.id.changePass);
        sendEmail = (Button) findViewById(R.id.send);
        cancelEmailVerification = (Button) findViewById(R.id.cancel);
        remove = (Button) findViewById(R.id.remove);
        signOut = (Button) findViewById(R.id.sign_out);
        webViewAct = (Button) findViewById(R.id.webViewAct);
        viewListings= (Button) findViewById(R.id.viewListings);

        oldEmail = (EditText) findViewById(R.id.old_email);
        newEmail = (EditText) findViewById(R.id.new_email);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);

        verifyEmailMessage = (TextView) findViewById(R.id.verifyEmailMessage);

        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        cancelEmailVerification.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);
        verifyEmailMessage.setVisibility(View.GONE);
        viewListings.setVisibility(View.GONE);

        ////////////////////////////////////////////////
        btnChangeEmail.setVisibility(View.GONE);
        btnChangePassword.setVisibility(View.GONE);
        //////////////////////////////////////////////////
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        Button verifyEmail= (Button) findViewById(R.id.verifyEmail);

        if (user.isEmailVerified()==false) {
            //Toast.makeText(MainActivity.this, "Unverified info.androidhive.firebase.User", Toast.LENGTH_SHORT).show();

           // when a new user signs up.. a verification email must be sent to them and they should be logged out.
            // if they login without verifying...

            verifyEmailMessage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("VerificationEmail", "Email sent.");
                                Toast.makeText(MainActivity.this, "Verification Email Sent. Check your email!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                //signOut();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Failed send verification email!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

            btnSendResetEmail.setVisibility(View.GONE);
            nextAct.setVisibility(View.GONE);
        }
        else
        {
            verifyEmail.setVisibility(View.GONE);
            btnSendResetEmail.setVisibility(View.VISIBLE);
            nextAct.setVisibility(View.VISIBLE);
            viewListings.setVisibility(View.VISIBLE);
        }



        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isEmailVerified()==false)
                {
                    verifyEmailMessage.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("VerificationEmail", "Email sent.");
                                        Toast.makeText(MainActivity.this, "Verification Email Sent. Check your email!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Failed send verification email!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Your Email is Verified", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

        userType= BuildProfile.userType;

        viewListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listings = new Intent(getApplicationContext(),Listings.class).putExtra("UserType",userType);
                startActivity(listings);

            }
        });


        webViewAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),webViewAct);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), ""+item.getTitle(), Toast.LENGTH_SHORT).show();

                        if(item.getTitle().equals("Arbor Oaks"))
                        {
                            Intent apartmentInfo = new Intent(getApplicationContext(),ApartmentInfo.class).putExtra("URL","https://www.uta.edu/housing/housing/apartments/arbor-oaks.php");
                            startActivity(apartmentInfo);
                        }

                        else if(item.getTitle().equals("Meadow Run"))
                        {
                            Intent apartmentInfo = new Intent(getApplicationContext(),ApartmentInfo.class).putExtra("URL","https://www.uta.edu/housing/housing/apartments/meadow-run.php");
                            startActivity(apartmentInfo);
                        }
                        else if(item.getTitle().equals("University Village"))
                        {
                            Intent apartmentInfo = new Intent(getApplicationContext(),ApartmentInfo.class).putExtra("URL","https://www.uta.edu/housing/housing/apartments/university-village.php");
                            startActivity(apartmentInfo);
                        }
                        else if(item.getTitle().equals("Center Point"))
                        {
                            Intent apartmentInfo = new Intent(getApplicationContext(),ApartmentInfo.class).putExtra("URL","https://www.uta.edu/housing/housing/apartments/center-point.php");
                            startActivity(apartmentInfo);
                        }
                        else if(item.getTitle().equals("Garden Club"))
                        {
                            Intent apartmentInfo = new Intent(getApplicationContext(),ApartmentInfo.class).putExtra("URL","https://www.uta.edu/housing/housing/apartments/garden-club.php");
                            startActivity(apartmentInfo);
                        }

                        return true;
                    }
                });

                popupMenu.show();

                //Intent apartmentInfo = new Intent(getApplicationContext(),ApartmentInfo.class);
                //startActivity(apartmentInfo);
            }
        });

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        signOut();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.VISIBLE);
                cancelEmailVerification.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
                btnSendResetEmail.setVisibility(View.GONE);

                viewListings.setVisibility(View.GONE); // listing
                webViewAct.setVisibility(View.GONE);  // apartments
                nextAct.setVisibility(View.GONE);



                if (user != null) {
                    email = user.getEmail();
                }
                Log.i("CheckEmail",email);
            }
        });

        cancelEmailVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                cancelEmailVerification.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                viewListings.setVisibility(View.GONE); // listing
                webViewAct.setVisibility(View.VISIBLE);

                if(user.isEmailVerified()==true) {
                    // if email is verified show all this.. otherwise take away edit profile
                    btnSendResetEmail.setVisibility(View.VISIBLE);
                    nextAct.setVisibility(View.VISIBLE);
                    viewListings.setVisibility(View.VISIBLE);

                }
                else
                {
                    viewListings.setVisibility(View.GONE);
                    btnSendResetEmail.setVisibility(View.GONE);
                    nextAct.setVisibility(View.GONE);
                }

            }
        });


        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);
                if (oldEmail.getText().toString().trim().equals(email)) {
                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        auth.signOut();


                                    } else {
                                        Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    oldEmail.setError("Enter correct email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                if (user != null) {

                    // show an alert sign
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("Do you want to Permanently delete your account?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                                    finish();
                                                    //progressBar.setVisibility(View.GONE);
                                                } else {
                                                    Toast.makeText(MainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                                    //progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }
                                });
                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        //progressBar.setVisibility(View.GONE);
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();



                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Are you sure you want to Sign Out?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                signOut();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                //progressBar.setVisibility(View.GONE);
                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        nextAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), BuildProfile.class);
                startActivity(intent);
            }
        });

    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}