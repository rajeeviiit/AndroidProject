package rajeevpc.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    SharedPreferences myPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final SharedPreferences.Editor myEditor = myPreferences.edit();
        loginButton = (LoginButton)findViewById(R.id.fb_login_bn);
        loginButton.setReadPermissions("email", "public_profile");
        textView  = (TextView)findViewById(R.id.textview);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(MainActivity.this);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

//                textView.setText("Login Success \n" +
//                        loginResult.getAccessToken().getUserId()+
//                        "\n" + loginResult.getAccessToken().getToken());


                progressDialog.show();
//                FirebaseUser current_user = mAuth.getCurrentUser();
                final String uid = loginResult.getAccessToken().getUserId();
                myEditor.putString("ID", uid);
                myEditor.commit();
                Log.d("ID", myPreferences.getString("ID", "unknown"));
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("name","Rajeev singh");
                userMap.put("status","Hi there I'm using instagram");
                userMap.put("image","default");
                userMap.put("thumb_image","thumb_default");
                mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        }
                    }
                });




            }

            @Override
            public void onCancel() {
                textView.setText("Login Cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode,resultCode,data);

    }
}
