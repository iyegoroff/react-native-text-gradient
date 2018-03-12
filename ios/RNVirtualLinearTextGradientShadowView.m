#import "RNLinearGradientUtils.h"
#import "RNVirtualLinearTextGradientShadowView.h"

@implementation RNVirtualLinearTextGradientShadowView

- (UIColor *)gradientWithFrame:(CGRect)frame
{
  return [RNLinearGradientUtils gradientWithFrame:frame shadowView:self];
}

- (NSDictionary *)gradientComparisonKey:(CGRect)frame
{
  return [RNLinearGradientUtils gradientComparisonKey:frame shadowView:self];
}

@end
