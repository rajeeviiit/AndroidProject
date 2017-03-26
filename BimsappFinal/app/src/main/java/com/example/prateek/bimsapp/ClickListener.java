package com.example.prateek.bimsapp;

import android.view.View;

/**
 * Created by prateek on 7/10/16.
 */
public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
