package org.me.gcu.hewitt_gary_s2339266;

public class DriverStanding {
    private int position;
    private int points;
    private int wins;
    private Driver driver;

    public DriverStanding(int position, int points, int wins, Driver driver) {
        this.position = position;
        this.points = points;
        this.wins = wins;
        this.driver = driver;
    }

    public int getPosition() {
        return position;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public Driver getDriver() {
        return driver;
    }
}
//s2339266