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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.StringReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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
        recyclerView = findViewById(R.id.recyclerView);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new DriverStandingAdapter(driverStandings, this);
            recyclerView.setAdapter(adapter);
        }

        if (lastUpdated == null) {
            Log.e("MainActivity", "lastUpdated TextView is null");
        }

        startButton.setOnClickListener(this);

        // NavigationView Listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_drivers) {
            // Load the Drivers page
        } else if (id == R.id.nav_schedule) {
            // Load the Schedule page
        } else if (id == R.id.nav_update) {
            // Trigger an update
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_drivers:
                // Show detailed driver information
                return true;
            case R.id.action_schedule:
                // Show race schedule
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        startProgress();
    }

    public void startProgress() {
        String urlSource = "http://ergast.com/api/f1/current/driverStandings";
        new Thread(new Task(urlSource)).start();
    }

    private class Task implements Runnable {
        private final String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {
            try {
                URL aurl = new URL(url);
                String response = NetworkUtils.getResponseFromHttpUrl(aurl);

                runOnUiThread(() -> {
                    try {
                        parseDriverStandings(response);
                        updateLastUpdated();
                        displayDriverStandings();
                    } catch (Exception e) {
                        Log.e("MainActivity", "Error while updating UI", e);
                    }
                });
            } catch (Exception e) {
                Log.e("MainActivity", "Error while updating UI", e);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void parseDriverStandings(String xmlData) throws Exception {
        XmlPullParser parser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(new StringReader(xmlData));
        int eventType = parser.getEventType();
        driverStandings.clear();

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

                Driver driver = new Driver(givenName, familyName);
                DriverStanding standing = new DriverStanding(position, points, wins, driver);
                driverStandings.add(standing);
            }
            eventType = parser.next();
        }

        rawDataDisplay.setText("Data Parsed");
    }

    @SuppressLint("NotifyDataSetChanged")
    private void displayDriverStandings() {
        if (adapter == null) {
            adapter = new DriverStandingAdapter(driverStandings);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @SuppressLint("SetTextI18n")
    private void updateLastUpdated() {
        TextView lastUpdatedTextView = findViewById(R.id.lastUpdated);
        if (lastUpdatedTextView != null) {
            lastUpdatedTextView.setText("Last updated: " + getCurrentTime());
        } else {
            Log.e("MainActivity", "TextView is null!");
        }
    }
}

// S2339266