package com.example.prateek.bimsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class LocationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LocationFragment() {
    }

    public static LocationFragment newInstance(int index) {
        LocationFragment fragment = new LocationFragment();
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

    Button buttonPickLocation;
    int status = 1;
    Place place;
    TextView textViewAddress, textViewAddress2;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    RadioButton radioButton, radioButton1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_location, container, false);
        textViewAddress =(TextView)view.findViewById(R.id.textViewAddress);
        textViewAddress2 =(TextView)view.findViewById(R.id.textViewAddress2);

        if(storeSharedPreferences.getUserCustomLocation(getActivity())!=null){
            textViewAddress2.setText(storeSharedPreferences.getUserCustomLocation(getActivity()));
        }
        buttonPickLocation = (Button)view.findViewById(R.id.buttonPickLocation);
        buttonPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
                if (status != ConnectionResult.SUCCESS) {
                    if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                        GooglePlayServicesUtil.getErrorDialog(status, getActivity(),
                                100).show();
                    }
                }
                if (status == ConnectionResult.SUCCESS) {
                    int PLACE_PICKER_REQUEST = 199;
                    LatLng topLeft = new LatLng(0, 0);
                    LatLng bottomRight = new LatLng(0,0);
                    // if(StoreSharedPreferences.getUserCustomLocation(ProceedOrder.this).equals("Gandhinagar")) {
                    topLeft = new LatLng(23.179860, 72.649143);
                    bottomRight = new LatLng(23.249227, 72.652202);
//            }
//            else if(StoreSharedPreferences.getUserCustomLocation(ProceedOrder.this).equals("Vadodara")){
//                topLeft = new LatLng(22.265240, 73.144044);
//                bottomRight = new LatLng(22.381635, 73.195201);
//            }
                    LatLngBounds bounds = new LatLngBounds(topLeft, bottomRight);
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    builder.setLatLngBounds(bounds);
                    try {
                        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        radioButton = (RadioButton)view.findViewById(R.id.radio_ninjas);
        radioButton1 = (RadioButton)view.findViewById(R.id.radio_ninjas2);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "hi,", Toast.LENGTH_LONG).show();
                radioButton1.setChecked(false);
                storeSharedPreferences.setUserCustomLocation(getActivity(), textViewAddress2.getText().toString());
            }
        });
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton.setChecked(false);
                storeSharedPreferences.setUserCustomLocation(getActivity(), textViewAddress.getText().toString());
            }
        });


        return view;
    }


    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        }
        if (requestCode == 199) {
            if (data != null) {
                place = PlacePicker.getPlace(data, getActivity());
                LatLngBounds place2 = PlacePicker.getLatLngBounds(data);
                String toastMsg = String.format("Place: %s", place.getAddress()+" sfd"+place2.toString());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                textViewAddress.setText(place.getAddress());

                storeSharedPreferences.setUserCustomLocation(getActivity(), place.getAddress().toString());
                storeSharedPreferences.setUserCoordinates(getActivity(), place.getLatLng().toString());


            } else {
                Toast.makeText(getActivity(), "Select your location", Toast.LENGTH_LONG).show();
            }
        }
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
