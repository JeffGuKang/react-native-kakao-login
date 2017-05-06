/**
 * ReactNativeKakaoExample
 * https://github.com/react-native-kakao/react-native-kakao
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Image,
} from 'react-native';
import RCTKakao from 'react-native-kakao';

export default class ReactNativeKakaoExample extends Component {

  async _kakaoLogin() {
    try {
      var result = await RCTKakao.login();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  _onPressLogin() {
    this._kakaoLogin();
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.ios.js
        </Text>
        <Text style={styles.instructions}>
          Press Cmd+R to reload,{'\n'}
          Cmd+D or shake for dev menu
        </Text>
        <TouchableOpacity
          style={{top: 15, alignItems: 'center'}}
          onPress={this._onPressLogin}>
          <Image
            resizeMode={'contain'}
            style={styles.kakaoButton}
            source={require('./assets/kakao_login_btn.png')}
          />
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  kakaoButton: {
    width: 200,
  }
});

AppRegistry.registerComponent('ReactNativeKakaoExample', () => ReactNativeKakaoExample);
