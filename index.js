import { NativeModules } from 'react-native';
const { ReactNativeKakao } = NativeModules;

export const KakaoLoginAuthType = {
  Talk: ReactNativeKakao.KOAuthTypeTalk,
  Story: ReactNativeKakao.KOAuthTypeStory,
  Account: ReactNativeKakao.KOAuthTypeAccount,
}

export default ReactNativeKakao;

/**
  Kakao Login Example

  ```
  let authTypes = [KakaoAuthType.Talk, KakaoAuthType.Story, KakaoAuthType.Account];

  RNKakao.login(authTypes)
  .then(result => {
    console.log("Result");
    console.log(result);
    this.setState({
      userInfo: JSON.stringify(result)
    });
  })
  .catch(error => {
    console.log("Error");
    console.log(error);
  })
  ```
**/
