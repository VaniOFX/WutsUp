package hk.ust.cse.comp4521.watsup;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;
import java.io.InputStream;

import static hk.ust.cse.comp4521.watsup.models.Activities.CALLING_ACTIVITY;
import static hk.ust.cse.comp4521.watsup.models.Activities.ENROLLED_ACTIVITY;
import static hk.ust.cse.comp4521.watsup.models.Activities.HOSTED_ACTIVITY;

public class ProfileActivity extends AppCompatActivity {

    private Uri imageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    protected void onStart(){
        super.onStart();
        DataBaseCommunicator.setUpDataBase();
        DataBaseCommunicator.setEnrolled(FirebaseAuth.getInstance().getUid());


        TextView profileName = (TextView) findViewById(R.id.profileName);
        ImageView profileImage = (ImageView) findViewById(R.id.profileImageView);

        final AlertDialog dialog = createDialog();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Button enrolledEvents = (Button) findViewById(R.id.enrolledEvents);
        enrolledEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EventListActivity.class);
                i.putExtra(CALLING_ACTIVITY, ENROLLED_ACTIVITY);
                startActivityForResult(i,1);
            }
        });

        Button myEvents = (Button) findViewById(R.id.myevents);
        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EventListActivity.class);
                i.putExtra(CALLING_ACTIVITY, HOSTED_ACTIVITY);
                startActivityForResult(i,1);
            }
        });


        new DownloadImageTask((ImageView) findViewById(R.id.profileImageView))
                .execute(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());

    }

    private AlertDialog createDialog(){
        final String[] options = new String[]{"From Camera", "From SD Card"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileActivity.this,android.R.layout.select_dialog_item,options);
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Select an Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(),"tmp_avatar"+String.valueOf(System.currentTimeMillis()) +".jpg");
                    imageCaptureUri = Uri.fromFile(file);
                    try{
                        i.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                        i.putExtra("returndata", true);
                        startActivityForResult(i,1);
                    }
                    catch (Exception e){

                    }
                    dialog.cancel();
                }
                else{
                    Intent i = new Intent();
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(i,"Complete Action Using"), 2);
                }
            }
        });
        return builder.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== RESULT_OK){
            if(requestCode == 2){
                imageCaptureUri = data.getData();
            }
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(imageCaptureUri)
                    .build();
            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
        }
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
