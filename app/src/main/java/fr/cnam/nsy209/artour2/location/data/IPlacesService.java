package fr.cnam.nsy209.artour2.location.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface IPlacesService {

    public static final String ENDPOINT = "http://10.0.2.2:5000";

    @GET("/google_api/{longitude}&{latitude}")
    Call<List<Place>> listPlaces(@Path("latitude") String p_Latitude, @Path("longitude") String p_Longitude);
}
