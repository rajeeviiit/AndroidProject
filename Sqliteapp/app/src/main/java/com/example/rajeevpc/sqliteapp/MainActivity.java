package com.example.rajeevpc.sqliteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
   DatabaseHelper myDb;
    EditText editname,editsurname,editmarks,edittextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      myDb=new DatabaseHelper(this);
        editname=(EditText)findViewById(R.id.editText_name);
        editsurname=(EditText)findViewById(R.id.editText2_surname);
        editmarks=(EditText)findViewById(R.id.editText3_marks);
        edittextId=(EditText)findViewById(R.id.edittextid);
        btnAddData=(Button)findViewById(R.id.button_add);
        btnViewAll=(Button)findViewById(R.id.button_ViewAll);
        btnUpdate=(Button)findViewById(R.id.button_update);
        btnDelete=(Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        upDateData();
        deleteData();
    }
    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(edittextId.getText().toString());
                        if(deletedRows >0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void upDateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      boolean isupDate =myDb.updateData(edittextId.getText().toString(),editname.getText().toString()
                              ,editsurname.getText().toString(),editmarks.getText().toString());
                        if(isupDate == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void AddData()
    {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editname.getText().toString(),
                        editsurname.getText().toString(), editmarks.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res=myDb.getAllData();
                if(res.getCount()==0){
                    //show message
                    showMessage("Error","No Data Found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append(" Id :"+ res.getString(0)+"\n");
                    buffer.append(" Name :"+ res.getString(1)+"\n");
                    buffer.append(" Surname :"+ res.getString(2)+"\n");
                    buffer.append(" Marks :"+ res.getString(3)+"\n\n");

                }
                //show all the data
                showMessage("Data",buffer.toString());
            }
        });
    }
    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
