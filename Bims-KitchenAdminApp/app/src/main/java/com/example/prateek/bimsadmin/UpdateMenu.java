package com.example.prateek.bimsadmin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class UpdateMenu extends AppCompatActivity {

    final int RESULT_LOAD_IMAGE = 1;
    Firebase ref;
    String path;
    private final String URL = "https://bimsapp-81da4.firebaseio.com/";
    ImageView imageView;
    Button btnImage,btnUpdate;
    Spinner spinner;
    EditText et1,et2;
    String category,food,price;
    Data data;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);
        spinner = (Spinner)findViewById(R.id.spinnercat);
        adapter = ArrayAdapter.createFromResource(this,R.array.food_cat,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+ "selected",Toast.LENGTH_LONG).show();
                category = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnImage = (Button)findViewById(R.id.bselect);
        btnUpdate = (Button)findViewById(R.id.bupdate);
        spinner =(Spinner)findViewById(R.id.spinnercat);
        et1 =(EditText)findViewById(R.id.editTextitem);
        et2=(EditText)findViewById(R.id.editTextprice);
        imageView=(ImageView)findViewById(R.id.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);


        ref = new Firebase("https://bimsapp-81da4.firebaseio.com/");
        data = new Data();


    }

    public void select(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Log.d("here am I+++"+ picturePath, "oh bot");
            path = picturePath;
//            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(path));

            imagesend(new File(path));

        }
    }

    String picture_url;
    public void imagesend(File f) {
        Ion.with(getBaseContext()).load("POST", "http://uploads.im/api").uploadProgressHandler(new ProgressCallback() {
            @Override
            public void onProgress(long uploaded, long total) {
                System.out.println("uploaded " + (int) uploaded + " Total: " + total);
            }
        }).setMultipartParameter("platform", "android").setMultipartFile("image", f).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    JSONObject inerJson = new JSONObject(data);
                    String picture_url = inerJson.getString("img_url");
                    Log.d("picture_url-----", picture_url);
                    imageSet(picture_url);

                } catch (JSONException ee) {
                    Log.d("JSONException", ee + "");
                }
            }
        });

    }
    boolean flag = false;
    public void imageSet(String str){
        data.setUrl(str);
        flag = true;
    }



    public void update(View view) {
        if (flag) {
            food = et1.getText().toString();
            price = et2.getText().toString();
            data.setF(food);
            data.setCat(category);
            data.setP(price);
            data.setRating("5");



            Firebase newRef = ref.child("Menu").push();
            newRef.setValue(data);
//            System.out.println(data.getCat());
//            System.out.println(data.getF());
//            System.out.println(data.getP());
//            System.out.println(data.getRating());
//            System.out.println(data.getUrl());
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            flag = false;
            finish();
        } else {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        }
    }
}

class Data{
    private String cat;
    private String f;
    private String p;
    private String url;
    private String rating;

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }




}


