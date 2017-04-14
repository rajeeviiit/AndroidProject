package rajeevpc.demoasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText num1, num2;
    Button btn;
    String result = "";
    String strURL ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText)findViewById(R.id.num1);
        num2 = (EditText)findViewById(R.id.num2);
        btn = (Button)findViewById(R.id.btnAdd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(num1.getText().toString());
                int j = Integer.parseInt(num2.getText().toString());
                strURL = "http://www.telusko.com/addition.htm?t1="+i+"&t2="+j;
                new MultiplyTask().execute(strURL);
            }
        });
    }

    public class MultiplyTask extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            Toast.makeText(MainActivity.this,"The output is "+s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String value = bufferedReader.readLine();
                System.out.println("Result is " + value);
                result = value;


            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }



}
