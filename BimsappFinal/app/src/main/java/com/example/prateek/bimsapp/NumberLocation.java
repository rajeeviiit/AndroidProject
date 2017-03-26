package com.example.prateek.bimsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class NumberLocation extends AppCompatActivity {

    TextView tvSelectLocation;
    EditText etMobileNumber;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_location);

        tvSelectLocation = (TextView)findViewById(R.id.tvSelectLocation);
        etMobileNumber = (EditText)findViewById(R.id.etMobileNumber);

        tvSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builderSingle = new android.app.AlertDialog.Builder(NumberLocation.this);
                builderSingle.setTitle("Select Your Location");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        NumberLocation.this, android.R.layout.select_dialog_item);
                arrayAdapter.add("Gandhinagar");
                arrayAdapter.add("Vadodara");

                builderSingle.setAdapter(

                        arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = arrayAdapter.getItem(which);
                                android.app.AlertDialog.Builder builderInner = new android.app.AlertDialog.Builder(
                                        NumberLocation.this);
                                if (strName == "Gandhinagar") {
                                    tvSelectLocation.setText(strName);
                                    StoreSharedPreferences.setUserCustomLocation(NumberLocation.this, "Gandhinagar");
                                } else if (strName == "Vadodara") {
                                    tvSelectLocation.setText(strName);
                                    StoreSharedPreferences.setUserCustomLocation(NumberLocation.this, "Vadodara");

                                }
                            }
                        });
                builderSingle.create().show();
            }
        });
    }

    public void continueNumberLocation(View view) {
        storeSharedPreferences.setUserNumber(this, etMobileNumber.getText().toString());
        Intent intent = new Intent(this, MenuMain.class);
        startActivity(intent);
    }
}
