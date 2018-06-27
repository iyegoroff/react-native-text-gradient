import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  ScrollView
} from 'react-native';
import {LinearTextGradient} from 'react-native-text-gradient';

export default class App extends Component {
  render() {
    const size = 1;
    return (
      <ScrollView style={styles.container}>
        {/* <LinearTextGradient
          style={styles.gradient}
          locations={[0, 0.5, 1]}
          colors={['red', 'green', 'blue']}
        >
          HORIZONTAL GRADIENT███████████████████████████████████████████████████████████████████████
        </LinearTextGradient>
        <LinearTextGradient
          style={styles.gradient}
          locations={[0, 0.5, 1]}
          colors={['#00FFFF', '#FF00FF', '#FFFF00']}
          end={{ x: 0, y: 1 }}
        >
          VERTICAL GRADIENT█████████████████████████████████████████████████████████████████████████
        </LinearTextGradient> */}
        <LinearTextGradient
          style={styles.gradient}
          locations={[0, 0.5, 1]}
          colors={['yellow', 'purple', 'lime']}
          start={{ x: 1, y: 0 }}
          end={{ x: 0, y: 1 }}
        >
        {/* ██████████████████ */}
          {/* ██████████████████████████████████████████████████████████████████████████████████████████ */}
          {range(0, size).map(x => (
          <LinearTextGradient
            key={x}
            style={styles.gradient}
            useAbsoluteSizes={false}
            locations={[0, 0.25, 0.5, 0.75, 1]}
            colors={['red', 'white', 'green', 'white', 'purple']}
            start={{ x: 0, y: 0 }}
            end={{ x: 0, y: 1 }}
          >
            █████████ qeifmgb fw- wrkbm
          </LinearTextGradient>
          ))}
          {/* {range(0, size).map(x => '█████████')} */}
          {/* ██████████████████████████████████████████████████████████████████████████████████████████ */}
        </LinearTextGradient>
        <View style={styles.view}>
         {range(0, size).map(x => (
           <View key={x} style={styles.subview} />
         ))}
        </View>
      </ScrollView>
    );
  }
}

function range(start, end) {
  var array = new Array();
  for (var i = start; i < end; i++) {
    array.push(i);
  }
  return array;
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 5,
    backgroundColor: 'lightgray'
  },
  gradient: {
    fontSize: 40,
    // lineHeight: 47,
    marginBottom: 20,
    backgroundColor: 'wheat'
  },
  view: {
    position: 'absolute',
    // paddingTop: 5.5
  },
  subview: {
    width: 30,
    height: 47,
    backgroundColor: 'transparent',
    borderColor: 'black',
    borderBottomWidth: 1
  }
});
