package com.example.covid19tracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.covid19tracker.NetworkChecker.dialogBuilder;
import static com.example.covid19tracker.NetworkChecker.isNetworkAvailable;

public class TextFragment extends Fragment implements AdapterView.OnItemSelectedListener, OnTaskCompleted {

    private static String currentTime, updatedTime;
    private TickerView country, active, recovered, dead, total, casesToday, diedToday, critical, updateTime;
    private TextView countryTitle;
    private ImageView imageView;
    private int spinnerItemPostion;


    public TextFragment() {
        setRetainInstance(true);
    }

    public static String epochToDate(long epoch) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa (z)");
        return format.format(new Date(epoch));
    }

    public static String getCurrentTime() {
        return currentTime;
    }

    public static String getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isAdded()) {
            countryTitle = view.findViewById(R.id.countryFieldTitle);
            imageView = view.findViewById(R.id.flag_holder);

            if (!isNetworkAvailable(getContext())) {//If N/W not available
                dialogBuilder(getContext()).show();
            }//End of If
            else {//If N/W available
                super.onViewCreated(view, savedInstanceState);
                Spinner spinner = view.findViewById(R.id.selectCountryField);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.countries, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(this);
                spinner.setSelection(78); //Setting Default India
                spinner.setPrompt("Select Country");
                componentsInitializer(view); //initializing ui components
            }//End of else
        }//End of onViewCreated
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerItemPostion = position;
        String text = parent.getItemAtPosition(position).toString(); //Getting spinner data
        FetchData f = new FetchData(this);//Data Fetcher Asnyctask
        if (text.equalsIgnoreCase("all")) {
            countryTitle.setText("World");
        } else {
            countryTitle.setText("Country");
            imageView.setImageResource(0);
        }
        f.setCOUNTRY(text);//Setting the country got from spinner
        f.execute();//Starting AsyncTask
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void uiBuilder(CountryModel countryModel) {  //Setting the data to textviews
        if (countryModel.getCountry() == null) {
            country.setText("-->");
            countryTitle.setText("World");
        } else {
            country.setText(countryModel.getCountry());
        }
        active.setText(countryModel.getActive());
        recovered.setText(countryModel.getRecovered());
        dead.setText(countryModel.getDead());
        total.setText(countryModel.getTotal());
        casesToday.setText("+ " + countryModel.getToday());
        diedToday.setText("+ " + countryModel.getDiedToday());
        critical.setText(countryModel.getCritical());
        updateTime.setText("Last Updated: " + epochToDate(Long.parseLong(countryModel.getTimestamp())));

        updatedTime = getTime(Long.parseLong(countryModel.getTimestamp()));
        currentTime = getTime(System.currentTimeMillis());

        /*if(!updateStatusflag){
            if((epochToDate(Long.parseLong(countryModel.getTimestamp())).equals(epochToDate(System.currentTimeMillis())))){
                Toast.makeText(getContext(),"Already Updated.",Toast.LENGTH_SHORT).show();
            }else{
                final Snackbar snackbar = Snackbar.make(getView(),"Last Updated: "+epochToDate(Long.parseLong(countryModel.getTimestamp())),3000);
                snackbar.setAction("Okay", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                updateStatusflag=true;
                snackbar.show();
            }
        }*/


        if (countryModel.getCountryCode().equalsIgnoreCase("null")) {
            Picasso
                    .get()
                    .load("https://i.imgur.com/xrLk1Mf.png")
                    .resize(50, 50)
                    .into(imageView);
        } else {
            Picasso
                    .get()
                    .load("https://corona.lmao.ninja/assets/img/flags/" + countryModel.getCountryCode())
                    .resize(75, 50)
                    .into(imageView);
        }

    }

    private String getTime(Long epoch) {
        SimpleDateFormat format = new SimpleDateFormat("mm");
        return format.format(new Date(epoch));
    }

    private void componentsInitializer(View v) {
        country = v.findViewById(R.id.countryField);
        active = v.findViewById(R.id.activeField);
        recovered = v.findViewById(R.id.recoveredField);
        dead = v.findViewById(R.id.deadField);
        total = v.findViewById(R.id.totalCasesField);
        casesToday = v.findViewById(R.id.todayCasesField);
        diedToday = v.findViewById(R.id.todayDeadField);
        critical = v.findViewById(R.id.criticalField);
        updateTime = v.findViewById(R.id.last_updated);
        //ticker work
        country.setCharacterLists(TickerUtils.provideNumberList());
        active.setCharacterLists(TickerUtils.provideNumberList());
        recovered.setCharacterLists(TickerUtils.provideNumberList());
        dead.setCharacterLists(TickerUtils.provideNumberList());
        total.setCharacterLists(TickerUtils.provideNumberList());
        casesToday.setCharacterLists(TickerUtils.provideNumberList());
        diedToday.setCharacterLists(TickerUtils.provideNumberList());
        critical.setCharacterLists(TickerUtils.provideNumberList());
        updateTime.setCharacterLists(TickerUtils.provideNumberList());

    }

    @Override
    public void onTaskCompleted(CountryModel o) {
        //Setting Arguments
        if (isAdded()) {
            uiBuilder(o);//Setting data in fragment
        }
    }
}
