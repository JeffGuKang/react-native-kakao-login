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
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

//import com.kakao.auth.ErrorResult;

public class ReactNativeKakaoLogin {
    private static final String LOG_TAG = "ReactNativeKakao";
    private final ReactApplicationContext reactApplicationContext;
    private Activity currentActivity;
    private SessionCallback sessionCallback;
    private boolean init = false;

    public ReactNativeKakaoLogin(ReactApplicationContext context) {
        this.reactApplicationContext = context;
    }

    public void initialize(){
        if(!init){
            Log.v(LOG_TAG, "kakao : initialize");
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
        Session.getCurrentSession().clearCallbacks();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().open(AuthType.KAKAO_TALK, currentActivity); // KAKAO_ACCOUNT 정상 동작 확인
    }

    /**
     * Log in with all types
     */
    public void loginWithAllTypes(Promise promise) {
        initialize();
        this.sessionCallback = new SessionCallback(promise);
        Session.getCurrentSession().clearCallbacks();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, currentActivity);
    }

    /**
     * Log out
     */
    public void logout(final Promise promise) {
        Log.d(LOG_TAG, "Logout");
        initialize();
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            WritableMap response = Arguments.createMap();

            @Override
            public void onCompleteLogout() {
                response.putString("success", "Logged out");
                promise.resolve(response);
            }
        });

    }

    /**
     * Convert to Json response
     * https://developers.kakao.com/docs/android/user-management#사용자-정보-요청
     * @param userProfile
     */
    private WritableMap convertMapUserProfile(MeV2Response userProfile) {
        Log.v(LOG_TAG, "kakao : handleResult");
        WritableMap response = Arguments.createMap();

        UserAccount account = userProfile.getKakaoAccount();

        response.putDouble("id", userProfile.getId());
        response.putString("accessToken", Session.getCurrentSession().getAccessToken());
        response.putString("nickname", userProfile.getNickname());
        response.putString("profileImage", userProfile.getProfileImagePath());
        response.putString("profileImageThumbnail", userProfile.getThumbnailImagePath());
//        response.putString("properties", String.valueOf(userProfile.getProperties()));

        if (account != null) {
            if (account.getEmail() != null) {
                response.putString("email", account.getEmail());
            }
            if (account.getGender() != null) {
                response.putString("gender", String.valueOf(account.getGender()));
            }
            if (account.getAgeRange() != null) {
                response.putString("ageRange", String.valueOf(account.getAgeRange()));
            }
            if (account.getBirthday() != null) {
                response.putString("birthday", account.getBirthday());
            }
        }

        return response;
    }

    /**
     * Get signed user information
     */
    public void userInfo(final Promise promise) {
        initialize();

        Log.d(LOG_TAG, "userInfo");
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("properties.thumbnail_image");
        keys.add("kakao_account.email");
        keys.add("kakao_account.age_range");
        keys.add("kakao_account.birthday");
        keys.add("kakao_account.gender");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                promise.reject("onFailure", errorResult.toString());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                promise.reject("onNotSignedUp", errorResult.toString());
            }

            @Override
            public void onSuccess(MeV2Response response) {
                WritableMap userMap = convertMapUserProfile(response);
                promise.resolve(userMap);
            }
        });
    }

    /**
     * Class SessonCallback
     * https://developers.kakao.com/docs/android/user-management#사용자-정보-요청
     */
    private class SessionCallback implements ISessionCallback {
        private final Promise promise;

        public SessionCallback(Promise promise) {
            this.promise = promise;
        }

        @Override
        public void onSessionOpened() {
            Log.v(LOG_TAG, "kakao : SessionCallback.onSessionOpened");
            ReactNativeKakaoLogin.this.userInfo(this.promise);
            // call userInfo()
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.v(LOG_TAG, "kakao : onSessionOpenFailed: " + exception.toString());

                this.promise.reject("onSessionOpenFailed", exception.getMessage(), exception);
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

                // 로그인시 인증받을 타입을 지정한다. 지정하지 않을 시 가능한 모든 옵션이 지정된다.
                 @Override
                 public AuthType[] getAuthTypes() {
                     return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
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
