package fr.cnam.nsy209.artour2.location;


import com.google.ar.core.Pose;

import java.util.Locale;

public class GeoPoint {

    public static double LATITUDE_LENGTH    = 110570;
    public static double LONGITUDE_LENGTH   = 111320;

    private double m_Latitude;
    private double m_Longitude;

    private static String EXCEPTION_TAG = "GeoPoint";

    public double getLatitude   () { return this.m_Latitude; }
    public double getLongitude  () { return this.m_Longitude;}

    public void setLatitude(double p_Latitude) throws Exception {
        if (p_Latitude > 90F || p_Latitude < -90F)
            throw new Exception(String.format(Locale.getDefault(),
                    "%s - Bad latitude passed as argument to constructor -> latitude: %d",
                    EXCEPTION_TAG, p_Latitude));
    }

    public void setLongitude(double p_Longitude) throws Exception {
        if (p_Longitude > 90F || p_Longitude < -90F)
            throw new Exception(String.format(Locale.getDefault(),
                    "%s - Bad longitude passed as argument to constructor -> latitude: %d",
                    EXCEPTION_TAG, p_Longitude));
    }

    public GeoPoint(double p_Latitude, double p_Longitude) throws Exception {
        this.setLatitude(p_Latitude);
        this.setLongitude(p_Longitude);
    }

    public GeoPoint subtract(GeoPoint p_Operand) throws Exception {
        return new GeoPoint(p_Operand.getLatitude() - this.getLatitude(), p_Operand.getLongitude() - this.getLongitude());
    }

    public GeoPoint add(GeoPoint p_Operand) throws Exception {
        return new GeoPoint(p_Operand.getLatitude() + this.getLatitude(), p_Operand.getLongitude() + this.getLongitude());
    }

    public Pose getRelativePose() throws Exception {
        float l_Latitude = (float)(LATITUDE_LENGTH * this.getLatitude());
        float l_Longitude = (float)(LONGITUDE_LENGTH * this.getLongitude() * Math.cos(l_Latitude));
        return new Pose(new float[]{l_Latitude, l_Longitude, 0f}, new float[]{0f, 0f, 0f});
    }

    /*
    public GeoPoint getRelativeGeoPoint(Pose p_Pose) {
        double l_X = p_Pose.qx();
        double l_Y = p_Pose.qy();
        double l_Z = p_Pose.qz();
    }*/

}
