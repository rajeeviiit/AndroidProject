package rajeevpc.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvusername ,tvpassword;
    EditText etusername,etpassword;
    Button btnsave,btnnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvusername = (TextView)findViewById(R.id.tvusername);
        tvpassword = (TextView)findViewById(R.id.tvpassword);
        etusername = (EditText)findViewById(R.id.etusername);
        etpassword = (EditText)findViewById(R.id.etpassword);
        btnsave = (Button)findViewById(R.id.btnsave);
        btnnext = (Button)findViewById(R.id.btnnext);



        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });


    }

    public void save(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("Mydata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",etusername.getText().toString());
        editor.putString("password",etpassword.getText().toString());
        editor.commit();
        Toast.makeText(this,"Data was saved Successfully",Toast.LENGTH_SHORT).show();


    }
}
