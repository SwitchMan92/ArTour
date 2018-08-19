package fr.cnam.nsy209.artour2.location;

import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import fr.cnam.nsy209.artour2.location.data.IPlacesService;
import fr.cnam.nsy209.artour2.location.data.Place;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListPlacesTask extends AsyncTask<String,Void,List<Place>> {

        private ListPlacesTaskListener m_Listener;

        public ListPlacesTask(ListPlacesTaskListener p_Listener) {
            this.m_Listener = p_Listener;
        }

        @Override
        protected List<Place> doInBackground(String... params) {

        IPlacesService placesService = new Retrofit.Builder()
                .baseUrl(IPlacesService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IPlacesService.class);

            try {
                List<Place> placesList = placesService.listPlaces(params[0], params[1]).execute().body();
                return placesList;
            }
            catch (Exception e) {
                Log.e("IPlacesService", e.toString());
                return new ArrayList<Place>();
            }
        }

        @Override
        protected void onPostExecute(List<Place> p_Places) {
        super.onPostExecute(p_Places);

        this.m_Listener.onPostExecute(p_Places);
        /*
            for (Place l_Place: p_Places) {
                Log.d("IPlacesService", l_Place.name);
            }*/
        }
    }
