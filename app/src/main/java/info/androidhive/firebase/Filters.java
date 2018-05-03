package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class Filters extends AppCompatActivity {

    private RadioGroup user_type, Question0_group, Question1_group, Question2_group, Question3_group, Question4_group;
    private String[] filters={"-99","-99","-99","-99","-99"};

    private Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        Question0_group= (RadioGroup) findViewById(R.id.Question0_group); // gender
        Question1_group= (RadioGroup) findViewById(R.id.Question1_group); //major
        Question2_group = (RadioGroup) findViewById(R.id.Question2_group); //sleep
        Question3_group = (RadioGroup) findViewById(R.id.Question3_group); //cleaning
        Question4_group = (RadioGroup) findViewById(R.id.Question4_group); //guest

        apply= (Button) findViewById(R.id.apply);

        Question0_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {    // data 2

                if (checkedId== R.id.male)
                    filters[0]= "Male";

                else  if (checkedId== R.id.female)
                    filters[0]="Female";

                else if (checkedId== R.id.otherGender)
                    filters[0]="Other";
            }
        });


        Question1_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {       // data 4

                if (checkedId== R.id.stem)
                    filters[1]= "STEM";

                else  if (checkedId== R.id.arts)
                    filters[1]="Arts";

                else if (checkedId== R.id.business)
                    filters[1]="Business";

                else  if (checkedId== R.id.others)
                    filters[1]="Others";
            }
        });

        Question2_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {        // data 5

                if (checkedId== R.id.early_bird)
                    filters[2]= "Early Bird";

                else  if (checkedId== R.id.night_owl)
                    filters[2]= "Night Owl";

            }
        });

        Question3_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {        // data 6

                if (checkedId== R.id.rarely)
                    filters[3]= "Rarely";

                else  if (checkedId== R.id.onceAWeek)
                    filters[3]="Once A Week";

                else if (checkedId== R.id.once2Weeks)
                    filters[3]="Once every 2 weeks";

                else  if (checkedId== R.id.onceAMonth)
                    filters[3]="Once A Month";
            }
        });

        Question4_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {     // data 7

                if (checkedId== R.id.RarelyGuests)
                    filters[4]= "Rarely";

                else  if (checkedId== R.id.onceAWhile)
                    filters[4]="Once or Twice a Week";

                else if (checkedId== R.id.SeveralTimesWeek)
                    filters[4]="Several Times A Week";

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getApplicationContext(),Listings.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("Gender", filters[0]);
                mBundle.putString("Major", filters[1]);
                mBundle.putString("Cleaning", filters[2]);
                mBundle.putString("Sleeping", filters[3]);
                mBundle.putString("Guest", filters[4]);

                Filters.this.startActivity(new Intent(Filters.this, Listings.class).putExtras(mBundle));
            }
        });






    }
}
