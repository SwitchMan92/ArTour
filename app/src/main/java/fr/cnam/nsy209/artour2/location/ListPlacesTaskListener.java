package fr.cnam.nsy209.artour2.location;

import java.util.List;

import fr.cnam.nsy209.artour2.location.data.Place;

public interface ListPlacesTaskListener {
    public void onPostExecute(List<Place> p_Places);
}
