package org.me.gcu.hewitt_gary_s2339266;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


    public class DriverDetailActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_driver_detail); // You need to create this layout

            // Retrieve the data from the Intent
            String driverName = getIntent().getStringExtra("driver_name");
            String dateOfBirth = getIntent().getStringExtra("date_of_birth");
            String nationality = getIntent().getStringExtra("nationality");
            String constructor = getIntent().getStringExtra("constructor");

            // Find the TextViews in the layout
            TextView nameTextView = findViewById(R.id.driverNameTextView);
            TextView dobTextView = findViewById(R.id.dateOfBirthTextView);
            TextView nationalityTextView = findViewById(R.id.nationalityTextView);
            TextView constructorTextView = findViewById(R.id.constructorTextView);

            // Set the data to the TextViews
            nameTextView.setText(driverName);
            dobTextView.setText(dateOfBirth);
            nationalityTextView.setText(nationality);
            constructorTextView.setText(constructor);
        }
    }

//S2339266
