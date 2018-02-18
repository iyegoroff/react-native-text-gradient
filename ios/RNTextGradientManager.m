#import "RNTextGradient.h"
#import "RNTextGradientManager.h"

@implementation RNTextGradientManager

- (RNTextGradient *)view
{
  return [RNTextGradient new];
}

RCT_EXPORT_SHADOW_PROPERTY(colors, NSArray);
RCT_EXPORT_SHADOW_PROPERTY(locations, NSArray);
RCT_EXPORT_SHADOW_PROPERTY(useViewFrame, BOOL);
RCT_EXPORT_SHADOW_PROPERTY(useGlobalCache, BOOL);

@end
