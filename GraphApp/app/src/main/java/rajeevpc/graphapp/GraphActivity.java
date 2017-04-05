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
import java.util.Map;

public class GraphActivity extends AppCompatActivity{

    String str_date = "2013-09-01";
    String end_date = "2013-09-05";
    String CURRENCY_URL = "http://api.coindesk.com/v1/bpi/historical/close.json";
    String KEY_START = "start";
    String KEY_END = "end";

    ArrayList<String> dates = new ArrayList<>();

    ArrayList<Dataa> datas = new ArrayList<>();



    private ArrayList<String> getDates() throws ParseException {
        ArrayList<String> dates = new ArrayList<>();

        String str_date = "2013-09-01";
        String end_date = "2013-09-05";

        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(str_date);
        Date endDate = formatter.parse(end_date);
        long interval = 24*1000*60*60;
        long endTime = endDate.getTime();
        long currTime = startDate.getTime();
        while(currTime<=endTime){
            dates.add(formatter.format(new Date(currTime)));
            currTime+=interval;
        }
        return dates;
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        //start parsing
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CURRENCY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jobj = new JSONObject(response);



                            // token = jobj.getString("token");



                            //test

                            DateFormat formatter;
                            formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date startDate = formatter.parse(str_date);
                            Date endDate = formatter.parse(end_date);
                            long interval = 24*1000*60*60;
                            long endTime = endDate.getTime();
                            long currTime = startDate.getTime();
                            while(currTime<=endTime){
                                dates.add(formatter.format(new Date(currTime)));
                                currTime+=interval;
                            }


                            //data

                            JSONObject object = new JSONObject(response);
                            JSONObject bpiObject = object.getJSONObject("bpi");


                            ArrayList<String> datesKeys = getDates();

                            RateParser rp = new RateParser();

                            for(int i=0;i<datesKeys.size();i++){
                                datas.add(new Dataa(datesKeys.get(i).toString(),bpiObject.getDouble(datesKeys.get(i))));
                            }











                            Log.e("tag", response);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GraphActivity.this,error.toString(),Toast.LENGTH_LONG ).show();


                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_START,str_date);
                map.put(KEY_END,end_date);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        //end of parsing





        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(dates.indexOf(0), datas.indexOf(0)),
                new DataPoint(dates.indexOf(1), datas.indexOf(1)),
                new DataPoint(dates.indexOf(2), datas.indexOf(2)),
                new DataPoint(dates.indexOf(3), datas.indexOf(3)),
                new DataPoint(dates.indexOf(4), datas.indexOf(4)),

        });
        graph.addSeries(series);


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
            startActivity(new Intent(GraphActivity.this, AccountActivity.class));
        }


        return super.onOptionsItemSelected(item);




    }
}

class Dataa {
    public String date;
    public double rate;

    public Dataa(String date, double rate) {
        this.date = date;
        this.rate = rate;
    }


}



