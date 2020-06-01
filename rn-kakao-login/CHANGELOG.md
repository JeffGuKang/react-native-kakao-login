# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

### [2.2.1](https://github.com/JeffGuKang/react-native-kakao/compare/2.2.0...2.2.1) (2020-06-01)


### Bug Fixes

* **android:** correct ISessionConfig ([cbf5360](https://github.com/JeffGuKang/react-native-kakao/commit/cbf53601b0f5d0d8dd5213252c970c4c3341a46a))

## [2.2.0](https://github.com/JeffGuKang/react-native-kakao/compare/2.1.0...2.2.0) (2020-06-01)


### Features

* loginWithAllTypes; update the example ([046b3aa](https://github.com/JeffGuKang/react-native-kakao/commit/046b3aa59c97b93cbcf8f85fe64a5a35d6a32826))

```
login = async () => {
  try {
    const result = await RNKakao.loginWithAllTypes();
    console.log('result', result);      
  } catch (e) {
    console.error('error', e);
  }
}
```

## [2.1.0](https://github.com/JeffGuKang/react-native-kakao/compare/2.0.3...2.1.0) (2020-06-01)


### BREAKING CHANGE

- Use a parameter `nickname` instead of `nickName` for login information.
- Kakao SDK version bumped to 1.23.1(iOS), 1.29.0(Android)