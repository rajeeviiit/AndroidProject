package rajeevpc.jsonarraydemo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RajeevPC on 2/16/2017.
 */

public class BackgroundTask {
    Context context;
    ArrayList<Contact> arrayList = new ArrayList<>();
    String json_url = "https://androiddevelopmentdata.000webhostapp.com/contactinfo.php";


    public BackgroundTask(Context context){
        this.context = context;
    }

    public ArrayList<Contact> getList(){
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.POST, json_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    int count = 0;
                        while (count<response.length()){
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                Contact contact = new Contact(jsonObject.getString("Name"),jsonObject.getString("Email"));
                                arrayList.add(contact);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error...",Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }
        );

        Mysingleton.getInstance(context).addToRequestque(jsonArrayRequest);
        return arrayList;
    }
}
