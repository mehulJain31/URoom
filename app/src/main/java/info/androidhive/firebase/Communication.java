package info.androidhive.firebase;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Communication extends AppCompatActivity {

    private Button messagingButton;
    private String phone;
    private TextView sex, major, lifestyle, clean, guest, bio,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        messagingButton= (Button)findViewById(R.id.button_text);

        name= (TextView)findViewById(R.id.name);
        sex = (TextView) findViewById(R.id.sex);
        major = (TextView) findViewById(R.id.major);
        lifestyle = (TextView) findViewById(R.id.lifestyle);
        clean = (TextView) findViewById(R.id.clean);
        guest = (TextView) findViewById(R.id.guest);
        bio = (TextView) findViewById(R.id.bio);

        name.setText(getIntent().getExtras().getString("Name"));
        //Toast.makeText(this,getIntent().getExtras().getString("Name"),Toast.LENGTH_SHORT).show();
        sex.setText(getIntent().getExtras().getString("Gender"));
        major.setText(getIntent().getExtras().getString("Major")+" Major");
        lifestyle.setText(getIntent().getExtras().getString("Lifestyle"));
        clean.setText("I Clean: "+getIntent().getExtras().getString("Cleaning"));
        guest.setText("Guests' Frequency: "+getIntent().getExtras().getString("Guest"));
        bio.setText(getIntent().getExtras().getString("Bio"));

        phone = getIntent().getExtras().getString("Phone");

        messagingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Communication.this,"Redirecting to Messaging...",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", "Hey!\n I saw your listing on URoom\n");

                try {
                    startActivity(intent);
                    Log.i("Finished sending SMS...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Communication.this,"SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
