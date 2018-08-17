package fr.cnam.nsy209.artour2.location.data;

import fr.cnam.nsy209.artour2.location.GeoPoint;

public class Place {
    public String       name;
    public String       details;
    public double       latitude;
    public double       longitude;

    //private GeoPoint    m_Location;

    /*
    public String getName       ()      { return this.m_Name; }
    public String getDetails    ()      { return this.m_Details; }
    public GeoPoint getLocation ()      { return this.m_Location; }

    public void setName(String p_Name) {
        this.m_Name = p_Name;
    }

    public void setDetails(String p_Details) {
        this.m_Details = p_Details;
    }

    public void setLocation(double p_Latitude, double p_Longitude) throws Exception {
        this.m_Location = new GeoPoint(p_Latitude, p_Longitude);
    }

    public void setLocation(GeoPoint p_Location) {
        this.m_Location = p_Location;
    }
    */
}
