package hk.ust.cse.comp4521.watsup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

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
                i.putExtra(EventListActivity.CALLING_ACTIVITY,EventListActivity.ENROLLED_EVENTS);
                startActivityForResult(i,1);
            }
        });

        Button myEvents = (Button) findViewById(R.id.myevents);
        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EventListActivity.class);
                i.putExtra(EventListActivity.CALLING_ACTIVITY,EventListActivity.HOSTED_EVENTS);
                startActivityForResult(i,1);
            }
        });


        new DownloadImageTask((ImageView) findViewById(R.id.imageView4))
                .execute(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());

//        public void onClick(View v) {
//            startActivity(new Intent(this, IndexActivity.class));
//            finish();
//
//        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
