package rajeevpc.androidtask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static rajeevpc.androidtask.ActorsAdapter.actors;


public class Two extends Fragment {

    private Context context;
    private TextView tv, spouse, child, title;

    public Two() {

    }

    SharedPreferences sharedpreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rooview = inflater.inflate(R.layout.fragment_two, container, false);


        return rooview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv = (TextView) view.findViewById(R.id.text);
        spouse = (TextView) view.findViewById(R.id.spouse);
        child = (TextView) view.findViewById(R.id.children);
        title = (TextView) view.findViewById(R.id.title);
        sharedpreferences = context.getSharedPreferences("mpref", Context.MODE_PRIVATE);
        //     tv.setText(sharedpreferences.getString("des","None"));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setData(Actors actors) {

        tv.setText("Description : " + actors.getDescription());
        spouse.setText("Spouse : " + actors.getSpouse());
        child.setText("Children : " + actors.getChildren());
        title.setText(actors.getName());
    }
}
