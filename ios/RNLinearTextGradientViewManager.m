#import "RNLinearTextGradientShadowView.h"
#import "RNLinearTextGradientViewManager.h"

@implementation RNLinearTextGradientViewManager

- (RNLinearTextGradientShadowView *)createShadowView
{
  return [[RNLinearTextGradientShadowView alloc] initWithBridge:self.bridge];
}

RCT_EXPORT_MODULE(RNLinearTextGradient);

RCT_EXPORT_SHADOW_PROPERTY(gradientStart, CGPoint);
RCT_EXPORT_SHADOW_PROPERTY(gradientEnd, CGPoint);

@end
