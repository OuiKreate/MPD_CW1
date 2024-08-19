package org.me.gcu.hewitt_gary_s2339266;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DriverStandingAdapter extends RecyclerView.Adapter<DriverStandingAdapter.ViewHolder> {

    private final List<DriverStanding> driverStandings;
    private final Context context; // Store context as a member variable

    public DriverStandingAdapter(List<DriverStanding> driverStandings, Context context) {
        this.driverStandings = driverStandings;
        this.context = context; // Initialize context in the constructor
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.driver_standing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DriverStanding standing = driverStandings.get(position);
        holder.driverName.setText(standing.getDriver().getFullName());
        holder.driverPoints.setText(String.valueOf(standing.getPoints()));
        holder.driverWins.setText(String.valueOf(standing.getWins()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DriverDetailActivity.class);
            intent.putExtra("driver_name", standing.getDriver().getFullName());
            intent.putExtra("date_of_birth", standing.getDriver().getDateOfBirth());
            intent.putExtra("nationality", standing.getDriver().getNationality());
            intent.putExtra("constructor", standing.getDriver().getConstructor());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return driverStandings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView driverName;
        TextView driverPoints;
        TextView driverWins;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driverName);
            driverPoints = itemView.findViewById(R.id.driverPoints);
            driverWins = itemView.findViewById(R.id.driverWins);
        }
    }
}
//S2339266