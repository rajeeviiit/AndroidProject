package rajeevpc.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static rajeevpc.myapplication.R.id.circleImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_PICK = 1;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference mImageStorage;
    private CircleImageView mDisplayImage;
    private ProgressDialog mProgressDialog;
    TextView mName;
    TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mDisplayImage = (CircleImageView) findViewById(circleImageView);
        mName = (TextView) findViewById(R.id.text_display_name);
        mStatus = (TextView) findViewById(R.id.text_status);
        mAuth = FirebaseAuth.getInstance();
        mImageStorage = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(PreferenceManager
                .getDefaultSharedPreferences(ProfileActivity.this)
                .getString("ID", "userid"));
        Log.d("IDnew1", PreferenceManager
                .getDefaultSharedPreferences(ProfileActivity.this)
                .getString("ID", "userid"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String image = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                //Image Loading from theurl
                Log.d("image", image);
                Picasso.with(ProfileActivity.this).load(image).into(mDisplayImage);

                mName.setText(name);
                mStatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main1_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_btn) {
            LoginManager.getInstance().logOut();
            Intent login = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(login);
            finish();
            // startActivity(new Intent(ProfileActivity.this,MainActivity.class));

        }
        if (item.getItemId() == R.id.main_feed_btn) {
            startActivity(new Intent(ProfileActivity.this, FeedPage.class));

        }
        return true;
    }

    public void changeStatus(View view) {
        String status_value = mStatus.getText().toString();
        String name_value = mName.getText().toString();
        Intent status_intent = new Intent(ProfileActivity.this, StatusActivity.class);
        status_intent.putExtra("status_value", status_value);
        status_intent.putExtra("name_value", name_value);

        startActivity(status_intent);

    }

    public void changeImage(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setAspectRatio(1, 1).start(ProfileActivity.this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mProgressDialog = new ProgressDialog(ProfileActivity.this);
                mProgressDialog.setTitle("Uploading Image");
                mProgressDialog.setMessage("Please Wait");
                mProgressDialog.show();
                Uri resultUri = result.getUri();
                final String current_user_id = PreferenceManager
                        .getDefaultSharedPreferences(ProfileActivity.this)
                        .getString("ID", "userid");
                Log.d("IDnew", current_user_id);

                StorageReference filePath = mImageStorage.child("profile_images").child(current_user_id + ".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            @SuppressWarnings("VisibleForTests")
                            String download_url = task.getResult().getDownloadUrl().toString();
                            Log.d("new url",download_url);
                            databaseReference.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(ProfileActivity.this, "Successfully uplaoded", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ProfileActivity.this, "Error in Uploading", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}

