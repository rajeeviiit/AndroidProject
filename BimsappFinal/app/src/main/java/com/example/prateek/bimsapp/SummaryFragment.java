package com.example.prateek.bimsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SummaryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    public SummaryFragment() {
    }

    public static SummaryFragment newInstance(int index) {
        SummaryFragment fragment = new SummaryFragment();
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



    TextView nameTv, addressTv, totalTv, remarksTv, foodItemList;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    List<FoodQuantity> l = new ArrayList<>();
    String itemString = "";
    Order order = new Order();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        l = storeSharedPreferences.loadFavorites(getActivity());


        order.setAddress(storeSharedPreferences.getUserCustomLocation(getActivity()));
        order.setAmount("120");
        order.setCoordinates(storeSharedPreferences.getUserCoordinates(getActivity()));

        int total = 0;
        for (int i=0;i<l.size();i++){

            int k = Integer.parseInt(l.get(i).getQuantity());
            int j = Integer.parseInt(l.get(i).getPrice());
            int m = k*j;

            itemString = itemString + l.get(i).getFood() +"     X     "+
                    l.get(i).getQuantity() +"     =     "+ Integer.toString(m) +","+"\n";
            total = total+m;

        }
        itemString = itemString.substring(0, itemString.length()-2);


        foodItemList = (TextView)view.findViewById(R.id.foodItemList);
        foodItemList.setText(null);
        foodItemList.setText(itemString);
        order.setItem(storeSharedPreferences.loadFavorites(getActivity()));


        order.setName(storeSharedPreferences.getUserName(getActivity()));
        order.setMail(storeSharedPreferences.getUserEmail(getActivity()));
        order.setNumber(storeSharedPreferences.getUserNumber(getActivity()));
        Toast.makeText(getActivity(), storeSharedPreferences.getUserNumber(getActivity()), Toast.LENGTH_SHORT).show();

        nameTv = (TextView)view.findViewById(R.id.nameTv);
        nameTv.setText(storeSharedPreferences.getUserName(getActivity()));

        addressTv = (TextView)view.findViewById(R.id.addressTv);
        addressTv.setText(storeSharedPreferences.getUserCustomLocation(getActivity()));

        totalTv = (TextView) view.findViewById(R.id.totalTv);
        totalTv.setText(Integer.toString(total));

        remarksTv = (TextView) view.findViewById(R.id.remarksTv);
        remarksTv.setText(storeSharedPreferences.getUserRemarks(getActivity()));
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
