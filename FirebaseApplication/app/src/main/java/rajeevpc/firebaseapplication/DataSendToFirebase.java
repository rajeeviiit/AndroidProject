package rajeevpc.firebaseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class DataSendToFirebase extends AppCompatActivity {

    private Button mAddButton;
    private EditText mValueField, mKeyValue;
    private Firebase mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_send_to_firebase);
        mRootRef = new Firebase("https://fir-application-e5d89.firebaseio.com/Users");

    //    data send to firebase

        mAddButton = (Button) findViewById(R.id.addButton);
        mValueField = (EditText) findViewById(R.id.valueField);
        mKeyValue = (EditText) findViewById(R.id.keyValue);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mValueField.getText().toString();
                String key = mKeyValue.getText().toString();

                Firebase childRef = mRootRef.child(key);

                childRef.setValue(value);

            }
        });


//
//        mRef = new Firebase("https://fir-application-e5d89.firebaseio.com/");
//
//        mSendData =(Button)findViewById(R.id.sendData);
//
//        mSendData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Firebase mRefChild = mRef.child("Name");
//                mRefChild.setValue("Rajeev");
//
//
//            }
//        });

    }
}
