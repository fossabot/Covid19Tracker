package com.example.covid19tracker;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public interface OnHistoryFetchListener {
    void onTaskCompleted(String fromDate, String toDate, ArrayList<Entry> total, ArrayList<Entry> recovered, ArrayList<Entry> death);
}
