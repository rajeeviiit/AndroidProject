package rajeevpc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private CircleImageView mDisplayImage;
    TextView mName;
    TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);
//        mDisplayImage = (CircleImageView)findViewById(R.id.circleImageView);
//        mName = (TextView)findViewById(R.id.text_display_name);
//        mStatus = (TextView)findViewById(R.id.text_status);
//        mAuth = FirebaseAuth.getInstance();
//
//        Bundle bundle = new Bundle();
//
//        bundle = getIntent().getExtras();
//        if (bundle != null)
//        {   String passuid = bundle.getString("passuid");
//            System.out.println("passuid : " + passuid);
//            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(passuid);
//
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    String image = dataSnapshot.child("image").getValue().toString();
//                    String name = dataSnapshot.child("name").getValue().toString();
//                    String status = dataSnapshot.child("status").getValue().toString();
//                    String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
//
//                    mName.setText(name);
//                    mStatus.setText(status);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//        }
//
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.main_menu,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            super.onOptionsItemSelected(item);
            if (item.getItemId()==R.id.main_logout_btn){
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                this.finish();
                System.exit(0);

            }
            if (item.getItemId()==R.id.main_profile_btn){
                startActivity(new Intent(FeedPage.this,ProfileActivity.class));

            }
            return true;
        }
}