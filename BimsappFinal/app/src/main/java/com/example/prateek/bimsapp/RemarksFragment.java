package com.example.prateek.bimsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RemarksFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RemarksFragment() {
    }

    public static RemarksFragment newInstance(int index) {
        RemarksFragment fragment = new RemarksFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText remarks;
    TextView cartNavigator;
    Button buttonAddRemarks;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remarks, container, false);
        cartNavigator = (TextView)view.findViewById(R.id.cartNavigator);
        remarks = (EditText) view.findViewById(R.id.editTextRemarks);
        buttonAddRemarks = (Button)view.findViewById(R.id.buttonAddRemarks);
        buttonAddRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setUserRemarks(getActivity(), remarks.getText().toString());
                Toast.makeText(getActivity(), "Remarks Added", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
