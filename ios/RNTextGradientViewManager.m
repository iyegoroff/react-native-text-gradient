#import <React/RCTAccessibilityManager.h>
#import "RNTextGradientView.h"
#import "RNTextGradientShadowView.h"
#import "RNTextGradientViewManager.h"
#import "RNTextGradientUtils.h"

@interface RCTTextViewManager ()
{
@protected
  NSHashTable<RCTTextShadowView *> *_shadowViews;
}

@end

@implementation RNTextGradientViewManager

- (RNTextGradientView *)view
{
  return [RNTextGradientView new];
}

- (RNTextGradientShadowView *)shadowView
{
  RNTextGradientShadowView *shadowView = [self createShadowView];
  shadowView.textAttributes.fontSizeMultiplier = self.bridge.accessibilityManager.multiplier;
  [_shadowViews addObject:shadowView];
  return shadowView;
}

- (RNTextGradientShadowView *)createShadowView
{
  MUST_BE_OVERRIDEN()
}

RCT_EXPORT_SHADOW_PROPERTY(colors, NSArray);
RCT_EXPORT_SHADOW_PROPERTY(locations, NSArray);
RCT_EXPORT_SHADOW_PROPERTY(useViewFrame, BOOL);
RCT_EXPORT_SHADOW_PROPERTY(useGlobalCache, BOOL);

@end

