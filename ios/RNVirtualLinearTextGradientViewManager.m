#import "RNVirtualLinearTextGradientShadowView.h"
#import "RNVirtualLinearTextGradientViewManager.h"

@implementation RNVirtualLinearTextGradientViewManager

- (RCTShadowView *)shadowView
{
  return [RNVirtualLinearTextGradientShadowView new];
}

RCT_EXPORT_MODULE(RNVirtualLinearTextGradient)

RCT_EXPORT_SHADOW_PROPERTY(gradientStart, CGPoint);
RCT_EXPORT_SHADOW_PROPERTY(gradientEnd, CGPoint);

@end
