package app;

/**
 * <code>Location</code> - simple class describing location in 2-dimensional space.
 */
public class Location {

    public int x;
    public int y;
/**
 * Simple constructor for class Location
 * @param X 
 * @param Y
 */
    public Location(int X, int Y) {
        x = X;
        y = Y;
    }
/**
 * Compares two Locations and returns <code>true<code/> when they describe the same place
 * @return boolean
 */
    public boolean equal(Location loc2) {
        if (x == loc2.x && y == loc2.y)
            return true;
        else
            return false;
    }
}