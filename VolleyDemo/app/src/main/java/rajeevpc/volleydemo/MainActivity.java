package rajeevpc.volleydemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    String server_url ="http://192.168.52.1:81/baby.jpg";
//    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
//        textView=(TextView)findViewById(R.id.txt);
        final ImageView imageView = (ImageView)findViewById(R.id.image);
        button = (Button)findViewById(R.id.bn1);
//        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
//        Network network = new BasicNetwork(new HurlStack());
//        requestQueue = new RequestQueue(cache,network);
//        requestQueue.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                ImageRequest imageRequest = new ImageRequest(server_url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);

                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Something went wrong ...",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                });

//                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                             textView.setText(response);
////                                requestQueue.stop();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        textView.setText("Something went wrong .....");
//                        error.printStackTrace();
////                        requestQueue.stop();
//
//                    }
//                });
//                requestQueue.add(stringRequest);
                Mysingleton.getInstance(getApplicationContext()).addToRequestque(imageRequest);
            }
        });
    }
}
