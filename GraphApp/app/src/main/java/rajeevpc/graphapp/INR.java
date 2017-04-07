package rajeevpc.graphapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class INR extends AppCompatActivity {

    String str_date = "2013-09-01";
    String end_date = "2013-09-05";
    String currency_INR = "INR";
    String CURRENCY_URL = "http://api.coindesk.com/v1/bpi/historical/close.json";
    String KEY_START = "start";
    String KEY_END = "end";
    String KEU_INDEX = "currency";




    private ArrayList<String> getDates() throws ParseException {
        ArrayList<String> dates = new ArrayList<>();

        String str_date = "2013-09-01";
        String end_date = "2013-09-05";


        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(str_date);
        Date endDate = formatter.parse(end_date);
        long interval = 24 * 1000 * 60 * 60;
        long endTime = endDate.getTime();
        long currTime = startDate.getTime();
        while (currTime <= endTime) {
            dates.add(formatter.format(new Date(currTime)));
            currTime += interval;
        }
        return dates;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inr);


        //start parsing
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CURRENCY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            obj = obj.getJSONObject("bpi");
                            Iterator<?> keys = obj.keys();
                            int size = obj.length();
                            int index = 0;
                            GraphView graph = (GraphView) findViewById(R.id.graph_inr);
                            DataPoint[] points = new DataPoint[size];
                            while (keys.hasNext()) {
                                String key = (String) keys.next();
                                double currency = obj.getDouble(key);
                                points[index] = new DataPoint(index, currency);
                                index++;
                            }
                            Log.e("Value of index", "" + index);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
                            graph.addSeries(series);
                        } catch (JSONException e) {
                            Toast.makeText(INR.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error in Request", "" + error);
                        Toast.makeText(INR.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_START, str_date);
                map.put(KEY_END, end_date);
                map.put(KEU_INDEX, currency_INR);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.graph, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_logout) {
            startActivity(new Intent(INR.this, AccountActivity.class));
        }


        return super.onOptionsItemSelected(item);


    }
}


