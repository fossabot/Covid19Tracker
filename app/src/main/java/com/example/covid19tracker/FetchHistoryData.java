package com.example.covid19tracker;

import android.os.AsyncTask;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;

public class FetchHistoryData extends AsyncTask<Void, Void, Void> {
    static int days = 15;
    static String HistCountry = "india";
    private static String BASE_URL = null;
    private OnHistoryFetchListener listener;

    private JsonObject rootobj = null, timelineData; //May be an array, may be an object.
    private String fromDate, toDate;

    public FetchHistoryData(OnHistoryFetchListener listener) {
        this.listener = listener;
    }

    public void setArguments(String histCountry, int days) {
        FetchHistoryData.days = days;
        HistCountry = histCountry;
    }

    public void setArguments(String histCountry) {
        HistCountry = histCountry;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Connect to the URL using java's native library
        try {
            //https://corona.lmao.ninja/v2/historical?lastdays=100
            //https://corona.lmao.ninja/v2/historical/india?lastdays=50
            BASE_URL = "https://corona.lmao.ninja/v2/historical/" + HistCountry + "?lastdays=" + days;
            URL url = new URL(BASE_URL);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            rootobj = root.getAsJsonObject();
            timelineData = rootobj.get("timeline").getAsJsonObject();//getting timeline data
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Entry> getData(String type) {
        JsonObject cases = timelineData.get(type).getAsJsonObject();//getting cases
        String casesString = cases.toString();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(casesString);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
        ArrayList<Entry> caseList = new ArrayList<>();
        int j = 1;
        for (Map.Entry<String, JsonElement> entry : entries) {
            caseList.add(new Entry(j, entry.getValue().getAsFloat()));
            j++;
        }
        return caseList;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dateInit("cases");
        listener.onTaskCompleted(fromDate, toDate, getData("cases"), getData("recovered"), getData("deaths"));
    }

    public void dateInit(String type) {
        JsonObject cases = timelineData.get(type).getAsJsonObject();//getting cases
        String casesString = cases.toString();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(casesString);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
        fromDate = entries.iterator().next().getKey();
        String tempDate = fromDate;
        LocalDate tFromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("M/d/yy"));
        fromDate = tFromDate.format(DateTimeFormatter.ofPattern("M/d/yy"));
        LocalDate localDate = LocalDate.parse(tempDate, DateTimeFormatter.ofPattern("M/d/yy"));
        localDate = localDate.plusDays(days);
        toDate = localDate.format(DateTimeFormatter.ofPattern("M/d/yy"));
    }
}