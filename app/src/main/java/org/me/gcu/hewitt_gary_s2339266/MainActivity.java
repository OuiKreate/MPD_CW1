/*  Starter project for Mobile Platform Development in main diet 2023/2024
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Gary Hewitt
// Student ID           S2339266
// Programme of Study   Mobile Platform Development
//

// UPDATE THE PACKAGE NAME to include your Student Identifier
package org.me.gcu.hewitt_gary_s2339266;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;

import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.StringReader;
import java.net.URL;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    private TextView rawDataDisplay;
    private RecyclerView recyclerView;
    private DriverStandingAdapter adapter;
    private final List<DriverStanding> driverStandings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView lastUpdated = findViewById(R.id.lastUpdated);
        Button startButton = findViewById(R.id.startButton);
        rawDataDisplay = findViewById(R.id.rawDataDisplay);
        recyclerView = findViewById(R.id.recyclerView); //s2339266

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));//s2339266
            adapter = new DriverStandingAdapter(driverStandings);//s2339266
            recyclerView.setAdapter(adapter);//s2339266


            if (lastUpdated == null) {  //s2339266 testing for error as no connect
                Log.e("MainActivity", "lastUpdated TextView is null");
            }
            startButton.setOnClickListener(this);
            Log.d("MainActivity", "Landscape layout loaded"); //test log
        }

    }


    public void onClick(View v) {

        startProgress();
    }

    public void startProgress() {
        String urlSource = "http://ergast.com/api/f1/current/driverStandings";
        new Thread(new Task(urlSource)).start();  //S2339266
    }

    private class Task implements Runnable {
        private final String url;

        public Task(String aurl) {

            url = aurl;
        }

        @Override
        public void run() { //s2339266
            try {
                URL aurl = new URL(url);//s2339266
                String response = NetworkUtils.getResponseFromHttpUrl(aurl);//s2339266

                runOnUiThread(() -> {//s2339266
                    try {//s2339266
                        parseDriverStandings(response);//s2339266
                        updateLastUpdated();//s2339266
                        displayDriverStandings(); //Display Driver standings in recycler
                    } catch (Exception e) {//s2339266
                        Log.e("MainActivity", "Error while updating UI", e);//s2339266
                    }
                });
            } catch (Exception e) {//s2339266
                Log.e("MainActivity", "Error while updating UI", e);//s2339266
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void parseDriverStandings(String xmlData) throws Exception {
        XmlPullParser parser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(new StringReader(xmlData));
        int eventType = parser.getEventType();
        driverStandings.clear();// clear list //s2339266

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equals("DriverStanding")) {
                int position = Integer.parseInt(parser.getAttributeValue(null, "position"));
                int points = Integer.parseInt(parser.getAttributeValue(null, "points"));
                int wins = Integer.parseInt(parser.getAttributeValue(null, "wins"));

                parser.nextTag(); // Skip to Driver tag
                parser.nextTag(); // Skip to GivenName tag
                String givenName = parser.nextText();
                parser.nextTag(); // Skip to FamilyName tag
                String familyName = parser.nextText();

                Driver driver = new Driver(givenName, familyName); //s2339266
                DriverStanding standing = new DriverStanding(position, points, wins, driver);
                driverStandings.add(standing);
            }
            eventType = parser.next();
        }

        rawDataDisplay.setText("Data Parsed"); //message
    }

    @SuppressLint("NotifyDataSetChanged")
    private void displayDriverStandings() {
        if (adapter == null) {
            adapter = new DriverStandingAdapter(driverStandings);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged(); //update if data changes //s2339266
        }
    }
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    @SuppressLint("SetTextI18n")
    private void updateLastUpdated() {
        TextView lastUpdatedTextView = findViewById(R.id.lastUpdated); // S2339266
        if (lastUpdatedTextView != null) {
            lastUpdatedTextView.setText("Last updated: " + getCurrentTime());
        } else {
            Log.e("MainActivity", "TextView is null!");
        }
    }
}
// S2339266