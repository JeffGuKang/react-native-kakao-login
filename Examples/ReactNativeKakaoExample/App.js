/**
 * ReactNativeKakaoExample
 * https://github.com/react-native-kakao/react-native-kakao
 */

import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Image,
  TextInput,
} from 'react-native';
import RNKakao from 'rn-kakao-login';

const KAKAO_BUTTON_IMG = require('./assets/images/kakao_login_btn.png');

export default class ReactNativeKakaoExample extends Component {
  constructor(props) {
    super(props);

    this.state = {
      userInfo: '',
    };
  }

  kakaoLogin = async (withAllTypes) => {
    console.log('withAllTypes', withAllTypes);
    let result;

    try {
      if (withAllTypes) {
        result = await RNKakao.loginWithAllTypes();
      } else {
        result = await RNKakao.login();
      }
      console.log('result', result);
      this.setState({
        userInfo: JSON.stringify(result),
      });
    } catch (e) {
      this.setState({
        userInfo: `Error: ${e}`,
      });
    }
  }

  kakaoLogout = async () => {
    try {
      const result = await RNKakao.logout();
      this.setState({
        userInfo: JSON.stringify(result),
      });
    } catch (e) {
      this.setState({
        userInfo: `Error: ${e}`,
      });
    }
  }

  getUserInfo = async () => {
    try {
      const result = await RNKakao.userInfo();
      this.setState({
        userInfo: JSON.stringify(result),
      });
    } catch (e) {
      this.setState({
        userInfo: `Error: ${e}`,
      });
    }
  }

  onPressLogin = () => {
    this.kakaoLogin(false);
  }

  onPressLoginWithAllTypes = () => {
    this.kakaoLogin(true);
  }

  onPressLogout = () => {
    this.kakaoLogout();
  }

  clear = async () => {
    await this.setState({
      userInfo: '',
    });
  }

  render() {
    const { userInfo } = this.state;

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          React Native Kakao
        </Text>
        <TouchableOpacity
          style={{ top: 15, alignItems: 'center' }}
          onPress={this.onPressLogin}
        >
          <Image
            resizeMode="contain"
            style={styles.kakaoButton}
            source={KAKAO_BUTTON_IMG}
          />
        </TouchableOpacity>
        <TouchableOpacity
            style={[styles.button, { backgroundColor: '#ffe812', marginBottom: 10 }]}
            onPress={this.onPressLoginWithAllTypes}
          >
            <Text style={[styles.buttonText]}>LoginWithAllTypes</Text>
          </TouchableOpacity>

        <View style={styles.buttonContainer}>
          <TouchableOpacity
            style={[styles.button, { backgroundColor: 'red' }]}
            onPress={this.onPressLogout}
          >
            <Text style={[styles.buttonText]}>LotOut</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, { backgroundColor: 'green' }]}
            onPress={this.clear}
          >
            <Text style={[styles.buttonText]}>Reset</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, { backgroundColor: 'blue' }]}
            onPress={this.getUserInfo}
          >
            <Text style={[styles.buttonText]}>UserInfo</Text>
          </TouchableOpacity>
        </View>

        <View style={{ width: '80%', height: 330, alignItems: 'center', marginTop: 10 }}>
          <Text>UserInfo</Text>
          <TextInput
            style={styles.userInfo}
            pointerEvents="none"
            multiline
            numberOfLines={22}
            editable={false}
            maxHeight={300}
            value={userInfo}
          />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
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
  },
  buttonContainer: {
    justifyContent: 'space-between',
    height: 120,
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    width: 200,
    height: 34,
  },
  buttonText: {
    textAlign: 'center',
    fontWeight: 'bold',
    color: 'white',
    fontSize: 19,
  },
  userInfo: {
    flex: 1,
    width: '100%',
    marginTop: 10,
    backgroundColor: 'grey',
    color: 'white',
  },
});
