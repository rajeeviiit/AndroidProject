package rajeevpc.materialdesigntest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static java.lang.System.err;

/**
 * Created by RajeevPC on 2/5/2017.
 */

public class MyFragment  extends Fragment {
    private TextView textView;
    public static MainActivity.MyFragment getInstance(int position){
        MainActivity.MyFragment myFragment = new MainActivity.MyFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my,container,false);
        textView =(TextView) layout.findViewById(R.id.position);
        Bundle bundle =getArguments();
        if(bundle!=null){
            textView.setText("The Page Selected Is "+bundle.getInt("position"));
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request =new StringRequest(Request.Method.GET, "http://www.php.net/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),"ERROR "+response,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"ERROR "+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);
        return layout;
    }
}