package com.example.covid19tracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class SafetyFragment extends Fragment {
    ImageView infobtn;
    LottieAnimationView a1, a2, a3, a4, a5, a6, a7;

    public SafetyFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_safety, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isAdded()) {
            super.onViewCreated(view, savedInstanceState);
            infobtn = view.findViewById(R.id.info_btn);
            infobtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoBuilder();
                    //Toast.makeText(getContext(),"info button clicked",Toast.LENGTH_LONG).show();
                }
            });
            //animationHandler();
        }
    }

    private void animationHandler() {
        a1 = getView().findViewById(R.id.anim1);
        a2 = getView().findViewById(R.id.anim2);
        a3 = getView().findViewById(R.id.anim3);
        a4 = getView().findViewById(R.id.anim4);
        a5 = getView().findViewById(R.id.anim5);
        a6 = getView().findViewById(R.id.anim6);
        a7 = getView().findViewById(R.id.anim7);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                a1.cancelAnimation();
                a2.cancelAnimation();
                a3.cancelAnimation();
                a4.cancelAnimation();
                a4.cancelAnimation();
                a5.cancelAnimation();
                a6.cancelAnimation();
                a7.cancelAnimation();
            }
        }, 4000);
    }

    private void infoBuilder() {
        String URL = "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public";
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme));
        alert.setTitle("World Health Organization");
        WebView wv = new WebView(getContext());
        wv.loadUrl(URL);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
