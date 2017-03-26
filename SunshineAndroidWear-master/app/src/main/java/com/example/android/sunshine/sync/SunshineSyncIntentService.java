/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine.sync;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.sunshine.R;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class SunshineSyncIntentService extends IntentService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleClient;

    public SunshineSyncIntentService() {
        super("SunshineSyncIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Build a new GoogleApiClient for the Wearable API
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleClient.connect();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SunshineSyncTask.syncWeather(this);
        sendDataToWearable();
        Log.v("DBG", "intent service");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // sendDataToWearable();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void sendDataToWearable(){
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sunshine_update");
        putDataMapReq.getDataMap().putInt("high", (int) SunshineSyncTask.highTemp);
        putDataMapReq.getDataMap().putInt("low", (int) SunshineSyncTask.lowTemp);
        putDataMapReq.getDataMap().putString("weather_string",
                SunshineWeatherUtils.getStringForWeatherCondition(this, SunshineSyncTask.weatherId)
        );
        // image
        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(), SunshineWeatherUtils.getSmallArtResourceIdForWeatherCondition(SunshineSyncTask.weatherId)
        );
        Asset asset = createAssetFromBitmap(bitmap);
        putDataMapReq.getDataMap().putAsset("image", asset);
        // change every time
        Calendar calendar = Calendar.getInstance();
        putDataMapReq.getDataMap().putLong("time", calendar.getTimeInMillis());
        // send
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest().setUrgent();
        Wearable.DataApi.putDataItem(googleClient, putDataReq).setResultCallback(new ResultCallbacks<DataApi.DataItemResult>() {
            @Override
            public void onSuccess(@NonNull DataApi.DataItemResult dataItemResult) {
                Log.v("DBG", "success" + dataItemResult.toString());
            }

            @Override
            public void onFailure(@NonNull Status status) {
                Log.v("DBG", "fail" + status);
            }
        });
        Log.v("DBG", "sent to wear");
    }

    private static Asset createAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }
    /* ****** */
}
