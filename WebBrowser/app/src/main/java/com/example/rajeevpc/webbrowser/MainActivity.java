package com.example.rajeevpc.webbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WebView browser;        // class variables
    private Button goBtn;           // class variables
    private EditText urlBox;        // class variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","onCreate");

        // instansiating the webview component  (creating objects)
        browser= (WebView) findViewById(R.id.webBrowser);
        // findViewById(R.id.webBrowser); returns a view of webview
        // which we type cast to webview
        // can be done also like
        // WebView browser=(WebView) findViewById(R.id.webBrowser);
        // instansiating the edit text component    (creating objects)
        browser.setWebViewClient(new WebViewClient());
        // this is for new webview client so that no system broser is opened

        urlBox= (EditText) findViewById(R.id.urlBox);
        //same
        goBtn= (Button) findViewById(R.id.goBtn);
        // now setting click listeners
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // on clicking the button we will grab the value of input field and set it as url of the webview component
                    String url="http://"+urlBox.getText().toString();     // grabbing the input of the input field
                    // we assume that url given by user is a valid url i.e  www.google.com
                    browser.loadUrl(url);                       // now passing the url to webview method
            }
        });
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
