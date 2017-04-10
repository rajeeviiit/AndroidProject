package com.example.jsroyal.githubrepo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserID extends AppCompatActivity {
    public EditText gitHubUserName;
    private Button submit;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_id);

        gitHubUserName = (EditText) findViewById(R.id.github_user_name);
        submit = (Button) findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserID.this, MainActivity.class);
                userName = gitHubUserName.getText().toString().trim();
                intent.putExtra("githubRepo", userName);
                startActivity(intent);

            }
        });

    }
}
