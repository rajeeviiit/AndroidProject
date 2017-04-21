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

public class SecondActivity extends AppCompatActivity {
    public static final String DEFAULT = "N/A";
    TextView tvoutusername, tvoutpassword, tvoutusername1, tvoutpassword2;
    ;

    Button btnload, btnprevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvoutusername = (TextView) findViewById(R.id.tvname);
        tvoutpassword = (TextView) findViewById(R.id.tvpass);
        tvoutusername1 = (TextView) findViewById(R.id.tvoutusername);
        tvoutpassword2 = (TextView) findViewById(R.id.tvoutpassword);
        btnload = (Button) findViewById(R.id.btnsave);
        btnprevious = (Button) findViewById(R.id.btnprevious);

        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });
    }

    public void load(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("Mydata", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", DEFAULT);
        String password = sharedPreferences.getString("password", DEFAULT);
        if (name.equals(DEFAULT) || password.equals(DEFAULT)) {

            Toast.makeText(this, "No Data was Found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data Loaded Successfully", Toast.LENGTH_SHORT).show();
             tvoutusername1.setText(name);
            tvoutpassword2.setText(password);
        }
    }
}
