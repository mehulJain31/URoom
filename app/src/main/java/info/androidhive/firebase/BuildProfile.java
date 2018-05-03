package info.androidhive.firebase;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class BuildProfile extends AppCompatActivity {

    public static String userType="";

    private  RadioGroup user_type, Question0_group, Question1_group, Question2_group, Question3_group, Question4_group;
    private String[] answers={"-99","-99","-99","-99","-99","-99","-99","-99","-99","-99"};
    private MultiAutoCompleteTextView bio;
    private TextView name, dateofBirth,phoneNumber;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.75F);
    private Button seeAnswers;

    private int day=0, month=0, year=0;
    private boolean allFieldsComplete= true;
    private TextView dateOfBirth;

    private FirebaseAuth auth1= FirebaseAuth.getInstance();
    Firebase reference= new Firebase("https://testingfirebase2-baa5c.firebaseio.com/");
    //auth1 = FirebaseAuth.getInstance();
    Firebase databaseReference = reference.child("data").child("Users").child(auth1.getCurrentUser().getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_profile);


        name= (TextView) findViewById(R.id.name) ; // data 0 done
        dateofBirth= (TextView) findViewById(R.id.dateOfBirth) ; // data 1 done
        phoneNumber= (TextView) findViewById(R.id.phoneNumber) ; //data 9

        user_type= (RadioGroup) findViewById(R.id.radioGroup);
        Question0_group= (RadioGroup) findViewById(R.id.Question0_group);
        Question1_group= (RadioGroup) findViewById(R.id.Question1_group);
        Question2_group = (RadioGroup) findViewById(R.id.Question2_group);
        Question3_group = (RadioGroup) findViewById(R.id.Question3_group);
        Question4_group = (RadioGroup) findViewById(R.id.Question4_group);

        bio = (MultiAutoCompleteTextView) findViewById(R.id.bio);

        seeAnswers = (Button) findViewById(R.id.seeAnswers);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("data");
        mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    User currentUser= dataSnapshot.getValue(User.class);

                    name.setText(currentUser.getName());
                    dateofBirth.setText(currentUser.getDate_of_birth());
                    phoneNumber.setText(currentUser.getPhone_number());

                    bio.setText(currentUser.getBio());

                    if(currentUser.getGender()!=null && currentUser.getGender().equals("Male"))
                    {
                        RadioButton male= (RadioButton) findViewById(R.id.male);
                        male.setChecked(true);

                    }
                    else if(currentUser.getGender()!=null && currentUser.getGender().equals("Female"))
                    {
                        RadioButton female= (RadioButton) findViewById(R.id.female);
                        female.setChecked(true);
                    }
                    else if(currentUser.getGender()!=null && currentUser.getGender().equals("Other"))
                    {
                        RadioButton other= (RadioButton) findViewById(R.id.otherGender);
                        other.setChecked(true);
                    }

                    if(currentUser.getUser_type()!=null && currentUser.getUser_type().equals("Looking for Apartment"))
                    {
                        RadioButton roommate= (RadioButton) findViewById(R.id.apartment);
                        roommate.setChecked(true);
                    }

                    /*
                    if(currentUser.getUser_type().equals("Looking for Roommate"))
                    {
                        RadioButton male= (RadioButton) findViewById(R.id.apartment);
                        male.setChecked(true);
                    }
                    else if(!currentUser.getUser_type().equals(""))
                    {
                        RadioButton male= (RadioButton) findViewById(R.id.roommate);
                        male.setChecked(true);
                    }
                    */
                    //Toast.makeText(Listings.this,"User name: "+currentUser.getName()+" "+currentUser.getUser_type(),Toast.LENGTH_SHORT).show();
                    //userType_listing=currentUser.getUser_type().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //userType_listing="";
                Toast.makeText(BuildProfile.this,"Firebase Error Occured",Toast.LENGTH_SHORT).show();
            }
        });




        //seeAnswers.setClickable(false);
        //seeAnswers.setAlpha(0.7f);

        Question0_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {    // data 2

                if (checkedId== R.id.male)
                    answers[2]= "Male";

                else  if (checkedId== R.id.female)
                    answers[2]="Female";

                else if (checkedId== R.id.otherGender)
                    answers[2]="Other";
            }
        });


        user_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {     // data 3
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId== R.id.roommate)
                {
                    Toast.makeText(getApplicationContext(), "Apartment", Toast.LENGTH_SHORT).show();
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(),user_type);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(getApplicationContext(), ""+item.getTitle(), Toast.LENGTH_SHORT).show();

                            RadioButton apartment = (RadioButton) findViewById(R.id.roommate);
                            apartment.setText("Looking for Roommate for: "+item.getTitle());

                            if(item.getTitle().equals("Arbor Oaks"))
                                answers[3]="Arbor Oaks";
                            else if(item.getTitle().equals("Meadow Run"))
                                answers[3]="Meadow Run";
                            else if(item.getTitle().equals("University Village"))
                                answers[3]="University Village";
                            else if(item.getTitle().equals("Center Point"))
                                answers[3]="Center Point";
                            else if(item.getTitle().equals("Garden Club"))
                                answers[3]="Garden Club";

                            return true;
                        }
                    });

                    popupMenu.show();
                }
                else if (checkedId== R.id.apartment)
                {
                    answers[3]="Looking for Apartment";
                }

            }
        });


        Question1_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {       // data 4

                if (checkedId== R.id.stem)
                    answers[4]= "STEM";

                else  if (checkedId== R.id.arts)
                    answers[4]="Arts";

                else if (checkedId== R.id.business)
                    answers[4]="Business";

                else  if (checkedId== R.id.others)
                    answers[4]="Others";
            }
        });

        Question2_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {        // data 5

                if (checkedId== R.id.early_bird)
                    answers[5]= "Early Bird";

                else  if (checkedId== R.id.night_owl)
                    answers[5]= "Night Owl";

            }
        });

        Question3_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {        // data 6

                if (checkedId== R.id.rarely)
                    answers[6]= "Rarely";

                else  if (checkedId== R.id.onceAWeek)
                    answers[6]="Once A Week";

                else if (checkedId== R.id.once2Weeks)
                    answers[6]="Once every 2 weeks";

                else  if (checkedId== R.id.onceAMonth)
                    answers[6]="Once A Month";
            }
        });

        Question4_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {     // data 7

                if (checkedId== R.id.RarelyGuests)
                    answers[7]= "Rarely";

                else  if (checkedId== R.id.onceAWhile)
                    answers[7]="Once or Twice a Week";

                else if (checkedId== R.id.SeveralTimesWeek)
                    answers[7]="Several Times A Week";

            }
        });


        seeAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allFieldsComplete=true;
                answers[8] = bio.getText().toString();
                answers[9] = phoneNumber.getText().toString();

                answers[0] = name.getText().toString();  // name done

                answers[1] = "" + day + "/" + (month + 1) + "/" + year; // date of birth done


                for(int i=0;i<=9;i++)
                {
                    Log.i("answer",answers[i]);
                    if(answers[i].equals("-99") || answers[i].equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill all sections",Toast.LENGTH_SHORT).show();
                        allFieldsComplete= false;
                        break;
                    }
                    else
                        continue;
                }


                if(allFieldsComplete== true)
                {
                    Toast.makeText(getApplicationContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
                    User user = new User(answers[0], answers[1], answers[2], answers[3], answers[4], answers[5], answers[6], answers[7], answers[8], answers[9]);

                    // name, date_of_birth, gender, user_type, major, sleep_Preferences,cleaning_Frequency, guests, bio, phone_Number

                    databaseReference.child("name").setValue(user.getName());
                    databaseReference.child("date_of_birth").setValue(user.getDate_of_birth());
                    databaseReference.child("gender").setValue(user.getGender());
                    databaseReference.child("user_type").setValue(user.getUser_type());
                    databaseReference.child("major").setValue(user.getMajor());
                    databaseReference.child("sleep_Preferences").setValue(user.getSleep_Preferences());
                    databaseReference.child("cleaning_Frequency").setValue(user.getCleaning_Frequency());
                    databaseReference.child("guests").setValue(user.getGuests());
                    databaseReference.child("bio").setValue(user.getBio());
                    databaseReference.child("phone_number").setValue(user.getPhone_number());

                    // return to main activity
                    userType=user.getUser_type();
                    Intent mainActivityIntent = new Intent(BuildProfile.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                }

            }
        });


        dateOfBirth =(TextView) findViewById(R.id.dateOfBirth);

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

    }


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            dateOfBirth.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };
}
