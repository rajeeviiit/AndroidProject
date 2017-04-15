package rajeevpc.real_time_chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        buttonSignIn =(Button)findViewById(R.id.buttonLogin);
        editTextEmail=(EditText)findViewById(R.id.editTextLEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextLPassword);
        textViewSignUp =(TextView)findViewById(R.id.textViewSignup);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

    }
    public void userLogin(){
        String email =editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"please enter password", Toast.LENGTH_LONG).show();
            return;

        }

        progressDialog.setMessage("Logging User ..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(LoginActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });



    }


    @Override
    public void onClick(View v) {
        if(v==buttonSignIn)
        {
            userLogin();
        }
        if(v==textViewSignUp)
        {
            finish();
            startActivity(new Intent(this,RegisterActivity.class));
        }
    }
}
