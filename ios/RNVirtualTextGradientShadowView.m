#import "RNTextGradientUtils.h"
#import "RNVirtualTextGradientShadowView.h"

@implementation RNVirtualTextGradientShadowView

- (UIColor *)gradientWithFrame:(CGRect)frame
{
  MUST_BE_OVERRIDEN()
}

- (NSDictionary *)gradientComparisonKey:(CGRect)frame
{
  MUST_BE_OVERRIDEN()
}

- (UIColor *)calculateGradient:(CGRect)frame
{
  static NSMutableDictionary *patternCache;
  
  return [RNTextGradientUtils calculateGradient:frame shadowView:self patternCache:patternCache];
}

- (void)setColors:(NSArray<NSNumber *> *)colors
{
  _colors = [RNTextGradientUtils convertColors:colors];
}

- (BOOL)useViewFrame
{
  return false;
}

- (UIEdgeInsets)paddingAsInsets
{
  return (UIEdgeInsets){ 0.0f, 0.0f, 0.0f, 0.0f };
}

@end
