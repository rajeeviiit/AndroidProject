package rajeevpc.firebaseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private Button mAddButton;
    private Button mGetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddButton = (Button) findViewById(R.id.addButton);
        mGetButton = (Button) findViewById(R.id.getButton);




    }


    public void DataAddToFirebase(View view) {
        startActivity(new Intent(MainActivity.this,DataSendToFirebase.class));
    }


    public void GetDataFromFirebase(View view) {
        startActivity(new Intent(MainActivity.this,DataGetFromFirebase.class));
    }
}
