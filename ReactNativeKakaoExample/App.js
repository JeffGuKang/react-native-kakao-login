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
import RNKakao from 'react-native-kakao';

// eslint-disable-next-line import/no-unresolved
const KAKAO_BUTTON_IMG = require('./assets/images/kakaologinbtn.png');

export default class ReactNativeKakaoExample extends Component {
  constructor(props) {
    super(props);

    this.state = {
      userInfo: ''
    };
  }

  kakaoLogin = () => {
    RNKakao.login()``
      .then((result) => {
        this.setState({
          userInfo: JSON.stringify(result)
        });
      })
      .catch((error) => {
        this.setState({
          userInfo: `Error: ${error}`
        });
      });
  }

  kakaoLogout = () => {
    RNKakao.logout()
      .then((result) => {
        this.setState({
          userInfo: JSON.stringify(result)
        });
      })
      .catch((error) => {
        this.setState({
          userInfo: `Error: ${error}`
        });
      });
  }

  userInfo = () => {
    RNKakao.userInfo()
      .then((result) => {
        this.setState({
          userInfo: JSON.stringify(result)
        });
      })
      .catch((error) => {
        this.setState({
          userInfo: `Error: ${error}`
        });
      });
  }

  onPressLogin = () => {
    this.kakaoLogin();
  }

  onPressLogout = () => {
    this.kakaoLogout();
  }

  clear = () => {
    this.setState({
      userInfo: ''
    });
  }

  render() {
    const { userInfo } = this.state;

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          React Native Kakao
        </Text>
        <Text style={styles.instructions} />
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

        <View>
          <TouchableOpacity
            style={{ alignItems: 'center' }}
            onPress={this.onPressLogout}
          >
            <Text style={styles.button}>LotOut</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={{ alignItems: 'center' }}
            onPress={this.clear}
          >
            <Text style={styles.button}>Reset</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={{ alignItems: 'center' }}
            onPress={this.userInfo}
          >
            <Text style={styles.button}>UserInfo</Text>
          </TouchableOpacity>
        </View>

        <View style={{ width: '80%', height: 330, alignItems: 'center' }}>
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
  button: {
    textAlign: 'center',
    backgroundColor: 'yellow',
    width: 200,
    height: 80,
  },
  userInfo: {
    flex: 1,
    width: '100%',
    marginTop: 10,
    backgroundColor: 'grey',
    color: 'white',
  }
});
