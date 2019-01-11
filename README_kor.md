# React Native Kakao

리엑트 네이티브 카카오 로그인

안드로이드 >= 4.1
iOS >= 10.0
React Native는 0.57 버전에서 테스트되었습니다.

## 소개

카카오 로그인 SDK를 사용한 리엑트 네이티브 모듈

## 설치

NPM

```js
npm install --save react-native-kakao
react-native link react-native-kakao
```

Yarn

```js
yarn add react-native-kakao
react-native link react-native-kakao
```

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
    profileImageThumnail: <user picture profile thumnail url> // nullable
    ageRange: <user age range> // nullable
    gender: <user gender> // nullable
  }
```

## 프로젝트 셋업 및 초기 설정

### iOS

[Officail Kakao](https://developers.kakao.com/docs/ios#시작하기-개발환경)

- 카카오SDK 인스톨

  1. 최신 SDK [다운로드](https://developers.kakao.com/sdk/latest-ios-sdk)

  2. 드래그 앤 드롭을 해주세요. 
  
      ![Drag&Drop](https://developers.kakao.com/assets/images/ios/drag_sdk.png)
      ![Settings](https://developers.kakao.com/assets/images/ios/drag_sdk_dialog.png)

  3. 타겟 세팅 체크

      ![build phase](https://developers.kakao.com/assets/images/ios/link_binary_with_libraries_confirm.png)

  4. 빌드 설정 추가 `-all_load` in `Other Linker Flags`.

      ![argument](https://developers.kakao.com/assets/images/ios/other_linker_flags.png)

- 카카오에 앱 등록 [Official](https://developers.kakao.com/docs/ios#시작하기-앱-생성)

  1. 새로운 앱 만들기 [Make new app](https://developers.kakao.com/apps/new)

      ![makeapp](https://developers.kakao.com/assets/images/dashboard/dev_017.png)

  2. iOS 플랫폼 추가

      ![addios](https://developers.kakao.com/assets/images/dashboard/dev_018.png)

     iOS bundle id must same with XCode project's Bundle Identifier.

- 프로젝트 앱 설정

  1. URL types 추가

      Add `kakao<yourappId>` in URL Schemes
      ![url types](https://developers.kakao.com/assets/images/ios/url_types.png)

  2. plist에 네이티브 앱 키 추가

      ![addkakaoid](https://developers.kakao.com/assets/images/ios/setting_plist.png)

- `AppDelegate.m`에 코드 추가 

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

### 안드로이드(Android)

안드로이드 소스는 [helpkang](https://github.com/helpkang/react-native-kakao-login) 님의 소스를 기반으로 만들어졌습니다.

[공식 설정](https://developers.kakao.com/docs/android/getting-started#%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%84%B1)

1. `android/build.gradle`에 maven 추가

```js
subprojects {
    repositories {
        mavenCentral()
        maven { url 'http://devrepo.kakao.com:8088/nexus/content/groups/public/' }
    }
}
```

2. `android/app/build.gradle`에 디펜던시 추가
Gradle 버전에 따라 `compile`이나 `implementation`을 사용하면 됩니다. 

```js
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation "com.android.support:appcompat-v7:28.0.0"
    implementation "com.facebook.react:react-native:+"
    // From node_modules
    implementation project(':react-native-kakao') // Check this line.
}
```

3. `AndroidManifest.xml`에 앱키 등록. `KakaoWebViewActivity` 관련 설정은 추가하지 않아도 됩니다.

```xml
<application>
  <meta-data
      android:name="com.kakao.sdk.AppKey"
      android:value="YOUR_APP_KEY" />
      ...
```

`settings.gradle`은 자동설정 됩니다. 혹시나 react-native link 관련 중복이 발생하는 경우도 있으니 참고하세요.

```js
include ':react-native-kakao'
project(':react-native-kakao').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-kakao/android')
```

#### 키 해쉬
테스트를 위해 개발환경의 키 해쉬를 등록해야합니다. [공식문서](https://developers.kakao.com/docs/android/getting-started#키해시-등록)

OS X, Linux

```js
keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore -storepass android -keypass android | openssl sha1 -binary | openssl base64
```

### TO DO

- [ ] dynamic agreement(https://developers.kakao.com/docs/android/user-management#동적동의)

### Troubleshooting

Recommend run ReactNativeKakaoExample.

#### IOS

##### Build Error: linker, arm64, x86_64

추가한 KakaoOpenSDK.framewrok 를 눌러 Target Membership 체크가 정상적으로 되어 있는지 확인한다.

## Licence

(MIT)
