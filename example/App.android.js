import React, { Component } from 'react';
import { LinearTextGradient } from 'react-native-text-gradient';


export default class App extends Component {
  render() {
    return (
      <LinearTextGradient
        style={{ fontWeight: 'bold', fontSize: 72 }}
        locations={[0, 1]}
        colors={['red', 'blue']}
        start={{ x: 0, y: 0 }}
        end={{ x: 1, y: 0 }}
      >
        THIS IS TEXT GRADIENT
      </LinearTextGradient>
    );
  }
}