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
  TextInput,
} from 'react-native';
import RNKakao from 'react-native-kakao';

export default class ReactNativeKakaoExample extends Component {

  constructor(props) {
    super(props);

    this.state = {
      userInfo: ''
    };
  }

  _kakaoLogin = () => {
    RNKakao.login()
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
  }

  _kakaoLogout = () => {
    RNKakao.logout()
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
  }

  _userInfo = () => {
    RNKakao.userInfo()
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
  }

  _onPressLogin = () => {
    console.log("_onPressLogin");
    this._kakaoLogin();
  }

  _onPressLogout = () => {
    console.log("_onPressLogout");
    this._kakaoLogout();
  }

  _clear = () => {
    this.setState({
      userInfo: ""
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          React Native Kakao
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
            source={require('./assets/images/kakao_login_btn.png')}
          />
        </TouchableOpacity>
        <TouchableOpacity
          style={{alignItems: 'center'}}
          onPress={this._onPressLogout}>
          <Text style={{textAlign: 'center', backgroundColor: 'yellow', width: 100}}>LotOut</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={{alignItems: 'center', height: 30}}
          onPress={this._clear}>
          <Text style={{textAlign: 'center', backgroundColor: 'grey', width: 100}}>Reset</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={{alignItems: 'center', height: 30}}
          onPress={this._userInfo}>
          <Text style={{textAlign: 'center', backgroundColor: 'blue', width: 100}}>UserInfo</Text>
        </TouchableOpacity>

        <View style={{width: '80%', height: 330, alignItems: 'center'}}>
          <Text>UserInfo</Text>
          <TextInput
            style={styles.userInfo}
            pointerEvents="none"
            multiline={true}
            numberOfLines={22}
            editable={false}
            maxHeight={300}
            value={this.state.userInfo}/>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection:'column',
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
  userInfo: {
    flex: 1,
    width: '100%',
    marginTop: 10,
    backgroundColor: 'grey',
    color: 'white',
  }
});
