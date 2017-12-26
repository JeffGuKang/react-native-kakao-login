package com.jeffgukang.ReactNativeKakao;

/**
 * Created by jeffkang on 12/26/17.
 */


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

import static com.kakao.usermgmt.UserManagement.requestMe;


//import com.kakao.auth.ErrorResult;

public class ReactNativeKakaoLogin {
    private static final String LOG_TAG = "ReactKakaoLogin";
    private final ReactApplicationContext reactApplicationContext;
    private Activity currentActivity;
    private SessionCallback sessionCallback;
    private boolean init = false;

    public ReactNativeKakaoLogin(ReactApplicationContext context) {
        Log.v(LOG_TAG, "kakao : initialize");
        this.reactApplicationContext = context;


    }

    private void initialize(){
        if(!init){
            currentActivity = reactApplicationContext.getCurrentActivity();
            init = true;
            try {
                KakaoSDK.init(new KakaoSDKAdapter(currentActivity));
            }catch(RuntimeException e){
                Log.e("kakao init error", "error", e);
            }

        }
    }

    /**
     * Log in
     */
    public void login(Promise promise) {
        initialize();
        this.sessionCallback = new SessionCallback(promise);
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().open(AuthType.KAKAO_TALK, currentActivity);
    }

    /**
     * Log out
     */
    public void logout(final Promise promise) {
        initialize();
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
            }
        });
        WritableMap response = Arguments.createMap();
        response.putString("success", "true");
        promise.resolve(response);
    }

    /**
     * Result
     * @param userProfile
     */
    private WritableMap convertMapUserProfile(UserProfile userProfile) {
        Log.v(LOG_TAG, "kakao : handleResult");
        WritableMap response = Arguments.createMap();

        response.putString("id", String.valueOf(userProfile.getId()));
        response.putString("nickName", userProfile.getNickname());
        response.putString("profileImage", userProfile.getProfileImagePath());
        response.putString("profileImageThumnail", userProfile.getThumbnailImagePath());
        response.putString("email", userProfile.getEmail());
        response.putString("accessToken", Session.getCurrentSession().getAccessToken());

        return response;
    }

    /**
     * Class SessonCallback
     */
    private class SessionCallback implements ISessionCallback {
        private final Promise promise;

        public SessionCallback(Promise promise) {
            this.promise = promise;
        }

        @Override
        public void onSessionOpened() {
            Log.v(LOG_TAG, "kakao : SessionCallback.onSessionOpened");
            List<String> propertyKeys = new ArrayList<String>();
            propertyKeys.add("kaccount_email");
            propertyKeys.add("nickname");
            propertyKeys.add("profile_image");
            propertyKeys.add("thumbnail_image");

            requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    removeCallback();
                    promise.reject("onFaileure", "로그인 실패");
//                    callbackContext.error("kakao : SessionCallback.onSessionOpened.requestMe.onFailure - " + errorResult);
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.v(LOG_TAG, "kakao : SessionCallback.onSessionOpened.requestMe.onSessionClosed - " + errorResult);
                    Session.getCurrentSession().checkAndImplicitOpen();
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    removeCallback();
                    WritableMap userMap = convertMapUserProfile(userProfile);
//                    Log.d("userMap::::", userMap.toString());
                    promise.resolve(userMap);
                }



                @Override
                public void onNotSignedUp() {
                    removeCallback();
                    promise.reject("onNotSignedUp", "로그인 취소");
//                    callbackContext.error("this user is not signed up");
                }
                private void removeCallback(){
                    Session.getCurrentSession().removeCallback(sessionCallback);
                }
            }, propertyKeys, false);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.v(LOG_TAG, "kakao : onSessionOpenFailed" + exception.toString());
            }
        }
    }


    /**
     * Return current activity
     */
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Set current activity
     */
    public void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    /**
     * Class KakaoSDKAdapter
     */
    private static class KakaoSDKAdapter extends KakaoAdapter {

        private final Activity currentActivity;

        public KakaoSDKAdapter(Activity activity) {
            this.currentActivity = activity;
        }

        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_TALK};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return false;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return currentActivity.getApplicationContext();
                }
            };
        }
    }

}
