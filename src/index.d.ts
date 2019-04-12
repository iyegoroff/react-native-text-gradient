import * as React from 'react';
import * as ReactNative from 'react-native';

export interface LinearTextGradientProps extends ReactNative.TextProperties {
  readonly start?: {x: number; y: number};
  readonly end?: {x: number; y: number};
  readonly locations?: number[];
  readonly colors: string[];
  readonly useViewFrame?: boolean;
  readonly useGlobalCache?: boolean;
}

export class LinearTextGradient extends React.Component<LinearTextGradientProps, {}> {

}
