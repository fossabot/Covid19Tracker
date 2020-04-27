package com.example.covid19tracker;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.covid19tracker.NetworkChecker.dialogBuilder;
import static com.example.covid19tracker.NetworkChecker.isNetworkAvailable;

public class GraphFragment extends Fragment implements AdapterView.OnItemSelectedListener, OnHistoryFetchListener, OnTaskCompleted {
    private Spinner spinner;
    private ImageView imgFlag;
    private ImageView graphIcon1, graphIcon2, graphIcon3;
    private LineChart mChart1, mChart2, mChart3;
    private TextView chartTitle1, chartTitle2, chartTitle3;
    private LottieAnimationView lottieGraph1, lottieGraph2, lottieGraph3;
    private CountryModel countryModel;
    private TickerView duration;

    public GraphFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isAdded()) {
            if (!isNetworkAvailable(getContext())) {
                dialogBuilder(getContext()).show();
            } else {
                //If connection available then
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.countriesforgraph, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner = view.findViewById(R.id.graph_spinner);
                imgFlag = view.findViewById(R.id.flag_holderGraph);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(this);
                spinner.setSelection(77); //Setting Default India
                spinner.setPrompt("Select Country");

                componentInitialzer(view);
                /*graphGenerator();*/
                super.onViewCreated(view, savedInstanceState);
            }
        }
    }

    private void graphGenerator(String requestType, LineChart mchart, ArrayList<Entry> arrayList, int graphLineColor, int markerBorderBg, LottieAnimationView graphLoadingAnimation) {
        XAxis xAxis = mchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.default_text));
        xAxis.setGridColor(getResources().getColor(R.color.default_text));
        xAxis.setTextSize(12);

        YAxis yAxisLeft = mchart.getAxisLeft();
        YAxis yAxisRight = mchart.getAxisRight();
        yAxisLeft.setTextColor(getResources().getColor(R.color.default_text));
        yAxisRight.setTextColor(getResources().getColor(R.color.default_text));
        yAxisLeft.setGridColor(getResources().getColor(R.color.default_text));
        yAxisRight.setGridColor(getResources().getColor(R.color.default_text));
        yAxisLeft.setTextSize(12);
        yAxisRight.setTextSize(12);

        mchart.setDragEnabled(true);
        mchart.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mchart.setDescription(null);
        mchart.moveViewToX(10);
        mchart.setVisibleXRangeMaximum(5);
        GraphMarkerView mv = new GraphMarkerView(getContext(), R.layout.custom_marker_view, markerBorderBg);
        mchart.setMarkerView(mv);
        mchart.getLegend().setEnabled(false);
        mchart.setExtraOffsets(5, 0, 5, 10);

        LineDataSet set1;
        set1 = new LineDataSet(arrayList, "");
        set1.setHighLightColor(Color.parseColor("#fd6c9e"));
        set1.setHighlightLineWidth(0.5F);

        set1.setLineWidth(3.5f);

        set1.setColor(getContext().getResources().getColor(graphLineColor));
        set1.setValueTextColor(getResources().getColor(graphLineColor));
        set1.setDrawValues(false);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        mchart.setData(data);
        lottieGraph1.cancelAnimation();
        CardView graph1 = getView().findViewById(R.id.cardgraph1);
        CardView graph2 = getView().findViewById(R.id.cardgraph2);
        CardView graph3 = getView().findViewById(R.id.cardgraph3);
        graph1.setVisibility(View.VISIBLE);
        graph2.setVisibility(View.VISIBLE);
        graph3.setVisibility(View.VISIBLE);
        lottieGraph1.setVisibility(View.GONE);
        mchart.animateXY(1000, 1000);
    }

    private void componentInitialzer(View view) {

        graphIcon1 = view.findViewById(R.id.graph_icon1);
        imageColorChanger(graphIcon1, R.color.blue);
        graphIcon2 = view.findViewById(R.id.graph_icon2);
        graphIcon3 = view.findViewById(R.id.graph_icon3);
        chartTitle1 = view.findViewById(R.id.graph_title1);
        chartTitle2 = view.findViewById(R.id.graph_title2);
        chartTitle3 = view.findViewById(R.id.graph_title3);
        mChart1 = view.findViewById(R.id.linechart1);
        mChart2 = view.findViewById(R.id.linechart2);
        mChart3 = view.findViewById(R.id.linechart3);
        mChart1.setNoDataText("");
        mChart2.setNoDataText("");
        mChart3.setNoDataText("");
        //setting html font
        chartTitle1.setText(Html.fromHtml(getString(R.string.total_graph_title)));
        chartTitle2.setText(Html.fromHtml(getString(R.string.recovered_graph_title)));
        chartTitle3.setText(Html.fromHtml(getString(R.string.dead_graph_title)));

        lottieGraph1 = view.findViewById(R.id.graph2_anim);
        /*lottieGraph2 = view.findViewById(R.id.graph2_anim);
        lottieGraph3 = view.findViewById(R.id.graph3_anim);*/

        duration = view.findViewById(R.id.graph_duration);
        duration.setCharacterLists(TickerUtils.provideNumberList());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString(); //Getting spinner data
        FetchData f = new FetchData(this);
        f.setCOUNTRY(text);//Setting the country got from spinner
        f.execute();//Starting AsyncTask
        FetchHistoryData fd = new FetchHistoryData(this);
        fd.setArguments(text);
        fd.execute();//Starting AsyncTask
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setFlag(CountryModel fetchedData) {
        if (fetchedData.getCountryCode().equalsIgnoreCase("null")) {
            Picasso
                    .get()
                    .load("https://i.imgur.com/xrLk1Mf.png")
                    .resize(50, 50)
                    .into(imgFlag);
        } else {
            Picasso
                    .get()
                    .load("https://corona.lmao.ninja/assets/img/flags/" + fetchedData.getCountryCode())
                    .resize(75, 50)
                    .into(imgFlag);
        }
    }

    @Override
    public void onTaskCompleted(CountryModel o) {
        todayDataAdder(o);
        setFlag(o);
    }

    private void todayDataAdder(CountryModel o) {
        this.countryModel = o;
    }

    @Override
    public void onTaskCompleted(String fromDate, String toDate, ArrayList<Entry> totalList, ArrayList<Entry> recoveredList, ArrayList<Entry> deadList) {
        if (isAdded()) {
            graphGenerator("cases", mChart1, totalList, R.color.blue, R.drawable.marker_border_blue, lottieGraph1);
            graphGenerator("recovered", mChart2, recoveredList, R.color.green, R.drawable.marker_border_green, lottieGraph2);
            graphGenerator("dead", mChart3, deadList, R.color.red, R.drawable.marker_border_red, lottieGraph3);
            duration.setText("Data Duration: " + fromDate + " -- " + toDate);
        }
    }

    public void imageColorChanger(ImageView img, int id) {
        int newColor = getContext().getResources().getColor(id);
        img.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        //image.setColorFilter(null); for resetting the imageView
    }

}//End of class


