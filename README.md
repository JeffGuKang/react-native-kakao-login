# React Native Kakao

Supported operating systems are >= Android 4.1 (API 16) and >= iOS 10.0.
Tested React Native 0.57v

## Introduction

React Native module for using kakao login sdk.

## Installation

Auto install is supported by npm.

```
  npm install --save react-native-kakao
  react-native link react-native-kakao
```

Yarn

```
  yarn add react-native-kakao
  react-native link react-native-kakao
```

## Example

Refer to ReactNativeKakaoExample.

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
let authTypes = [RNKakao.KOAuthTypeTalk, RNKakao.KOAuthTypeStory, RNKakao.KOAuthTypeAccount];

RNKakao.login(authTypes)
.then(user => {
  console.log(user);
  this.setState({user: user});
})
.catch(error => {
  console.log(error);
})
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

Recommend test on real device instead of simulator. Latest Kakao SDK is not support x86_64 architecture.

[Officail Kakao](https://developers.kakao.com/docs/ios#시작하기-개발환경)

- Install Kakao SDK

  1. Download [latest SDK](https://developers.kakao.com/sdk/latest-ios-sdk)

  2. Drag and drop framework.
    ![Drag&Drop](https://developers.kakao.com/assets/images/ios/drag_sdk.png)
    ![Settings](https://developers.kakao.com/assets/images/ios/drag_sdk_dialog.png)

  3. Check target settings
    ![build phase](https://developers.kakao.com/assets/images/ios/link_binary_with_libraries_confirm.png)

  4. Add a argument `-all_load` in `Other Linker Flags`.
    ![argument](https://developers.kakao.com/assets/images/ios/other_linker_flags.png)

- Register your application in Kakao.
  [Official](https://developers.kakao.com/docs/ios#시작하기-앱-생성)

  1. Make new app
    [Make new app](https://developers.kakao.com/apps/new)
    ![makeapp](https://developers.kakao.com/assets/images/dashboard/dev_017.png)

  2. Add iOS platform
    ![addios](https://developers.kakao.com/assets/images/dashboard/dev_018.png)

    iOS bundle id must same with XCode project's Bundle Identifier.

- App settings in project

  1. Add URL types
    Add `kakao<yourappId>` in URL Schemes
    ![url types](https://developers.kakao.com/assets/images/ios/url_types.png)

  2. Add native app key in plist
    ![addkakaoid](https://developers.kakao.com/assets/images/ios/setting_plist.png)

- Add codes to `AppDelegate.m`
  ```
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
(...ing)

## Install

Add maven
build.gradle

```
subprojects {
    repositories {
        mavenCentral()
        maven { url 'http://devrepo.kakao.com:8088/nexus/content/groups/public/' }
    }
}
```

### Troubleshooting

Recommend run ReactNativeKakaoExample.

#### IOS

##### Build Error: linker, arm64, x86_64

추가한 KakaoOpenSDK.framewrok 를 눌러 Target Membership 체크가 정상적으로 되어 있는지 확인한다.

#### Android

  `compile group: 'com.kakao.sdk', name: 'usermgmt', version: '1.1.36'`

   1.2 버전 이상에서는 빌드가 되지 않는다.
   최신버전(1.3) 을 사용하기 위해서
   - Gradle 2.14.1
   - Android Gradle Plugin 2.2.3
   이상을 사용하기를 권장한다.

## Licence
(MIT)
