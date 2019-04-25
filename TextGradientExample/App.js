/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View} from 'react-native';
import {LinearTextGradient} from 'react-native-text-gradient'

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {

  grad(text, key) {
    return (
      <LinearTextGradient
        style = {
          [styles.welcome, {
            backgroundColor: 'red',
            textDecorationLine: 'underline',
          }]
        }
        key={key}
        locations={[0, 1]}
        colors={['green', 'red']}
        start={{ x: 0, y: 0 }}
        end={{ x: 1, y: 0 }}
      >
        {text}
      </LinearTextGradient>
    )
  }
  render() {
    return (
      <View style={styles.container}>
        <LinearTextGradient
          style = {
            [styles.welcome, {
              backgroundColor: 'lightgray'
            }]
          }
          locations={[0, 1]}
          colors={['lime', 'purple']}
          start={{ x: 1, y: 0 }}
          end={{ x: 0, y: 0 }}
        >
        {/* _████████████_ */}
          {this.grad('.█ ██ ███ █ ███ ',1)}
          {this.grad('.█ ',2)}
          {this.grad('2█ ')}
          {this.grad('3█ ')}
          {this.grad('.██ ')}
          {this.grad('.███ ')}
          {this.grad('.█ █ ')}
          {this.grad('.█ █ █ ')}
          {this.grad('.█ ██ ██ ', 'yellow')}
          {this.grad('.█ ')}
          {this.grad('?██ ')}
          {this.grad('.███ ')}
          {this.grad('.█ █ ')}
          {this.grad('.█ █ █ ')}
          {this.grad('.█ ██ ██ ')}
          {this.grad('.█')}
          {this.grad('.██ ')}
          {this.grad('.█████████ ')}
          {this.grad('.█ █ ')}
          {this.grad('.█ █ █ ')}
          {this.grad('.█ ██ █ ')}
          {this.grad('.█ ')}
          {this.grad('.██ ')}
          {this.grad('.███ ')}
          {this.grad('.█ █ ')}
          {this.grad('.█ █ █ ')}
        </LinearTextGradient>
        <Text style={styles.instructions}>
          To get started, edit App.js
          <Text style={[styles.instructions, {
            textDecorationLine: 'underline',
            fontWeight: 'bold'
          }]}>{instructions}</Text>
        </Text>
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
    fontSize: 30,
    textAlign: 'center',
    margin: 10,
    color: 'white'
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5
  },
});
