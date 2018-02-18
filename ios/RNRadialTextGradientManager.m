#import "RNShadowRadialTextGradient.h"
#import "RNRadialTextGradientManager.h"

@implementation RNRadialTextGradientManager

RCT_EXPORT_MODULE();

- (RCTShadowView *)shadowView
{
  return [RNShadowRadialTextGradient new];
}

RCT_EXPORT_SHADOW_PROPERTY(center, CGPoint);
RCT_EXPORT_SHADOW_PROPERTY(radius, CGFloat);

@end
