package sportsfight.com.s.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;

/**
 * Created by Ashish.Kumar on 02-02-2018.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    AppController controller;
    @Override
    public void onTokenRefresh() {
        controller=(AppController) getApplicationContext();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());
        registerToken(token);
    }

    private void registerToken(String token) {
        Common.FCMToken=token;
        controller.getPrefManager().setFcmToken(token);

    }

}

