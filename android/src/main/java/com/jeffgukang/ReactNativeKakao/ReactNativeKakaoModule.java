package com.jeffgukang.ReactNativeKakao;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;


public class ReactNativeKakaoModule extends ReactContextBaseJavaModule {

  private static final String MODULE_NAME = "ReactNativeKakao";
  private static final Integer KOAuthTypeTalk = 0;
  private static final Integer KOAuthTypeStory = 1;
  private static final Integer KOAuthTypeAccount = 2;

  private ReactNativeKakaoLogin kakaoLogin;

  public ReactNativeKakaoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    if( this.kakaoLogin != null) return;
    this.kakaoLogin= new ReactNativeKakaoLogin(reactContext);
  }

  @Override
  public String getName() {
    return MODULE_NAME;
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("KOAuthTypeTalk", KOAuthTypeTalk);
    constants.put("KOAuthTypeStory", KOAuthTypeStory);
    constants.put("KOAuthTypeAccount", KOAuthTypeAccount);
    return constants;
  }

  @ReactMethod
  public void login(Promise promise) {
    Log.d(MODULE_NAME, "Login");
    kakaoLogin.login(promise);
  }

  @ReactMethod
  public void logout(Promise promise) {
    kakaoLogin.logout(promise);
  }

  @ReactMethod
  public void userInfo(Promise promise) {
    kakaoLogin.userInfo(promise);
  }

}
