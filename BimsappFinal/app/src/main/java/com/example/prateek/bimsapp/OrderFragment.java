package com.example.prateek.bimsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Config;
import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    public OrderFragment() {
    }

    private String mParam1;
    private String mParam2;
    public static OrderFragment newInstance(int index) {
        OrderFragment fragment = new OrderFragment();
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

    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    ArrayList<FoodQuantity> foodQuantityArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private ProceedFoodAdapter proceedFoodAdapter;
    Button continueToOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        foodQuantityArrayList = storeSharedPreferences.loadFavorites(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_order);
        proceedFoodAdapter = new ProceedFoodAdapter(foodQuantityArrayList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(proceedFoodAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                ImageView a = (ImageView) view.findViewById(R.id.upCount);
                ImageView e = (ImageView) view.findViewById(R.id.downCount);
                final TextView b = (TextView)view.findViewById(R.id.price_quant);
                final TextView quantity = (TextView)view.findViewById(R.id.quantity);
                final String s, d;
                s = b.getText().toString();
                d = quantity.getText().toString();
                final float basePrice = Integer.parseInt(d.substring(0, d.length()-2))/Integer.valueOf(s.charAt(5)-48);
                final int c = position;
                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), ""+c, Toast.LENGTH_SHORT).show();
                        char as = s.charAt(5);
                        int a = Integer.valueOf(as);
                        a=a-48;
                        if(a!=9){
                            a++;}else{Toast.makeText(getActivity(), "Not more than 9", Toast.LENGTH_SHORT).show();}
                        b.setText(" X   "+Integer.toString(a) + "   = ");
                        int basePriceInt = (int)basePrice;
                        quantity.setText(Integer.toString(basePriceInt*Integer.parseInt(Integer.toString(a)))+" ₹");
                        Log.d("alkfd", "quantity is="+basePriceInt);

                        FoodQuantity fff;
                        fff = foodQuantityArrayList.get(c);
                        fff.setQuantity(Integer.toString(a));
                        storeSharedPreferences.removeAllQuant(getActivity());
                        for(int i=0;i<foodQuantityArrayList.size();i++){
                            FoodQuantity f;
                            f = foodQuantityArrayList.get(i);
                            storeSharedPreferences.addFavorite(getContext(), f);
                        }
                    }
                });

                e.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), ""+c, Toast.LENGTH_SHORT).show();
                        char as = s.charAt(5);
                        int a = Integer.valueOf(as);
                        if(a>20){
                            a=a-48;}
                        if(a!=0){
                            a--;
                        }else{
                        }

                        int basePriceInt = (int)basePrice;
                        quantity.setText(Integer.toString(basePriceInt*Integer.parseInt(Integer.toString(a)))+" ₹");
                        if(a==0){
                            foodQuantityArrayList.remove(c);
                            proceedFoodAdapter.notifyDataSetChanged();
                        }else {
                            b.setText(" X   "+Integer.toString(a) + "   = ");
                            FoodQuantity fff;
                            fff = foodQuantityArrayList.get(c);
                            fff.setQuantity(Integer.toString(a));
                        }
                        storeSharedPreferences.removeAllQuant(getActivity());
                        for(int i=0;i<foodQuantityArrayList.size();i++){
                            FoodQuantity f;
                            f = foodQuantityArrayList.get(i);
                            storeSharedPreferences.addFavorite(getContext(), f);
                        }
                        if(storeSharedPreferences.loadFavorites(getActivity())==null){
                            getActivity().finish();
                        }
                    }
                });
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
        mListener=null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
