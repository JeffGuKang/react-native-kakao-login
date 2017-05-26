# React Native Kakao

Supported operating systems are >= Android 4.1 (API 16) and >= iOS 9.0.

## Introduction

React Native module for using kakao sdk.

## Installation

Auto install is supported by npm.


```
  npm install --save react-native-kakao
  npm link react-native-kakao
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
    nickname: <user nickname>
    email: <user email>
    profileImage: <user picture profile url>    
    profileImageThumnail: <user picture profile thumnail url>    
  }
```

## Project setup and initialization

### iOS

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
      ...
  }

  - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
                                                   options:(NSDictionary<NSString *,id> *)options {
      ...
      if ([KOSession isKakaoAccountLoginCallback:url]) {
          return [KOSession handleOpenURL:url];
      }

      return NO;
      ...    
  }

  - (void)applicationDidBecomeActive:(UIApplication *)application
  {
      [KOSession handleDidBecomeActive];
  }
  ```

### Android
(...ing)

## Licence
(MIT)
