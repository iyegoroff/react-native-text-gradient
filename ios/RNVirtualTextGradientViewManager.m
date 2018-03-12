#import "RNVirtualTextGradientShadowView.h"
#import "RNVirtualTextGradientViewManager.h"
#import "RNTextGradientUtils.h"

@implementation RNVirtualTextGradientViewManager

- (RCTShadowView *)shadowView
{
  MUST_BE_OVERRIDEN()
}

RCT_EXPORT_SHADOW_PROPERTY(colors, NSArray);
RCT_EXPORT_SHADOW_PROPERTY(locations, NSArray);
RCT_EXPORT_SHADOW_PROPERTY(useGlobalCache, BOOL);

@end
