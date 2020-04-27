package com.example.covid19tracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutFragment extends Fragment {
    ImageView aInsta, aTwitter, aFacebook, aGithub;
    ImageView yInsta, yTwitter, yFacebook, yGithub;
    TextView description;
    CardView paypalbtn;
    String anujLinks[] = {
            "https://www.instagram.com/anuj.mutha",
            "https://www.facebook.com/anuj.mutha.31",
            "https://twitter.com/anuj_mutha",
            "https://github.com/Anuj200016"};
    String yashLinks[] = {
            "https://www.instagram.com/muthayashm",
            "https://www.facebook.com/muthayashm",
            "https://twitter.com/muthayashm",
            "https://github.com/muthayashm"};
    private ImageView sharebtn;
    private TextView paypal;
    private boolean isCheck = false;

    public AboutFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isAdded()) {
            super.onViewCreated(view, savedInstanceState);

            /*description = view.findViewById(R.id.app_description);
            description.setText(Html.fromHtml(getString(R.string.about_yash_anuj)));*/

            description = view.findViewById(R.id.app_description);
            description.setText(Html.fromHtml(getString(R.string.app_disclaimer)));


            profiles(view);

            sharebtn = view.findViewById(R.id.sharebtn);
            sharebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            /*    Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                intent.putExtra(Intent.EXTRA_TEXT, "Check Out this app");
                startActivity(Intent.createChooser(intent, "Choose one to share"));*/
                    Toast.makeText(getContext(), "Shared", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void profiles(View view) {
        paypal = view.findViewById(R.id.paypal_title);
        paypal.setText(Html.fromHtml(getString(R.string.AboutFragmentPaypalTitle)));

        aInsta = view.findViewById(R.id.ainsta);
        aFacebook = view.findViewById(R.id.aface);
        aTwitter = view.findViewById(R.id.atwit);
        aGithub = view.findViewById(R.id.agit);
        paypalbtn = view.findViewById(R.id.paypalcardview);

        yInsta = view.findViewById(R.id.yinsta);
        yFacebook = view.findViewById(R.id.yface);
        yTwitter = view.findViewById(R.id.ytwit);
        yGithub = view.findViewById(R.id.ygit);

        aInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = anujLinks[0];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        aFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = anujLinks[1];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        aTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = anujLinks[2];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        aGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = anujLinks[3];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //For yash

        yInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = yashLinks[0];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        yFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = yashLinks[1];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        yTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = yashLinks[2];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        yGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = yashLinks[3];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        paypalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.paypal.me/AnujMutha";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
