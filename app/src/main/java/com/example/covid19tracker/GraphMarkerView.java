package com.example.covid19tracker;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class GraphMarkerView extends MarkerView {
    private TextView tvContent;

    public GraphMarkerView(Context context, int layoutResource, int drawableResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.marker_border);
        tvContent.setBackgroundDrawable(getResources().getDrawable(drawableResource));
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, false));
        } else {

            tvContent.setText("" + Utils.formatNumber(e.getY(), 0, false));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
