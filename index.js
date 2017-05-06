import { NativeModules } from 'react-native';
const Kakao = NativeModules.ReactNativeKakao;

const RCTKakao = {

  /**
   * Kakao Login
   */
  login() {
    return new Promise((resolve, reject) => {
      Kakao.login()
      .then(result => {
        return resolve(result);
      })
      .catch(error => {
        return reject(error);
      })
    });
  }


}

export default RCTKakao;
