package rajeevpc.materialdesigntest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import rajeevpc.materialdesigntest.network.VolleySingleton;
import rajeevpc.materialdesigntest.tabs.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

       drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout),toolbar);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout)findViewById(R.id.tabs);

        mTabs.setCustomTabView(R.layout.custom_tab_veiw,R.id.tabText);
        mTabs.setDistributeEvenly(true);
//        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.colorAccent);
//            }
//        });
        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTabs.setViewPager(mPager);
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
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_navigation) {
            startActivity(new Intent(this, SubActivity.class));
        }


        if (id == R.id.action_library) {
            startActivity(new Intent(this, ActivityUsingTabLibrary.class));
        }

        return super.onOptionsItemSelected(item);
    }
//    class MyPagerAdapter extends FragmentPagerAdapter{
//        String[] tabs;
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//            tabs = getResources().getStringArray(R.array.tabs);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            MyFragment myFragment =MyFragment.getInstance(position);
//            return myFragment;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position)
//        {
//            return tabs[position];
//        }
//
//        @Override
//        public int getCount() {
//            return 5;
//        }
//    }

    public static class MyFragment  extends Fragment{
        private TextView textView;
        public static MyFragment getInstance(int position){
            MyFragment myFragment = new MyFragment();
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
            RequestQueue requestQueue = VolleySingleton.getInstance().getmRequestQueue();
            StringRequest request =new StringRequest(Request.Method.GET, "http://www.php.net/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(),"RESPONSE "+response,Toast.LENGTH_SHORT).show();

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
    class MyPagerAdapter extends  FragmentPagerAdapter {
        int icons [] ={R.drawable.ic_home_black_24dp,R.drawable.ic_location_on_black_24dp,R.drawable.ic_send_black_24dp
                ,R.drawable.ic_location_on_black_24dp};
        String[] tabText =getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText =getResources().getStringArray(R.array.tabs);

        }

        @Override
        public Fragment getItem(int position) {
            MyFragment fragment = MyFragment.getInstance(position);
            return fragment;
        }
        public CharSequence getPageTitle(int position){
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;

        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
