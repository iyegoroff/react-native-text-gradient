import { Platform } from 'react-native';
import createTextGradientClass from './create-text-gradient-class';

export default createTextGradientClass(
  'RNLinearTextGradient',
  'RNVirtualLinearTextGradient',
  {
    start: { x: 0, y: 0 },
    end: { x: 1, y: 0 }
  },
  (props) => {
    if (Platform.OS === 'android') {
      return {
        ...props,
        start: [props.start.x, props.start.y],
        end: [props.end.x, props.end.y],
      }
    }

    return props;
  }
);
