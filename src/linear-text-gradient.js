import { Platform } from 'react-native';
import createTextGradientClass from './create-text-gradient-class';

export default createTextGradientClass(
  'RNLinearTextGradient',
  'RNVirtualLinearTextGradient',
  {
    gradientStart: { x: 0, y: 0 },
    gradientEnd: { x: 1, y: 0 }
  },
  ({ start, end, gradientStart, gradientEnd, ...props }) => {
    start = start || gradientStart;
    end = end || gradientEnd;

    const isAndroid = Platform.OS === 'android';

    return {
      ...props,
      gradientStart: isAndroid ? [start.x, start.y] : start,
      gradientEnd: isAndroid ? [end.x, end.y] : end
    }
  }
);
