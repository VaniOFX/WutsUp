package hk.ust.cse.comp4521.watsup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    protected void onStart(){
        super.onStart();
        DataBaseCommunicator.setUpDataBase();
        DataBaseCommunicator.setEnrolled(FirebaseAuth.getInstance().getUid());


        TextView name = (TextView) findViewById(R.id.textView2);
        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Button enrolledEvents = (Button) findViewById(R.id.enrolledevents);
        enrolledEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EventListActivity.class);
                i.putExtra(EventListActivity.CALLING_ACTIVITY,EventListActivity.PROFILE_ACTIVITY);
                startActivityForResult(i,1);
            }
        });
    }
}
