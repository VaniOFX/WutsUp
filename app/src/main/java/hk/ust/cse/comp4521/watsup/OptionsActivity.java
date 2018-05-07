package hk.ust.cse.comp4521.watsup;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OptionsActivity extends AppCompatActivity {

    private static final String TAG = "OptionsActivity";

    ImageButton imageButtonAE;
    ImageButton imageButtonExplore;
    ImageButton imageButtonProfile;

    ImageButton imageButtonAddL;
    ImageButton imageButtonExL;
    ImageButton imageButtonProfL;

    View.OnClickListener exploreButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), EventListActivity.class);
            i.putExtra(EventListActivity.CALLING_ACTIVITY,EventListActivity.OPTIONS_ACTIVITY);
            startActivityForResult(i,1);
        }
    };

    View.OnClickListener addEventButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(OptionsActivity.this, AddEventActivity.class);
            startActivity(i);
        }
    };

    View.OnClickListener profileButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(OptionsActivity.this, ProfileActivity.class);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Options Activity started");
        setContentView(R.layout.activity_options);
        Button addEventButton = (Button) findViewById(R.id.addevent);
        Button exploreEventsButton = (Button) findViewById(R.id.explorebutton);
        Button profileButton = (Button) findViewById(R.id.profile);

        addEventButton.setOnClickListener(addEventButtonListener);
        exploreEventsButton.setOnClickListener(exploreButtonListener);
        profileButton.setOnClickListener(profileButtonListener);

//        checkOrientation(this.getResources().getConfiguration());
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Log.d(TAG, "onConfigurationChanged called.");
//        checkOrientation(newConfig);
//    }
//
//    private void checkOrientation(Configuration newConfig) {
//        switch (newConfig.orientation) {
//            case Configuration.ORIENTATION_PORTRAIT:
//                setContentView(R.layout.activity_options);
//                imageButtonAE = (ImageButton) findViewById(R.id.imageaddeb);
//                imageButtonExplore = (ImageButton) findViewById(R.id.imageButton3);
//                imageButtonProfile = (ImageButton) findViewById(R.id.imageButton4);
//
//                if(!imageButtonAE.hasOnClickListeners()) imageButtonAE.setOnClickListener(addEventButtonListener);
//
//                if(!imageButtonExplore.hasOnClickListeners()) imageButtonExplore.setOnClickListener(exploreButtonListener);
//
//                if(!imageButtonProfile.hasOnClickListeners()) imageButtonExplore.setOnClickListener(profileButtonListener);
//
//                break;
//
//            case Configuration.ORIENTATION_LANDSCAPE:
//                setContentView(R.layout.activity_options);
//                imageButtonAddL = (ImageButton) findViewById(R.id.imageButton5);
//                imageButtonExL = (ImageButton) findViewById(R.id.imageButton9);
//                imageButtonProfL = (ImageButton) findViewById(R.id.imageButton6);
//
//                if(!imageButtonAddL.hasOnClickListeners()) imageButtonAddL.setOnClickListener(addEventButtonListener);
//
//                if(!imageButtonExL.hasOnClickListeners()) imageButtonExL.setOnClickListener(exploreButtonListener);
//
//                if(!imageButtonProfL.hasOnClickListeners()) imageButtonProfL.setOnClickListener(profileButtonListener);
//
//                break;
//        }
//    }
}
