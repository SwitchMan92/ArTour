package fr.cnam.nsy209.artour2;

import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

import java.util.ArrayList;
import java.util.List;

import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;
import fr.cnam.nsy209.artour2.location.data.IPlacesService;
import fr.cnam.nsy209.artour2.location.data.Place;
import fr.common.helpers.CameraPermissionHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ng6fd11 on 17/05/2018.
 */

public class OpenGLES20Activity extends AppCompatActivity {

    private MyGLSurfaceView mGLView;
    private Session m_Session;
    private boolean installRequested;
    private MyGLRenderer m_Renderer;


    class ListPlacesTask extends AsyncTask<String,Void,List<Place>> {

        @Override
        protected List<Place> doInBackground(String... params) {

            IPlacesService githubService = new Retrofit.Builder()
                    .baseUrl(IPlacesService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IPlacesService.class);

            String user = params[0];

            try {
                List<Place> placesList = githubService.listPlaces(params[0], params[1]).execute().body();
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

            for (Place l_Place: p_Places) {
                Log.d("IPlacesService", l_Place.getName());
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new ListPlacesTask().execute("48.858093", "2.294694");

        ProgramLoader.setContext(this);

        mGLView = new MyGLSurfaceView(this);
        m_Renderer = new MyGLRenderer(this);
        mGLView.setRenderer(this.m_Renderer);
        this.mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.installRequested = false;
        setContentView(mGLView);
    }

    private void updateSession() {

        if (this.m_Session == null) {
            Exception exception = null;
            String message = null;
            try {
                switch (ArCoreApk.getInstance().requestInstall(this, !installRequested)) {
                    case INSTALL_REQUESTED:
                        installRequested = true;
                        return;
                    case INSTALLED:
                        break;
                }

                if (!CameraPermissionHelper.hasCameraPermission(this)) {
                    CameraPermissionHelper.requestCameraPermission(this);
                    return;
                }

                this.m_Session = new Session(/* context= */ this);
                this.m_Renderer.setSession(this.m_Session);
                this.m_Session.resume();

            } catch (UnavailableArcoreNotInstalledException
                    | UnavailableUserDeclinedInstallationException e) {
                message = "Please install ARCore";
                exception = e;
            } catch (UnavailableApkTooOldException e) {
                message = "Please update ARCore";
                exception = e;
            } catch (UnavailableSdkTooOldException e) {
                message = "Please update this app";
                exception = e;
            } catch (UnavailableDeviceNotCompatibleException e) {
                message = "This device does not support AR";
                exception = e;
            } catch (Exception e) {
                message = "Failed to create AR session";
                exception = e;
            }
            finally {
                if (message != null) {
                    Log.e("error onResume", message);
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateSession();
        this.mGLView.onResume();
        this.m_Renderer.setSession(this.m_Session);
        this.m_Renderer.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.m_Session != null) {
            this.m_Session.pause();
            this.m_Renderer.setSession(this.m_Session);
            this.m_Renderer.onPause();
            this.mGLView.onPause();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                    .show();
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                CameraPermissionHelper.launchPermissionSettings(this);
            }
            finish();
        }
    }

}
