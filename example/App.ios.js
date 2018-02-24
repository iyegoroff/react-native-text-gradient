import React, { Component } from 'react';
import { Text } from 'react-native';
import { LinearTextGradient } from 'react-native-text-gradient';


export default class App extends Component {
  render() {
    return (
      <LinearTextGradient>
        Neted linear gradients
        <Text>This is just text</Text>
        <LinearTextGradient
          style={{ fontWeight: 'bold', fontSize: 72 }}
          locations={[0, 1]}
          colors={['red', 'blue']}
          start={{ x: 0, y: 0 }}
          end={{ x: 1, y: 0 }}
        >
          THIS IS TEXT GRADIENT
        </LinearTextGradient>
        <Text>This is also text</Text>
      </LinearTextGradient>
    );
  }
}