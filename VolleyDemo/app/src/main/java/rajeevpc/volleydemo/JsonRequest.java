package rajeevpc.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonRequest extends AppCompatActivity {
    Button button;
    TextView Name,Email,Mobile;
    String json_url ="http://192.168.52.1:81/getinfo.php";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_request);
        button = (Button)findViewById(R.id.bnInfo);
        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        Mobile= (TextView)findViewById(R.id.mobile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                JSONObject object = new JSONObject();


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(json_url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    Name.setText(response.getString("Name"));
                                    Email.setText(response.getString("Email"));
                                    Mobile.setText(response.getString("Mobile"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JsonRequest.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String,String> hm = new HashMap<String, String>();
                        return hm;

                        //return super.getParams();
                    }
                };

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                Mysingleton.getInstance(getApplicationContext()).addToRequestque(jsonObjectRequest);


            }
        });

    }
}
