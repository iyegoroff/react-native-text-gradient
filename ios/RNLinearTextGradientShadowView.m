#import "RNLinearGradientUtils.h"
#import "RNLinearTextGradientShadowView.h"

@implementation RNLinearTextGradientShadowView

- (UIColor *)gradientWithFrame:(CGRect)frame
{
  return [RNLinearGradientUtils gradientWithFrame:frame shadowView:self];
}

- (NSDictionary *)gradientComparisonKey:(CGRect)frame
{
  return [RNLinearGradientUtils gradientComparisonKey:frame shadowView:self];
}

@end
