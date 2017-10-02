package rajeevpc.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {
    private EditText editTextStatus;
    private EditText editTextName;
    private Button saveChanges;

    //firebase
    private DatabaseReference mStatusDatabase;

    //Progress
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        //
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(PreferenceManager
                .getDefaultSharedPreferences(StatusActivity.this)
                .getString("ID", "userid"));

        String status_value = getIntent().getStringExtra("status_value");
        String name_value = getIntent().getStringExtra("name_value");

        editTextStatus = (EditText) findViewById(R.id.et_status);
        editTextName = (EditText) findViewById(R.id.et_name);
        saveChanges = (Button) findViewById(R.id.btn_status_save);
        editTextStatus.setText(status_value);
        editTextName.setText(name_value);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Progress
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();
                String name = editTextName.getText().toString();
                String status = editTextStatus.getText().toString();
                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgress.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "There was some error in saving changes", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mStatusDatabase.child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgress.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "There was some error in saving changes", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                startActivity(new Intent(StatusActivity.this, ProfileActivity.class));


            }
        });
    }
}
