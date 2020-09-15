# DEPRECATED

This library has been deprecated and is no longer maintained now. Please use https://github.com/react-native-seoul/react-native-kakao-login instead.

![IMG_9520](https://user-images.githubusercontent.com/216363/93151883-132a7400-f738-11ea-8e05-95a0e905a0f1.GIF)

# React Native Kakao Login(rn-kakao-login)

Android: ![Android](https://build.appcenter.ms/v0.1/apps/6ef44ff8-32de-43c6-8e21-97017bcf405e/branches/master/badge)  
iOS: ![iOS](https://build.appcenter.ms/v0.1/apps/a4726ca7-bf3e-46d0-80cc-b2c960ce984d/branches/master/badge)

<img src="./screenshots/main.png" alt="RNKakao" width="200"/>

English Document: [English](./README_en.md)

리엑트 네이티브용 카카오 로그인 라이브러리: rn-kakao-login

안드로이드 >= 4.1
iOS >= 10.0
React Native는 0.60.0 버전 이상에서 테스트되었습니다.

## 소개

카카오 로그인 SDK를 사용한 리엑트 네이티브 카카오 로그인 라이브러리입니다. 

## 설치

rn-kakao-login > 2.0.0, React Native > 0.60.0 에서는 autolinking을 지원하므로 별도의 연결을 하지 않아도 됩니다. 

NPM

```js
npm install --save rn-kakao-login
```

Yarn

```js
yarn add rn-kakao-login
```

### iOS
```
cd ios && pod install
```

### Android

별도의 작업 필요 없음

### 설정

[초기 설정](#프로젝트-셋업-및-초기-설정)

## 예제

ReactNativeKakaoExample 폴더를 참조하세요.

```
cd ReactNativeKakaoExample

npm install
or
yarn
```

## 사용법

```js
import RNKakao from 'rn-kakao-login';
```

### Kakao Login

[Official documentations](https://developers.kakao.com/docs/ios#사용자-관리-로그인).

```js
RNKakao.login()
```

Example

```js
  kakaoLogin = async () => {
    try {
      const result = await RNKakao.login();
      this.setState({
        userInfo: JSON.stringify(result)
      });
    } catch (e) {
      this.setState({
        userInfo: `Error: ${e}`
      });
    }
  }

  kakaoLogout = async () => {
    try {
      const result = await RNKakao.logout();
      this.setState({
        userInfo: JSON.stringify(result)
      });
    } catch (e) {
      this.setState({
        userInfo: `Error: ${e}`
      });
    }
  }

  getUserInfo = async () => {
    try {
      const result = await RNKakao.userInfo();
      this.setState({
        userInfo: JSON.stringify(result)
      });
    } catch (e) {
      this.setState({
        userInfo: `Error: ${e}`
      });
    }
  }
```

#### - 인증 타입

지원예정이며
현재 KakaoTalk 고정입니다.

```js
RNKakao.KOAuthTypeTalk,
RNKakao.KOAuthTypeStory,
RNKakao.KOAuthTypeAccount
```

#### - 유저 정보

로그인 후 받을 수 있는 유저정보입니다. 유저가 수락하지 않은 정보들은 null로 들어옵니다.

```js
  {
    id: <user id>
    accessToken: <needed to access kakao API from the application>
    nickname: <user nickname> // nullable
    email: <user email> // nullable
    profileImage: <user picture profile url> // nullable
    profileImageThumbnail: <user picture profile thumbnail url> // nullable
    ageRange: <user age range> // nullable
    gender: <user gender> // nullable
  }
```

## 프로젝트 셋업 및 초기 설정

### iOS

[Official Kakao](https://developers.kakao.com/docs/ios#시작하기-개발환경)

- 카카오SDK 인스톨

  version > 2.0 에서는 자동으로 인스톨하므로 별도의 프레임워크 등록 과정이 필요 없습니다. 

  1. 빌드 설정 추가 `-all_load` in `Other Linker Flags`.

      ![argument](https://developers.kakao.com/assets/images/ios/other_linker_flags.png)

- 카카오에 앱 등록 [Official](https://developers.kakao.com/docs/ios#시작하기-앱-생성)

  1. 새로운 앱 만들기 [Make new app](https://developers.kakao.com/apps/new)

      ![makeapp](https://developers.kakao.com/assets/images/dashboard/dev_017.png)

  2. iOS 플랫폼 추가

      ![addios](https://developers.kakao.com/assets/images/dashboard/dev_018.png)

     iOS bundle id must same with XCode project's Bundle Identifier.

- 프로젝트 앱 설정

공식 설정 적용
[Kakao SDK](https://developers.kakao.com/docs/ios#%EA%B0%9C%EB%B0%9C%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EC%A0%95) 

  1. URL types 추가

      Add `kakao<yourappId>` in URL Schemes
      ![url types](https://developers.kakao.com/assets/images/ios/url_types.png)

  2. plist에 네이티브 앱 키 추가

      ![addkakaoid](https://developers.kakao.com/assets/images/ios/setting_plist.png)

- `AppDelegate.m`에 코드 추가

```js
  #import <KakaoOpenSDK/KakaoOpenSDK.h>
  
  ...
  
  
  - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
                                         sourceApplication:(NSString *)sourceApplication
                                                annotation:(id)annotation {
      ...
      if ([KOSession isKakaoAccountLoginCallback:url]) {
          return [KOSession handleOpenURL:url];
      }

      return NO;
  }

  - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
                                                   options:(NSDictionary<NSString *,id> *)options {
      ...
      if ([KOSession isKakaoAccountLoginCallback:url]) {
          return [KOSession handleOpenURL:url];
      }

      return NO;
  }

  - (void)applicationDidBecomeActive:(UIApplication *)application
  {
      [KOSession handleDidBecomeActive];
  }
```

#### Auto Refresh Token(Option)

https://developers.kakao.com/docs/ios/user-management#토큰-주기적-갱신

AppDelegate

```
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    ...
    [KOSession sharedSession].automaticPeriodicRefresh = YES;
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    ...
    [KOSession handleDidEnterBackground];
}
```

### Android

안드로이드 소스는 [helpkang](https://github.com/helpkang/react-native-kakao-login)을 기반으로 만들어졌습니다.

[공식 설정](https://developers.kakao.com/docs/android/getting-started#%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%84%B1)

1. `android/build.gradle`에 maven 추가

```js
allprojects {
    repositories {
        mavenCentral()
        ...
        maven { url 'http://devrepo.kakao.com:8088/nexus/content/groups/public/' } // 추가 
    }
}
```

3. `AndroidManifest.xml`에 앱키 등록.

```xml
<application>
  <meta-data
      android:name="com.kakao.sdk.AppKey"
      android:value="YOUR_APP_KEY" />
      ...
```

`KakaoWebViewActivity` 관련 문제가 발생한다면 아래 코드 역시 추가해주세요.  [#5](https://github.com/JeffGuKang/react-native-kakao/issues/5)

```xml
<activity
    android:name="com.kakao.auth.authorization.authcode.KakaoWebViewActivity"
    android:launchMode="singleTop"
    android:exported="false"
    android:windowSoftInputMode="adjustResize">
</activity>
```

#### Proguard

```
-keep class com.kakao.** { *; }
-keepattributes Signature
-keepclassmembers class * {
  public static <fields>;
  public *;
}
-dontwarn android.support.v4.**,org.slf4j.**,com.google.android.gms.**
```
#### 키 해쉬
테스트를 위해 개발환경의 키 해쉬를 등록해야합니다. [공식문서](https://developers.kakao.com/docs/android/getting-started#키해시-등록)
-keystore 옵션에 사용할 debug.keystore 경로를 입력해주세요.

OS X, Linux

android/app 폴더에 debug.keysotre가 있는 경우 (RN > 0.60 자동생성)
default: `Xo8WBi6jzSxKDVR4drqm84yr9iU=`

```sh
keytool -exportcert -alias androiddebugkey -keystore android/app/debug.keystore -storepass android -keypass android | openssl sha1 -binary | openssl base64
```

android/app 폴더에 debug.keysotre가 없는 경우

```js
keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore -storepass android -keypass android | openssl sha1 -binary | openssl base64
```

### TO DO

- [ ] 이용중 동의 dynamic agreement(https://developers.kakao.com/docs/android/user-management#동적동의)
- [v] TypeScript 적용
- [v] Autolinking 적용 

### Troubleshooting

`Example` 폴더를 통해 예제를 먼저 돌려보세요.

#### Android

##### 로그인 후 아무 반응이 없을 때 

키 해쉬가 잘 추가되었는지 확인해주세요.

##### Google Sign In 후 

google console에서 앱 서명란에 들어가 SHA-1 인증서 지문을 구글에서 다운받는다. 

그 후 터미널 등을 통해 base64로 변경 후 카카오에 해쉬 키를 등록하면 된다. 

`echo [SHA-1] | xxd -r -p | openssl base64`



#### IOS

##### Build Error: linker, arm64, x86_64

추가한 KakaoOpenSDK.framewrok 를 눌러 Target Membership 체크가 정상적으로 되어 있는지 확인해주세요.

### Contributors

- [QuadFlask](https://github.com/QuadFlask)
- [JB Paul](https://github.com/yjb94)

## Licence

(MIT)

## References

- gif: [Inuyasa](https://www.viz.com/inuyasha)
