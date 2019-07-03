/**
 * @providesModule TextGradient
 * @flow
 */
'use strict';

import * as React from 'react';
import ReactNativeViewAttributes from 'react-native/Libraries/Components/View/ReactNativeViewAttributes';
import TextAncestor from 'react-native/Libraries/Text/TextAncestor';

import createReactNativeComponentClass from 'react-native/Libraries/Renderer/shims/createReactNativeComponentClass';
import nullthrows from 'fbjs/lib/nullthrows';
import { Touchable, processColor, UIManager } from 'react-native';

const PRESS_RECT_OFFSET = {top: 20, left: 20, right: 20, bottom: 30};

const createTextGradientClass = (
  uiViewClassName,
  uiVirtualViewClassName,
  defProps,
  convertProps
) => {
  const defaultPropAttributes = Object.keys(defProps)
    .reduce((acc, key) => { acc[key] = true; return acc; }, {});

  const viewConfig = {
    validAttributes: {
      ...ReactNativeViewAttributes.UIView,
      isHighlighted: true,
      numberOfLines: true,
      ellipsizeMode: true,
      allowFontScaling: true,
      maxFontSizeMultiplier: true,
      disabled: true,
      selectable: true,
      selectionColor: true,
      adjustsFontSizeToFit: true,
      minimumFontScale: true,
      textBreakStrategy: true,
      onTextLayout: true,
      colors: true,
      locations: true,
      useViewFrame: true,
      useGlobalCache: true,
      ...defaultPropAttributes
    },
    directEventTypes: {
      topTextLayout: {
        registrationName: 'onTextLayout',
      },
    },
    uiViewClassName,
  };

  class TouchableTextGradient extends React.Component {
    static defaultProps = {
      accessible: true,
      allowFontScaling: true,
      ellipsizeMode: 'tail',
    };

    state = {
      ...Touchable.Mixin.touchableGetInitialState(),
      isHighlighted: false,
      createResponderHandlers: this._createResponseHandlers.bind(this),
      responseHandlers: null,
    };

    static getDerivedStateFromProps(nextProps, prevState) {
      return prevState.responseHandlers == null && isTouchable(nextProps)
        ? {
            responseHandlers: prevState.createResponderHandlers(),
          }
        : null;
    }

    static canRenderString = true;
    static viewConfig = viewConfig;

    render() {
      let props = convertProps({
        ...defProps,
        ...this.props,
      });

      if (isTouchable(props)) {
        props = {
          ...props,
          ...this.state.responseHandlers,
          isHighlighted: this.state.isHighlighted,
        };
      }

      props = {
        ...props,
        style: [{ color: 'gray' }, props.style],
      };

      if (props.selectionColor != null) {
        props = {
          ...props,
          selectionColor: processColor(props.selectionColor),
        };
      }

      if (props.colors != null) {
        props = {
          ...props,
          colors: props.colors.map(processColor)
        };
      }

      if (__DEV__) {
        if (Touchable.TOUCH_TARGET_DEBUG && props.onPress != null) {
          props = {
            ...props,
            style: [props.style, {color: 'magenta'}],
          };
        }
      }

      return (
        <TextAncestor.Consumer>
          {hasTextAncestor =>
            hasTextAncestor ? (
              <RNVirtualTextGradient {...props} ref={props.forwardedRef} />
            ) : (
              <TextAncestor.Provider value={true}>
                <RNTextGradient {...props} ref={props.forwardedRef} />
              </TextAncestor.Provider>
            )
          }
        </TextAncestor.Consumer>
      );
    }

    _createResponseHandlers() {
      return {
        onStartShouldSetResponder: () => {
          const {onStartShouldSetResponder} = this.props;
          const shouldSetResponder =
            (onStartShouldSetResponder == null
              ? false
              : onStartShouldSetResponder()) || isTouchable(this.props);

          if (shouldSetResponder) {
            this._attachTouchHandlers();
          }
          return shouldSetResponder;
        },
        onResponderGrant: (event, dispatchID) => {
          nullthrows(this.touchableHandleResponderGrant)(event, dispatchID);
          if (this.props.onResponderGrant != null) {
            this.props.onResponderGrant.call(this, event, dispatchID);
          }
        },
        onResponderMove: (event) => {
          nullthrows(this.touchableHandleResponderMove)(event);
          if (this.props.onResponderMove != null) {
            this.props.onResponderMove.call(this, event);
          }
        },
        onResponderRelease: (event) => {
          nullthrows(this.touchableHandleResponderRelease)(event);
          if (this.props.onResponderRelease != null) {
            this.props.onResponderRelease.call(this, event);
          }
        },
        onResponderTerminate: (event) => {
          nullthrows(this.touchableHandleResponderTerminate)(event);
          if (this.props.onResponderTerminate != null) {
            this.props.onResponderTerminate.call(this, event);
          }
        },
        onResponderTerminationRequest: () => {
          const {onResponderTerminationRequest} = this.props;
          if (!nullthrows(this.touchableHandleResponderTerminationRequest)()) {
            return false;
          }
          if (onResponderTerminationRequest == null) {
            return true;
          }
          return onResponderTerminationRequest();
        },
      };
    }

    /**
     * Lazily attaches Touchable.Mixin handlers.
     */
    _attachTouchHandlers() {
      if (this.touchableGetPressRectOffset != null) {
        return;
      }
      for (const key in Touchable.Mixin) {
        if (typeof Touchable.Mixin[key] === 'function') {
          (this)[key] = Touchable.Mixin[key].bind(this);
        }
      }
      this.touchableHandleActivePressIn = () => {
        if (!this.props.suppressHighlighting && isTouchable(this.props)) {
          this.setState({isHighlighted: true});
        }
      };
      this.touchableHandleActivePressOut = () => {
        if (!this.props.suppressHighlighting && isTouchable(this.props)) {
          this.setState({isHighlighted: false});
        }
      };
      this.touchableHandlePress = (event) => {
        if (this.props.onPress != null) {
          this.props.onPress(event);
        }
      };
      this.touchableHandleLongPress = (event) => {
        if (this.props.onLongPress != null) {
          this.props.onLongPress(event);
        }
      };
      this.touchableGetPressRectOffset = () =>
        this.props.pressRetentionOffset == null
          ? PRESS_RECT_OFFSET
          : this.props.pressRetentionOffset;
    }
  }

  const isTouchable = (props) =>
    props.onPress != null ||
    props.onLongPress != null ||
    props.onStartShouldSetResponder != null;

  const RNTextGradient = createReactNativeComponentClass(
    uiViewClassName,
    () => viewConfig,
  );

  const RNVirtualTextGradient =
    UIManager.getViewManagerConfig(uiVirtualViewClassName) == null
      ? RNTextGradient
      : createReactNativeComponentClass(uiVirtualViewClassName, () => ({
          validAttributes: {
            ...ReactNativeViewAttributes.UIView,
            isHighlighted: true,
            maxFontSizeMultiplier: true,
            colors: true,
            locations: true,
            useGlobalCache: true,
            ...defaultPropAttributes
          },
          uiViewClassName: uiVirtualViewClassName,
        }));

  // $FlowFixMe - TODO T29156721 `React.forwardRef` is not defined in Flow, yet.
  const TextGradient = React.forwardRef((props, ref) => (
    <TouchableTextGradient {...props} forwardedRef={ref} />
  ));
  TextGradient.displayName = uiVirtualViewClassName;

  return TextGradient;
};

module.exports = createTextGradientClass;
