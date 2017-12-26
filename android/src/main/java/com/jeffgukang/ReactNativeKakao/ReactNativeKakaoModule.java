package com.jeffgukang.ReactNativeKakao;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableNativeArray;
import com.jeffgukang.ReactNativeKakao.ReactNativeKakaoLogin;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;


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
    kakaoLogin.login(promise);
  }

  @ReactMethod
  public void logout(Promise promise) {
    kakaoLogin.logout(promise);
  }
}
