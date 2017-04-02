package rajeevpc.firebaseapplication;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by RajeevPC on 4/2/2017.
 */

public class FirebaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
