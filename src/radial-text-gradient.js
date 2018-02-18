import { Platform } from 'react-native';
import createTextGradientClass from './create-text-gradient-class';

export default createTextGradientClass(
  'RNRadialTextGradient',
  'RNVirtualRadialTextGradient',
  {
    center: { x: 0.5, y: 0.5 },
    radius: 0.5
  },
  (props) => {
    if (Platform.OS === 'android') {
      return {
        ...props,
        center: [props.center.x, props.center.y],
      }
    }

    return props;
  }
);
