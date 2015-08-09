package com.traylz.trailmix;

import java.util.ArrayList;

/**
 * Two ArrayLists consisting of doubles to make up the pairs of latitude and longitude points.
 * @author Chris Wang
 */
public class CoordArray {

    public static CoordArray testArray = new CoordArray();

    public ArrayList<Double> lats = new ArrayList<>();
    public ArrayList<Double> lngs = new ArrayList<>();

    public synchronized void addCoordinate(double lat, double lng) {
        lats.add(lat);
        lngs.add(lng);
    }

    public synchronized int size() {
        return lats.size();
    }

    /**
     * Gets 2D double array representing the coordinates of the trail.
     * @return avgcoords
     */
    public synchronized double[][] getCoordinates() {
        int len = lats.size();
        double[][] toReturn = new double[len][2];
        for (int i = 0; i < len; i++) {
            toReturn[i][0] = lats.get(i);
            toReturn[i][1] = lngs.get(i);
        }
        return toReturn;
    }

    public synchronized double[] getAvg() {
        int len = lats.size();
        if (len == 0)
            return new double[]{0D, 0D};
        double lat = 0;
        double lng = 0;
        for (int i = 0; i < len; i++) {
            lat += lats.get(i);
            lng += lngs.get(i);
        }
        lat /= (double) len;
        lng /= (double) len;
        return new double[]{lat, lng};
    }

    /**
     * Clears the entire array of lats and longs.
     */
    public synchronized void clear() {
        lats.clear();
        lngs.clear();
    }

    /**
     * Simple distance formula.
     * @param d1 lat
     * @param d2 long
     * @param d3 lat
     * @param d4 long
     * @return dist
     */
    public static double dist(double d1, double d2, double d3, double d4) {
        return Math.sqrt(Math.pow(d1 - d3, 2.0) + Math.pow(d2 - d4, 2.0));
    }
}
