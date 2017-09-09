package rajeevpc.androidtask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class One extends Fragment {

    ArrayList<Actors> actorsList;

    ActorsAdapter adapter;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rooview = inflater.inflate(R.layout.fragment_one, container, false);


        return rooview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actorsList = new ArrayList<Actors>();
        new JSONAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");

        RecyclerView rec_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        rec_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ActorsAdapter(getActivity(), actorsList, (ActorsAdapter.OnDetailListener) activity);
        rec_view.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpPost post = new HttpPost(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(post);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("actors");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Actors actor = new Actors();

                        actor.setName(object.getString("name"));
                        actor.setDescription(object.getString("description"));
                        actor.setDob(object.getString("dob"));
                        actor.setCountry(object.getString("country"));
                        actor.setHeight(object.getString("height"));
                        actor.setSpouse(object.getString("spouse"));
                        actor.setChildren(object.getString("children"));
                        actor.setImage(object.getString("image"));

                        actorsList.add(actor);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
//            dialog.cancel();
            adapter.notifyDataSetChanged();

        }
    }


}
