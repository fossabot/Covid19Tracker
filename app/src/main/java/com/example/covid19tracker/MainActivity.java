package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.sdsmdg.tastytoast.TastyToast;

import static com.example.covid19tracker.NetworkChecker.dialogBuilder;
import static com.example.covid19tracker.NetworkChecker.isNetworkAvailable;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private SwipeRefreshLayout pullToRefresh;

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChipNavigationBar bottomNav = findViewById(R.id.bottom_nav);//Init Bottom NavBar

        if (!isNetworkAvailable(MainActivity.this)) {
            AlertDialog.Builder builder = dialogBuilder(MainActivity.this);
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAndRemoveTask();
                    System.exit(0);
                }
            }).show();
        } else {
            if (savedInstanceState == null) {
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TextFragment fragment = new TextFragment();
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.commit();

                bottomNav.setItemSelected(R.id.textualData, true);
            }
            //Swipe Refresher
            pullToRefresh = findViewById(R.id.refresher);
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        refreshData(); // your code
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pullToRefresh.setRefreshing(false);
                }
            });
            //Setting bottom nav card bg

            /*fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,new TextFragment())
                    .commit();

            bottomNav.setItemSelected(R.id.textualData,true);*/


            bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

                @Override
                public void onItemSelected(int id) {
                    Fragment fragment = null;
                    switch (id) {
                        case R.id.textualData:
                            pullToRefresh.setEnabled(true);
                            fragment = new TextFragment();
                            break;
                        case R.id.graphicalData:
                            pullToRefresh.setEnabled(false);
                            fragment = new GraphFragment();
                            break;
                        case R.id.safetyMeasures:
                            pullToRefresh.setEnabled(false);
                            fragment = new SafetyFragment();
                            break;
                        case R.id.aboutData:
                            pullToRefresh.setEnabled(false);
                            fragment = new AboutFragment();
                            break;
                    }
                    if (fragment != null) {
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.fragment_container, fragment)
                                .commit();
                    } else {
                        Log.e("TAG", "Error In creating fragment ");
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
            });
        }
    }

    public void refreshData() throws Exception {
     /*   new TextFragment().updateStatusflag=true;
        Fragment fragment = new TextFragment();
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.fragment_container,fragment);
        tr.commit();*/

        int currTime = Integer.parseInt(TextFragment.getCurrentTime());
        int upTime = Integer.parseInt(TextFragment.getUpdatedTime());
        Log.d("TIME_TAG", "Current Time: " + currTime + " Updated time: " + upTime);

        if (!isNetworkAvailable(MainActivity.this)) {
            //Toast.makeText(MainActivity.this,"No Internet Access !!",Toast.LENGTH_SHORT).show();
            dialogBuilder(MainActivity.this).show();
            pullToRefresh.setRefreshing(false);
        } else {
            FetchData f = new FetchData();
            TastyToast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT, TastyToast.DEFAULT).show();
            //f.getData();
            f.execute();
        }
    }
}
