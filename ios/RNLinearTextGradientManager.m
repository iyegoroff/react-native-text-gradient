#import "RNShadowLinearTextGradient.h"
#import "RNLinearTextGradientManager.h"

@implementation RNLinearTextGradientManager

RCT_EXPORT_MODULE();

- (RCTShadowView *)shadowView
{
  return [RNShadowLinearTextGradient new];
}

RCT_REMAP_SHADOW_PROPERTY(start, gradientStart, CGPoint);
RCT_REMAP_SHADOW_PROPERTY(end, gradientEnd, CGPoint);

@end
