package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Listings extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

   //private Firebase reference= new Firebase("https://testingfirebase2-baa5c.firebaseio.com/data/Users");

    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("data").child("Users");
    Query q= usersRef.orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

    private ArrayList<String> titles=new ArrayList<String>();  //user type
    private ArrayList<String> details=new ArrayList<String>();  //major
    private ArrayList<String> images=new ArrayList<String>();   //name
    private ArrayList<String> phoneNumbers= new ArrayList<String>(); //phone number
    private ArrayList<String> gender=new ArrayList<String>();   //gender
    private ArrayList<String> lifestyle= new ArrayList<String>(); // sleep
    private ArrayList<String> cleaning= new ArrayList<String>(); //cleaning
    private ArrayList<String> guest= new ArrayList<String>(); // guest
    private ArrayList<String> bio= new ArrayList<String>(); //bio

    //what i need:name8, gender, major8, lifestyle, cleaning,guest, bio

    private String filter_gender="", filter_major="", filter_cleaning="", filter_sleeping="",filter_guest="";

    String userType_listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        filter_major= getIntent().getExtras().getString("Major");
        filter_cleaning= getIntent().getExtras().getString("Cleaning");
        filter_gender= getIntent().getExtras().getString("Gender");
        filter_sleeping= getIntent().getExtras().getString("Sleeping");
        filter_guest= getIntent().getExtras().getString("Guest");



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("data");
        mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                       User currentUser= dataSnapshot.getValue(User.class);
                    //Toast.makeText(Listings.this,"User name: "+currentUser.getName()+" "+currentUser.getUser_type(),Toast.LENGTH_SHORT).show();
                    if (userType_listing!=null)userType_listing=currentUser.getUser_type().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                userType_listing="";
                Toast.makeText(Listings.this,"Firebase Error Occured",Toast.LENGTH_SHORT).show();
            }
        });



        usersRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
               @Override
               public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                   User downloadingUser =  dataSnapshot.getValue(User.class);

                   if (userType_listing!=null && userType_listing.equals("Looking for Apartment")) {

                            if (!downloadingUser.getUser_type().equals("Looking for Apartment")) {


                                titles.add(downloadingUser.getUser_type());
                                Log.i("DownLoad", "" + downloadingUser.getUser_type());

                                details.add(downloadingUser.getMajor());
                                Log.i("DownLoad", "" + downloadingUser.getMajor());

                                images.add(downloadingUser.getName());
                                Log.i("DownLoad", "" + downloadingUser.getName());

                                phoneNumbers.add(downloadingUser.getPhone_number());
                                Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                                ////////////////////////////////////////////////////////////////

                                gender.add(downloadingUser.getGender());
                                lifestyle.add(downloadingUser.getSleep_Preferences());
                                cleaning.add(downloadingUser.getCleaning_Frequency());
                                guest.add(downloadingUser.getGuests());
                                bio.add(downloadingUser.getBio());


                                //userIDs.add(downloadingUser.getUserId());
                                //Log.i("DownLoad", "" + downloadingUser.getUserId());

                            }
                        } else if (userType_listing!=null && !userType_listing.equals("Looking for Apartment") && !userType_listing.equals("")) {
                            if (downloadingUser.getUser_type().equals("Looking for Apartment")) {

                                titles.add(downloadingUser.getUser_type());
                                Log.i("DownLoad", "" + downloadingUser.getUser_type());

                                details.add(downloadingUser.getMajor());
                                Log.i("DownLoad", "" + downloadingUser.getMajor());

                                images.add(downloadingUser.getName());
                                Log.i("DownLoad", "" + downloadingUser.getName());

                                phoneNumbers.add(downloadingUser.getPhone_number());
                                Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                                //userIDs.add(downloadingUser.getUserId());
                                //Log.i("DownLoad", "" + downloadingUser.getUserId());
                                gender.add(downloadingUser.getGender());
                                lifestyle.add(downloadingUser.getSleep_Preferences());
                                cleaning.add(downloadingUser.getCleaning_Frequency());
                                guest.add(downloadingUser.getGuests());
                                bio.add(downloadingUser.getBio());
                            }
                        } else {
                            titles.add(downloadingUser.getUser_type());
                            Log.i("DownLoad", "" + downloadingUser.getUser_type());

                            details.add(downloadingUser.getMajor());
                            Log.i("DownLoad", "" + downloadingUser.getMajor());

                            images.add(downloadingUser.getName());
                            Log.i("DownLoad", "" + downloadingUser.getName());

                            phoneNumbers.add(downloadingUser.getPhone_number());
                            Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                            //userIDs.add(downloadingUser.getUserId());
                            //Log.i("DownLoad", "" + downloadingUser.getUserId());
                            gender.add(downloadingUser.getGender());
                            lifestyle.add(downloadingUser.getSleep_Preferences());
                            cleaning.add(downloadingUser.getCleaning_Frequency());
                            guest.add(downloadingUser.getGuests());
                            bio.add(downloadingUser.getBio());

                        }

                   adapter.notifyDataSetChanged();
               }

               @Override
               public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s)
                   {

                       User downloadingUser = dataSnapshot.getValue(User.class);
                        /*if ((downloadingUser.getUser_type().equals("Looking for Apartment") && ( filter_major.equals(downloadingUser.getMajor()) && filter_cleaning.equals(downloadingUser.getCleaning_Frequency()) && filter_gender.equals(downloadingUser.getGender()) && filter_guest.equals(downloadingUser.getGuests()) && filter_sleeping.equals(downloadingUser.getSleep_Preferences())) )
                                )
                        {*/

                       if (userType_listing!=null && userType_listing.equals("Looking for Apartment")) {

                           if (!downloadingUser.getUser_type().equals("Looking for Apartment")) {
                               titles.add(downloadingUser.getUser_type());
                               Log.i("DownLoad", "" + downloadingUser.getUser_type());

                               details.add(downloadingUser.getMajor());
                               Log.i("DownLoad", "" + downloadingUser.getMajor());

                               images.add(downloadingUser.getName());
                               Log.i("DownLoad", "" + downloadingUser.getName());

                               phoneNumbers.add(downloadingUser.getPhone_number());
                               Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                               //userIDs.add(downloadingUser.getUserId());
                               //Log.i("DownLoad", "" + downloadingUser.getUserId());
                               gender.add(downloadingUser.getGender());
                               lifestyle.add(downloadingUser.getSleep_Preferences());
                               cleaning.add(downloadingUser.getCleaning_Frequency());
                               guest.add(downloadingUser.getGuests());
                               bio.add(downloadingUser.getBio());

                           }
                       } else if (userType_listing!=null && !userType_listing.equals("Looking for Apartment") && !userType_listing.equals("")) {
                           if (downloadingUser.getUser_type().equals("Looking for Apartment")) {
                               titles.add(downloadingUser.getUser_type());
                               Log.i("DownLoad", "" + downloadingUser.getUser_type());

                               details.add(downloadingUser.getMajor());
                               Log.i("DownLoad", "" + downloadingUser.getMajor());

                               images.add(downloadingUser.getName());
                               Log.i("DownLoad", "" + downloadingUser.getName());

                               phoneNumbers.add(downloadingUser.getPhone_number());
                               Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                               //userIDs.add(downloadingUser.getUserId());
                               //Log.i("DownLoad", "" + downloadingUser.getUserId());
                               gender.add(downloadingUser.getGender());
                               lifestyle.add(downloadingUser.getSleep_Preferences());
                               cleaning.add(downloadingUser.getCleaning_Frequency());
                               guest.add(downloadingUser.getGuests());
                               bio.add(downloadingUser.getBio());
                           }
                       } else {
                           titles.add(downloadingUser.getUser_type());
                           Log.i("DownLoad", "" + downloadingUser.getUser_type());

                           details.add(downloadingUser.getMajor());
                           Log.i("DownLoad", "" + downloadingUser.getMajor());

                           images.add(downloadingUser.getName());
                           Log.i("DownLoad", "" + downloadingUser.getName());

                           phoneNumbers.add(downloadingUser.getPhone_number());
                           Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                           //userIDs.add(downloadingUser.getUserId());
                           //Log.i("DownLoad", "" + downloadingUser.getUserId());
                           gender.add(downloadingUser.getGender());
                           lifestyle.add(downloadingUser.getSleep_Preferences());
                           cleaning.add(downloadingUser.getCleaning_Frequency());
                           guest.add(downloadingUser.getGuests());
                           bio.add(downloadingUser.getBio());

                       }
                   //}
                   adapter.notifyDataSetChanged();

               }

               @Override
               public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

               }

               @Override
               public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                   User downloadingUser =  dataSnapshot.getValue(User.class);

                   /*if((downloadingUser.getUser_type().equals("Looking for Apartment") && ( filter_major.equals(downloadingUser.getMajor()) && filter_cleaning.equals(downloadingUser.getCleaning_Frequency()) && filter_gender.equals(downloadingUser.getGender()) && filter_guest.equals(downloadingUser.getGuests()) && filter_sleeping.equals(downloadingUser.getSleep_Preferences())) )
                           ) {*/
                       if (userType_listing!=null && userType_listing.equals("Looking for Apartment")) {

                           if (!downloadingUser.getUser_type().equals("Looking for Apartment")) {
                               titles.add(downloadingUser.getUser_type());
                               Log.i("DownLoad", "" + downloadingUser.getUser_type());

                               details.add(downloadingUser.getMajor());
                               Log.i("DownLoad", "" + downloadingUser.getMajor());

                               images.add(downloadingUser.getName());
                               Log.i("DownLoad", "" + downloadingUser.getName());

                               phoneNumbers.add(downloadingUser.getPhone_number());
                               Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                               //userIDs.add(downloadingUser.getUserId());
                               //Log.i("DownLoad", "" + downloadingUser.getUserId());
                               gender.add(downloadingUser.getGender());
                               lifestyle.add(downloadingUser.getSleep_Preferences());
                               cleaning.add(downloadingUser.getCleaning_Frequency());
                               guest.add(downloadingUser.getGuests());
                               bio.add(downloadingUser.getBio());

                           }
                       } else if (userType_listing!=null &&!userType_listing.equals("Looking for Apartment") && !userType_listing.equals("")) {
                           if (downloadingUser.getUser_type().equals("Looking for Apartment")) {
                               titles.add(downloadingUser.getUser_type());
                               Log.i("DownLoad", "" + downloadingUser.getUser_type());

                               details.add(downloadingUser.getMajor());
                               Log.i("DownLoad", "" + downloadingUser.getMajor());

                               images.add(downloadingUser.getName());
                               Log.i("DownLoad", "" + downloadingUser.getName());

                               phoneNumbers.add(downloadingUser.getPhone_number());
                               Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                               //userIDs.add(downloadingUser.getUserId());
                               //Log.i("DownLoad", "" + downloadingUser.getUserId());
                               gender.add(downloadingUser.getGender());
                               lifestyle.add(downloadingUser.getSleep_Preferences());
                               cleaning.add(downloadingUser.getCleaning_Frequency());
                               guest.add(downloadingUser.getGuests());
                               bio.add(downloadingUser.getBio());
                           }
                       } else {
                           titles.add(downloadingUser.getUser_type());
                           Log.i("DownLoad", "" + downloadingUser.getUser_type());

                           details.add(downloadingUser.getMajor());
                           Log.i("DownLoad", "" + downloadingUser.getMajor());

                           images.add(downloadingUser.getName());
                           Log.i("DownLoad", "" + downloadingUser.getName());

                           phoneNumbers.add(downloadingUser.getPhone_number());
                           Log.i("DownLoad", "" + downloadingUser.getPhone_number());

                           //userIDs.add(downloadingUser.getUserId());
                           //Log.i("DownLoad", "" + downloadingUser.getUserId());
                           gender.add(downloadingUser.getGender());
                           lifestyle.add(downloadingUser.getSleep_Preferences());
                           cleaning.add(downloadingUser.getCleaning_Frequency());
                           guest.add(downloadingUser.getGuests());
                           bio.add(downloadingUser.getBio());

                       }
                   //}
                   adapter.notifyDataSetChanged();

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });


        adapter = new RecyclerAdapter(titles, details, images, phoneNumbers, gender, lifestyle, cleaning, guest, bio, Listings.this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast.makeText(this,"Settings option",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(getApplicationContext(),Filters.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Created by Divyanshu Sharma on 11/29/2017.
     */

}
