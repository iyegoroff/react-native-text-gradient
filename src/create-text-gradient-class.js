/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 * @providesModule TextGradient
 * @flow
 */
'use strict';

import * as React from 'react';
import StyleSheetPropType from 'react-native/Libraries/StyleSheet/StyleSheetPropType';
import ReactNativeViewAttributes from 'react-native/Libraries/Components/View/ReactNativeViewAttributes';
import mergeFast from './mergeFast';
import PropTypes from 'prop-types';
import createReactClass from 'create-react-class';
import TextStylePropTypes from 'react-native/Libraries/Text/TextStylePropTypes';
import createReactNativeComponentClass from 'react-native/Libraries/Renderer/shims/createReactNativeComponentClass';
import {
  ColorPropType,
  EdgeInsetsPropType,
  Touchable,
  processColor,
  UIManager
} from 'react-native';

const stylePropType = StyleSheetPropType(TextStylePropTypes);

const createTextGradientClass = (
  uiViewClassName,
  uiVirtualViewClassName,
  defaultProps,
  convertProps
) => {
  const defaultPropAttributes = Object.keys(defaultProps)
    .reduce((acc, key) => { acc[key] = true; return acc; }, {});

  const viewConfig = {
    validAttributes: mergeFast(ReactNativeViewAttributes.UIView, {
      isHighlighted: true,
      numberOfLines: true,
      ellipsizeMode: true,
      allowFontScaling: true,
      disabled: true,
      selectable: true,
      selectionColor: true,
      adjustsFontSizeToFit: true,
      minimumFontScale: true,
      textBreakStrategy: true,
      colors: true,
      locations: true,
      useViewFrame: true,
      useGlobalCache: true,
      ...defaultPropAttributes
    }),
    uiViewClassName,
  };

  /**
 * A React component for displaying text.
 *
 * See https://facebook.github.io/react-native/docs/text.html
 */

  const TextGradient = createReactClass({
    displayName: uiViewClassName,
    propTypes: {
      /**
       * When `numberOfLines` is set, this prop defines how text will be
       * truncated.
       *
       * See https://facebook.github.io/react-native/docs/text.html#ellipsizemode
       */
      ellipsizeMode: PropTypes.oneOf(['head', 'middle', 'tail', 'clip']),
      /**
       * Used to truncate the text with an ellipsis.
       *
       * See https://facebook.github.io/react-native/docs/text.html#numberoflines
       */
      numberOfLines: PropTypes.number,
      /**
       * Set text break strategy on Android.
       *
       * See https://facebook.github.io/react-native/docs/text.html#textbreakstrategy
       */
      textBreakStrategy: PropTypes.oneOf(['simple', 'highQuality', 'balanced']),
      /**
       * Invoked on mount and layout changes.
       *
       * See https://facebook.github.io/react-native/docs/text.html#onlayout
       */
      onLayout: PropTypes.func,
      /**
       * This function is called on press.
       *
       * See https://facebook.github.io/react-native/docs/text.html#onpress
       */
      onPress: PropTypes.func,
      /**
       * This function is called on long press.
       *
       * See https://facebook.github.io/react-native/docs/text.html#onlongpress
       */
      onLongPress: PropTypes.func,
      /**
       * Defines how far your touch may move off of the button, before
       * deactivating the button.
       *
       * See https://facebook.github.io/react-native/docs/text.html#pressretentionoffset
       */
      pressRetentionOffset: EdgeInsetsPropType,
      /**
       * Lets the user select text.
       *
       * See https://facebook.github.io/react-native/docs/text.html#selectable
       */
      selectable: PropTypes.bool,
      /**
       * The highlight color of the text.
       *
       * See https://facebook.github.io/react-native/docs/text.html#selectioncolor
       */
      selectionColor: ColorPropType,
      /**
       * When `true`, no visual change is made when text is pressed down.
       *
       * See https://facebook.github.io/react-native/docs/text.html#supperhighlighting
       */
      suppressHighlighting: PropTypes.bool,
      style: stylePropType,
      /**
       * Used to locate this view in end-to-end tests.
       *
       * See https://facebook.github.io/react-native/docs/text.html#testid
       */
      testID: PropTypes.string,
      /**
       * Used to locate this view from native code.
       *
       * See https://facebook.github.io/react-native/docs/text.html#nativeid
       */
      nativeID: PropTypes.string,
      /**
       * Whether fonts should scale to respect Text Size accessibility settings.
       *
       * See https://facebook.github.io/react-native/docs/text.html#allowfontscaling
       */
      allowFontScaling: PropTypes.bool,
      /**
       * Indicates whether the view is an accessibility element.
       *
       * See https://facebook.github.io/react-native/docs/text.html#accessible
       */
      accessible: PropTypes.bool,
      /**
       * Whether font should be scaled down automatically.
       *
       * See https://facebook.github.io/react-native/docs/text.html#adjustsfontsizetofit
       */
      adjustsFontSizeToFit: PropTypes.bool,
      /**
       * Smallest possible scale a font can reach.
       *
       * See https://facebook.github.io/react-native/docs/text.html#minimumfontscale
       */
      minimumFontScale: PropTypes.number,
      /**
       * Specifies the disabled state of the text view for testing purposes.
       *
       * See https://facebook.github.io/react-native/docs/text.html#disabled
       */
      disabled: PropTypes.bool,
    },
    getDefaultProps() {
      return {
        accessible: true,
        allowFontScaling: true,
        ellipsizeMode: 'tail',
      };
    },
    getInitialState: function () {
      return mergeFast(Touchable.Mixin.touchableGetInitialState(), {
        isHighlighted: false,
      });
    },
    mixins: [],
    viewConfig: viewConfig,
    getChildContext() {
      return {
        isInAParentText: true,
      };
    },
    childContextTypes: {
      isInAParentText: PropTypes.bool
    },
    contextTypes: {
      isInAParentText: PropTypes.bool
    },
    /**
     * Only assigned if touch is needed.
     */
    _handlers: null,
    _hasPressHandler() {
      return !!this.props.onPress || !!this.props.onLongPress;
    },
    /**
     * These are assigned lazily the first time the responder is set to make plain
     * text nodes as cheap as possible.
     */
    touchableHandleActivePressIn: null,
    touchableHandleActivePressOut: null,
    touchableHandlePress: null,
    touchableHandleLongPress: null,
    touchableGetPressRectOffset: null,
    render() {
      let newProps = convertProps({
        ...defaultProps,
        ...this.props,
      });

      if (this.props.onStartShouldSetResponder || this._hasPressHandler()) {
        if (!this._handlers) {
          this._handlers = {
            onStartShouldSetResponder: () => {
              const shouldSetFromProps =
                this.props.onStartShouldSetResponder &&
                // $FlowFixMe(>=0.41.0)
                this.props.onStartShouldSetResponder();
              const setResponder = shouldSetFromProps || this._hasPressHandler();
              if (setResponder && !this.touchableHandleActivePressIn) {
                // Attach and bind all the other handlers only the first time a touch
                // actually happens.
                for (const key in Touchable.Mixin) {
                  if (typeof Touchable.Mixin[key] === 'function') {
                    (this)[key] = Touchable.Mixin[key].bind(this);
                  }
                }
                this.touchableHandleActivePressIn = () => {
                  if (
                    this.props.suppressHighlighting ||
                    !this._hasPressHandler()
                  ) {
                    return;
                  }
                  this.setState({
                    isHighlighted: true,
                  });
                };

                this.touchableHandleActivePressOut = () => {
                  if (
                    this.props.suppressHighlighting ||
                    !this._hasPressHandler()
                  ) {
                    return;
                  }
                  this.setState({
                    isHighlighted: false,
                  });
                };

                this.touchableHandlePress = (e) => {
                  this.props.onPress && this.props.onPress(e);
                };

                this.touchableHandleLongPress = (e) => {
                  this.props.onLongPress && this.props.onLongPress(e);
                };

                this.touchableGetPressRectOffset = function () {
                  return this.props.pressRetentionOffset || PRESS_RECT_OFFSET;
                };
              }
              return setResponder;
            },
            onResponderGrant: function (e, dispatchID) {
              this.touchableHandleResponderGrant(e, dispatchID);
              this.props.onResponderGrant &&
                this.props.onResponderGrant.apply(this, arguments);
            }.bind(this),
            onResponderMove: function (e) {
              this.touchableHandleResponderMove(e);
              this.props.onResponderMove &&
                this.props.onResponderMove.apply(this, arguments);
            }.bind(this),
            onResponderRelease: function (e) {
              this.touchableHandleResponderRelease(e);
              this.props.onResponderRelease &&
                this.props.onResponderRelease.apply(this, arguments);
            }.bind(this),
            onResponderTerminate: function (e) {
              this.touchableHandleResponderTerminate(e);
              this.props.onResponderTerminate &&
                this.props.onResponderTerminate.apply(this, arguments);
            }.bind(this),
            onResponderTerminationRequest: function () {
              // Allow touchable or props.onResponderTerminationRequest to deny
              // the request
              var allowTermination = this.touchableHandleResponderTerminationRequest();
              if (allowTermination && this.props.onResponderTerminationRequest) {
                allowTermination = this.props.onResponderTerminationRequest.apply(
                  this,
                  arguments,
                );
              }
              return allowTermination;
            }.bind(this),
          };
        }
        newProps = {
          ...this.props,
          ...this._handlers,
          isHighlighted: this.state.isHighlighted,
        };
      }
      newProps = {
        ...newProps,
        style: [{ color: 'gray' }, newProps.style],
      };
      if (newProps.selectionColor != null) {
        newProps = {
          ...newProps,
          selectionColor: processColor(newProps.selectionColor),
        };
      }
      if (newProps.colors != null) {
        newProps = {
          ...newProps,
          colors: newProps.colors.map(processColor)
        };
      }
      if (Touchable.TOUCH_TARGET_DEBUG && newProps.onPress) {
        newProps = {
          ...newProps,
          style: [this.props.style, { color: 'magenta' }],
        };
      }
      if (this.context.isInAParentText) {
        return <RNVirtualTextGradient {...newProps} />;
      } else {
        return <RNTextGradient {...newProps} />;
      }
    },
  });

  var PRESS_RECT_OFFSET = {
    top: 20,
    left: 20,
    right: 20,
    bottom: 30
  };

  var RNTextGradient = createReactNativeComponentClass(
    uiViewClassName,
    () => viewConfig
  );
  var RNVirtualTextGradient = RNTextGradient;

  if (UIManager[uiVirtualViewClassName]) {
    RNVirtualTextGradient = createReactNativeComponentClass(
      uiVirtualViewClassName,
      () => ({
        validAttributes: mergeFast(ReactNativeViewAttributes.UIView, {
          isHighlighted: true,
          colors: true,
          locations: true,
          useGlobalCache: true,
          ...defaultPropAttributes
        }),
        uiViewClassName: uiVirtualViewClassName
      })
    );
  }

  return TextGradient;
};

module.exports = createTextGradientClass;
