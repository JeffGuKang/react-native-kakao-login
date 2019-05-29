# React Native Kakao

<img src="./screenshots/main.png" alt="RNKakao" width="200"/>

Supported operating systems are >= Android 4.1 (API 16) and >= iOS 10.0.
Tested React Native 0.57v

한글 문서: [Korean(한글) Document](./README_kor.md)


## Introduction

React Native module for using KakaoTalk login sdk.

## Installation

Auto install is supported by npm.

```js
npm install --save react-native-kakao
react-native link react-native-kakao
```

Yarn

```js
yarn add react-native-kakao
react-native link react-native-kakao
```

## Example

Refer to ReactNativeKakaoExample.

```
cd ReactNativeKakaoExample

npm install
or
yarn
```

## Public APIs

```js
import RNKakao from 'react-native-kakao';
```

### Kakao Login

[Official documentations](https://developers.kakao.com/docs/ios#사용자-관리-로그인).

```js
RNKakao.login(authTypes)
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

#### - Auth Types

Support types of kakao login.

```js
RNKakao.KOAuthTypeTalk,
RNKakao.KOAuthTypeStory,
RNKakao.KOAuthTypeAccount
```

#### - User object

This is the typical information you obtain once the user sign in:

```js
{
  id: <user id>
  accessToken: <needed to access kakao API from the application>
  nickname: <user nickname> // nullable
  email: <user email> // nullable
  profileImage: <user picture profile url> // nullable
  profileImageThumnail: <user picture profile thumnail url> // nullable
  ageRange: <user age range> // nullable
  gender: <user gender> // nullable
}
```

## Project setup and initialization

### iOS

[Officail Kakao](https://developers.kakao.com/docs/ios#시작하기-개발환경)

#### Install Kakao SDK

  1. Download [latest SDK](https://developers.kakao.com/sdk/latest-ios-sdk)

  2. Drag and drop framework.

      ![Drag&Drop](https://developers.kakao.com/assets/images/ios/drag_sdk.png)
      ![Settings](https://developers.kakao.com/assets/images/ios/drag_sdk_dialog.png)

  3. Check target settings

      ![build phase](https://developers.kakao.com/assets/images/ios/link_binary_with_libraries_confirm.png)

  4. Add a argument `-all_load` in `Other Linker Flags`.
      ![argument](https://developers.kakao.com/assets/images/ios/other_linker_flags.png)

#### Register your application in Kakao. [Official](https://developers.kakao.com/docs/ios#시작하기-앱-생성)

  1. Make new app [Official](https://developers.kakao.com/apps/new)

      ![makeapp](https://developers.kakao.com/assets/images/dashboard/dev_017.png)

  2. Add iOS platform

      ![addios](https://developers.kakao.com/assets/images/dashboard/dev_018.png)

      iOS bundle id must same with XCode project's Bundle Identifier.

#### App settings in project

  1. Add URL types

      Add `kakao<yourappId>` in URL Schemes
      ![url types](https://developers.kakao.com/assets/images/ios/url_types.png)

  2. Add native app key in plist

      ![addkakaoid](https://developers.kakao.com/assets/images/ios/setting_plist.png)

#### Add codes to `AppDelegate.m`

```js
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

### Android

Android is made based on [helpkang's source](https://github.com/helpkang/react-native-kakao-login)

[Official](https://developers.kakao.com/docs/android/getting-started#%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%84%B1)

#### 1. Add maven to `android/build.gradle`.

```js
subprojects {
    repositories {
        mavenCentral()
        maven { url 'http://devrepo.kakao.com:8088/nexus/content/groups/public/' }
    }
}
```

#### 2. Add dependencies to `android/app/build.gradle`.

It can be `compile` instead of `implementation` in gradle of low version.

```js
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation "com.android.support:appcompat-v7:28.0.0"
    implementation "com.facebook.react:react-native:+"
    // From node_modules
    implementation project(':react-native-kakao') // Check this line.
}
```

Add your AppKey in `AndroidManifest.xml`.

```xml
<application>
  <meta-data
      android:name="com.kakao.sdk.AppKey"
      android:value="YOUR_APP_KEY" />
      ...
```

Additionally,  to add activity `KakaoWebViewActivity` could be needed. [#5](https://github.com/JeffGuKang/react-native-kakao/issues/5)

```xml
<activity
    android:name="com.kakao.auth.authorization.authcode.KakaoWebViewActivity"
    android:launchMode="singleTop"
    android:exported="false"
    android:windowSoftInputMode="adjustResize">
</activity>
```

`settings.gradle` will be set automatically.

```js
include ':react-native-kakao'
project(':react-native-kakao').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-kakao/android')
```

#### Key hash
Do not forget adding debug or release key hash for test. [Official](https://developers.kakao.com/docs/android/getting-started#키해시-등록)

OS X, Linux

```js
keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore -storepass android -keypass android | openssl sha1 -binary | openssl base64
```

### TO DO

- [ ] dynamic agreement(https://developers.kakao.com/docs/android/user-management#동적동의)
- [v] Apply TypeScript

### Troubleshooting

Recommend run ReactNativeKakaoExample.

#### IOS

##### Build Error: linker, arm64, x86_64

Check Target Membership in KakaoOpenSDK.framework you added.

## Licence

(MIT)
