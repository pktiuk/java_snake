package app;

/**
 * Location
 */
public class Location {

    public int x;
    public int y;

    public Location(int X, int Y) {
        x = X;
        y = Y;
    }

    public boolean equal(Location loc2) {
        if (x == loc2.x && y == loc2.y)
            return true;
        else
            return false;
    }
}