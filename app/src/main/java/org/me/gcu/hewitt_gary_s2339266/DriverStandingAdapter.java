package org.me.gcu.hewitt_gary_s2339266;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView; // Import this for RecyclerView
import java.util.List;

public class DriverStandingAdapter extends RecyclerView.Adapter<DriverStandingAdapter.ViewHolder> {

    private List<DriverStanding> driverStandings;

    public DriverStandingAdapter(List<DriverStanding> driverStandings) {
        this.driverStandings = driverStandings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_standing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DriverStanding standing = driverStandings.get(position);
        holder.positionTextView.setText(String.valueOf(standing.getPosition()));
        holder.nameTextView.setText(standing.getDriver().getGivenName() + " " + standing.getDriver().getFamilyName());
        holder.pointsTextView.setText(String.valueOf(standing.getPoints()));
        holder.winsTextView.setText(String.valueOf(standing.getWins()));
    }

    @Override
    public int getItemCount() {
        return driverStandings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView positionTextView;
        public TextView nameTextView;
        public TextView pointsTextView;
        public TextView winsTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            positionTextView = itemView.findViewById(R.id.positionTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            pointsTextView = itemView.findViewById(R.id.pointsTextView);
            winsTextView = itemView.findViewById(R.id.winsTextView);
        }
    }
}
