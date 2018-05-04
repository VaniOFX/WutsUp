package hk.ust.cse.comp4521.watsup;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OptionsActivity extends AppCompatActivity {

    ImageButton imageButtonAE;
    ImageButton imageButtonExplore;
    ImageButton imageButtonProfile;
    ImageButton imageButtonAddL;
    ImageButton imageButtonExL;
    ImageButton imageButtonProfL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Button addEventButton = (Button) findViewById(R.id.addevent);
        Button exploreEventsButton = (Button) findViewById(R.id.explorebutton);



        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddEventActivity.class);
                startActivity(i);
            }
        });


        exploreEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EventListActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Log.i(TAG, "onConfigurationChanged called.");
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.activity_options);
                imageButtonAE = (ImageButton) findViewById(R.id.imageaddeb);
                imageButtonExplore = (ImageButton) findViewById(R.id.imageButton3);
                imageButtonProfile = (ImageButton) findViewById(R.id.imageButton4);

                if(!imageButtonAE.hasOnClickListeners()){
                    imageButtonAE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), AddEventActivity.class);
                            startActivity(i);
                        }
                    });
                }
                if(!imageButtonExplore.hasOnClickListeners()){
                    imageButtonExplore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), EventListActivity.class);
                            startActivity(i);
                        }
                    });
                }
                if(!imageButtonProfile.hasOnClickListeners()){
                    imageButtonExplore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), EventListActivity.class);
                            startActivity(i);
                        }
                    });
                }
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.activity_options);
                imageButtonAddL = (ImageButton) findViewById(R.id.imageButton5);
                imageButtonExL = (ImageButton) findViewById(R.id.imageButton9);
                imageButtonProfL = (ImageButton) findViewById(R.id.imageButton6);
                if(!imageButtonAddL.hasOnClickListeners()){
                    imageButtonAddL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), AddEventActivity.class);
                            startActivity(i);
                        }
                    });
                }
                if(!imageButtonExL.hasOnClickListeners()){
                    imageButtonExL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), EventListActivity.class);
                            startActivity(i);
                        }
                    });
                }
                if(!imageButtonProfL.hasOnClickListeners()){
                    imageButtonProfL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), EventListActivity.class);
                            startActivity(i);
                        }
                    });
                }
                break;
        }
    }
}
