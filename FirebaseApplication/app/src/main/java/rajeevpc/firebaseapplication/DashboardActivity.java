package rajeevpc.firebaseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity{

    private Button mAddButton;
    private Button mGetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAddButton = (Button) findViewById(R.id.addButton);
        mGetButton = (Button) findViewById(R.id.getButton);




    }


    public void DataAddToFirebase(View view) {
        startActivity(new Intent(DashboardActivity.this,DataSendToFirebase.class));
    }


    public void GetDataFromFirebase(View view) {
        startActivity(new Intent(DashboardActivity.this,DataGetFromFirebase.class));
    }
}
