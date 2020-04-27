package com.example.covid19tracker;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchData extends AsyncTask<Void, Void, Void> {
    static CountryModel countryModels;
    private static String dataToParsed = "";
    private static boolean allFlag = false;
    private static String flagURL;
    private String line = "";
    private String data = "";
    private String COUNTRY = "india";
    private OnTaskCompleted listener;
    private JSONObject JO;
    private JSONObject JOForFlag;
    private JSONArray JAForFlag;


    public FetchData() {

    }

    public FetchData(OnTaskCompleted listener) {
        this.listener = listener;
    }

    /*public String getCOUNTRY() {
        return COUNTRY;
    }*/

    public static CountryModel fromJson(JSONObject jo) throws Exception {
        CountryModel b = new CountryModel();
        // Deserialize json into object fields
        try {
            if (allFlag) {
                b.country = null;
                b.countryCode = "null";
                //Log.d("FLAG_TAG",b.countryCode);
            } else {
                b.country = jo.get("country").toString();
                b.countryCode = getCountryCode(jo);
            }
            b.total = jo.get("cases").toString();
            b.today = jo.get("todayCases").toString();
            b.dead = jo.get("deaths").toString();
            b.diedToday = jo.get("todayDeaths").toString();
            b.recovered = jo.get("recovered").toString();
            b.active = jo.get("active").toString();
            b.critical = jo.get("critical").toString();
            Log.d("FLAG_TAG", b.countryCode);
            b.timestamp = jo.get("updated").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static String getCountryCode(JSONObject jo) throws Exception {
        String code = jo.get("countryInfo").toString();

   /*   JSONArray ja = new JSONArray(code);
        JSONObject jacode = (JSONObject) ja.get(0);
        Log.d("C_LOG",jacode.get("iso2").toString());*/

        int sindex;
        sindex = code.indexOf("iso2");
        //Log.d("INDEX_LOG",sindex+"");
        String preCode = code.substring(sindex + 6, (code.indexOf("iso3")));
        String postCode = preCode.replace(",", "");
        String postCode2 = postCode.replace("\"", "").toLowerCase();
        postCode2 += ".png";
        Log.d("INDEX", postCode2);
        return postCode2;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        URL url;
        try {
            //URL url = new URL("https://coronavirus-19-api.herokuapp.com/countries/"+COUNTRY);
            if (COUNTRY.equalsIgnoreCase("View World Wide Data")) {
                allFlag = true;
                url = new URL("https://corona.lmao.ninja/v2/all");
            } else {
                allFlag = false;
                url = new URL("https://corona.lmao.ninja/v2/countries/" + COUNTRY);
            }

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            dataToParsed = "[" + data + "]";
            dataToParsed = dataToParsed.replace("null", "");
        }//End of try
        catch (Exception e) {
            e.printStackTrace();
        }//End of Catch
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            listener.onTaskCompleted(getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CountryModel getData() throws Exception {
        JSONArray JA = new JSONArray(dataToParsed);
        /*Log.d("JSON_ARRAY_LOG",JA.toString());*/
        JO = (JSONObject) JA.get(0);
        //CountryModel countryModel=new CountryModel();
        return fromJson(JO);
    }
}
