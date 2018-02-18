
# react-native-text-gradient
[![npm version](https://badge.fury.io/js/react-native-text-gradient.svg?t=1495378566925)](https://badge.fury.io/js/react-native-text-gradient)
[![Dependency Status](https://david-dm.org/iyegoroff/react-native-text-gradient.svg?t=1495378566925)](https://david-dm.org/iyegoroff/react-native-text-gradient)
[![typings included](https://img.shields.io/badge/typings-included-brightgreen.svg?t=1495378566925)](#typescript)
[![npm](https://img.shields.io/npm/l/express.svg?t=1495378566925)](https://www.npmjs.com/package/react-native-text-gradient)

React-Native text gradient component for iOS & Android.

## Getting started

`$ npm install react-native-text-gradient --save`

### Mostly automatic installation

`$ react-native link react-native-text-gradient`

### Manual installation

[link](manual_installation.md)

## Status

- iOS - component works as drop-in replacement for standard `Text` component, e.g. it is possible to have nested gradients;
- Android - WIP, currently only basic 'wrapper'-like behavior supported.
- React-Native - supported versions 0.50 - 0.52 , component is not usable with 0.53.0 because of this rn [bug](https://github.com/facebook/react-native/issues/17933)

## Usage

### LinearTextGradient
Interface is similar to https://github.com/react-native-community/react-native-linear-gradient#examples
```javascript
import { LinearTextGradient } from 'react-native-text-gradient';

<LinearTextGradient
  style={{ fontWeight: 'bold', fontSize: 72 }}
  locations={[0, 1]}
  colors={['red', 'blue']}
  start={{ x: 0, y: 0 }}
  end={{ x: 1, y: 0 }}
>
  THIS IS TEXT GRADIENT
</LinearTextGradient>
```

iOS                                            |  Android
:---------------------------------------------:|:---------------------------------------------:
<img src="ios.png" align="left" height="275">  |  <img src="android.jpg" align="right" height="275">


## Caveats

When mixing several text gradients and `Text`s top component always should be text gradient.
```javascript
<LinearTextGradient {...someGradientProps}>
  <Text>123</Text>
  qwerty
  <LinearTextGradient {...anotherGradientProps}>321</LinearTextGradient>
</LinearTextGradient>
```
This is necessary because only top text component is 'mapped' to actual native node and its children are 'virtual' from the native perspective.

## FAQ

#### Is it ready for production?
- Yes, I use it in production
