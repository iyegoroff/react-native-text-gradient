#import <React/RCTConvert.h>
#import "RNTextGradientUtils.h"
#import "RNTextGradientShadowView.h"
#import "RNVirtualTextGradientShadowView.h"

@implementation RNTextGradientUtils

+ (UIColor *)calculateGradient:(CGRect)frame
                    shadowView:(RCTBaseTextShadowView <RNTextGradientShadowViewDelegate> *)shadowView
                  patternCache:(NSMutableDictionary *)patternCache
{
  UIColor *color = shadowView.textAttributes.foregroundColor ?: [UIColor blackColor];
  NSArray<NSNumber *> *locations = [shadowView locations];
  NSArray<UIColor *> *colors = [shadowView colors];
  BOOL hasGradient = colors && locations && colors.count == locations.count;
  
  if (hasGradient) {
    if ([shadowView useGlobalCache]) {
      NSString *className = NSStringFromClass([shadowView class]);
      patternCache = patternCache ?: [NSMutableDictionary new];
      patternCache[className] = patternCache[className] ?: [NSMutableDictionary new];
      NSMutableDictionary *cache = patternCache[className];
      NSDictionary *comparisonKey = [shadowView gradientComparisonKey:frame];
      
      color = cache[comparisonKey] ?: [shadowView gradientWithFrame:frame];
      cache[comparisonKey] = color;
      
    } else {
      color = [shadowView gradientWithFrame:frame];
    }
  }
  
  return color;
}

+ (NSArray<UIColor *> *)convertColors:(NSArray<NSNumber *> *)colors
{
  NSMutableArray *gradientColors = [NSMutableArray arrayWithCapacity:colors.count];
  
  for (NSString *color in colors) {
    [gradientColors addObject:[RCTConvert UIColor:color]];
  }
  
  return gradientColors;
}

+ (BOOL)isTextGradientShadowView:(RCTShadowView *)shadowView
{
  return [shadowView isKindOfClass:[RNTextGradientShadowView class]] ||
         [shadowView isKindOfClass:[RNVirtualTextGradientShadowView class]];
}

@end
