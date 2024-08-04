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


import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.text.SimpleDateFormat; // Import SimpleDateFormat
import java.util.Date;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private TextView lastUpdated;
    private Button startButton;
    private String result;
    private String urlSource="http://ergast.com/api/f1/current/driverStandings";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        lastUpdated = findViewById(R.id.lastUpdated); //s2339266
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        startProgress();
    }

    public void startProgress(){
        new Thread(new Task(urlSource)).start();  //S2339266
    }
    private class Task implements Runnable {
        private String url;

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
                    } catch (Exception e) {//s2339266
                        e.printStackTrace();//s2339266
                    }
                });
            } catch (Exception e) {//s2339266
                e.printStackTrace();//s2339266
            }
        }
    }


    private void parseDriverStandings(String xmlData) throws Exception {
        XmlPullParser parser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(new StringReader(xmlData));
        int eventType = parser.getEventType();
        StringBuilder standings = new StringBuilder();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equals("DriverStanding")) {
                String position = parser.getAttributeValue(null, "position");
                String points = parser.getAttributeValue(null, "points");
                String wins = parser.getAttributeValue(null, "wins");

                parser.nextTag(); // Skip to Driver tag
                parser.nextTag(); // Skip to GivenName tag
                String givenName = parser.nextText();
                parser.nextTag(); // Skip to FamilyName tag
                String familyName = parser.nextText();

                standings.append(position).append(" - ").append(givenName).append(" ").append(familyName)
                        .append(" (Points: ").append(points)
                        .append(", Wins: ").append(wins).append(")\n");
            }
            eventType = parser.next();
        }

        rawDataDisplay.setText(standings.toString());
    }

    private void updateLastUpdated() {
        String lastUpdatedText = "Last Updated: " + new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a", Locale.getDefault()).format(new Date());
        lastUpdated.setText(lastUpdatedText);
    }
}
// S2339266