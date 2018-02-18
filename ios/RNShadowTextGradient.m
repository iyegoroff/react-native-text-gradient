#import <React/RCTConvert.h>
#import <Text/RCTShadowRawText.h>
#import <Text/RCTShadowText.h>
#import "RNTextGradient.h"
#import "RNGradientValue.h"
#import "RNShadowTextGradient.h"

@interface RCTShadowText ()

- (UIEdgeInsets)paddingAsInsets;

@end

@implementation RNShadowTextGradient
{
  NSDictionary *_previousComparisonKey;
}

#define MUST_BE_OVERRIDEN()                                                                                              \
NSString *reason = [NSString stringWithFormat:@"Method %@ must be overriden in a subclass", NSStringFromSelector(_cmd)]; \
@throw [NSException exceptionWithName:NSInternalInconsistencyException reason:reason userInfo:nil];

- (UIColor *)gradientWithFrame:(CGRect)frame
{
  MUST_BE_OVERRIDEN()
}

- (NSDictionary *)gradientComparisonKey:(CGRect)frame
{
  MUST_BE_OVERRIDEN()
}

- (UIColor *)calculateGradient:(CGRect)frame
{
  static NSMutableDictionary *patternCache;
  UIColor *color = self.color ?: [UIColor blackColor];
  BOOL hasGradient = self.colors && self.locations && self.colors.count == self.locations.count;
  
  if (hasGradient) {
    if (self.useGlobalCache) {
      NSString *className = NSStringFromClass([self class]);
      patternCache = patternCache ?: [NSMutableDictionary new];
      patternCache[className] = patternCache[className] ?: [NSMutableDictionary new];
      NSMutableDictionary *cache = patternCache[className];
      NSDictionary *comparisonKey = [self gradientComparisonKey:frame];
      
      color = cache[comparisonKey] ?: [self gradientWithFrame:frame];
      cache[comparisonKey] = color;
      
    } else {
      color = [self gradientWithFrame:frame];
    }
  }
  
  return [color colorWithAlphaComponent:CGColorGetAlpha(color.CGColor) * self.opacity];
}

- (NSDictionary<NSString *, id> *)processUpdatedProperties:(NSMutableSet<RCTApplierBlock> *)applierBlocks
                                          parentProperties:(NSDictionary<NSString *, id> *)parentProperties
{
  NSDictionary<NSString *, id> *properties = [super processUpdatedProperties:applierBlocks
                                                            parentProperties:parentProperties];
  
  [applierBlocks addObject:^(NSDictionary<NSNumber *, UIView *> *viewRegistry) {
    RNTextGradient *view = (RNTextGradient *)viewRegistry[self.reactTag];

    if (![self.superview isKindOfClass:[RNShadowTextGradient class]]) {
      view.colors = [RNShadowTextGradient extractColorsForSubviews:self];
    }
  }];
  
  return properties;
}

- (void)setLocations:(NSArray<NSNumber *> *)locations
{
  _locations = locations;
  [self dirtyText];
}

- (void)setColors:(NSArray<NSNumber *> *)colors
{
  NSMutableArray *gradientColors = [NSMutableArray arrayWithCapacity:colors.count];
  
  for (NSString *color in colors) {
    [gradientColors addObject:[RCTConvert UIColor:color]];
  }
  
  _colors = gradientColors;
  [self dirtyText];
}

- (void)setUseViewFrame:(BOOL)useViewFrame
{
  _useViewFrame = useViewFrame;
  [self dirtyText];
}

+ (NSArray<RNGradientValue *> *)extractColorsForSubviews:(RCTShadowView *)view
{
  @try {
  NSMutableArray<RNGradientValue *> *colors = [NSMutableArray new];
  
  int (^__block iter)(RCTShadowView *, int) = ^(RCTShadowView *view, int textIndex) {
    if ([view isKindOfClass:[RCTShadowRawText class]]) {
      BOOL parentIsGradient = [view.superview isKindOfClass:[RNShadowTextGradient class]];
      NSString *text = ((RCTShadowRawText *)view).text;
      
      if (parentIsGradient) {
        RNGradientBlock colorForFrame = ^(CGRect textFrame) {
          return ((RNShadowTextGradient *)view.superview).color;
        };
        
        [colors addObject:[[RNGradientValue alloc] initWithRange:NSMakeRange(textIndex, text.length)
                                                       isRawText:YES
                                                   colorForFrame:colorForFrame]];
      }
      
      return (int)(textIndex + text.length);
      
    } else if ([view isKindOfClass:[RCTShadowText class]]) {
      int nextTextIndex = textIndex;
      
      if ([view isKindOfClass:[RNShadowTextGradient class]]) {
        RNShadowTextGradient *gradientView = (RNShadowTextGradient *)view;
        NSRange range = NSMakeRange(textIndex, [RNShadowTextGradient textLength:gradientView]);
        RNGradientBlock colorForFrame = ^(CGRect textFrame) {
          CGRect actualFrame;
          
          if (gradientView.useViewFrame) {
            CGRect viewFrame = [gradientView frame];
            actualFrame = CGRectMake(0, 0, viewFrame.size.width, viewFrame.size.height);
            
          } else {
            UIEdgeInsets insets = [gradientView paddingAsInsets];
            RCTShadowView *parent = [gradientView reactSuperview];
            
            while ([parent isKindOfClass:[RNShadowTextGradient class]]) {
              insets = [(RNShadowTextGradient *)parent paddingAsInsets];
              parent = [parent reactSuperview];
            }
            
            actualFrame = CGRectMake(textFrame.origin.x + insets.left,
                                     textFrame.origin.y + insets.top,
                                     textFrame.size.width,
                                     textFrame.size.height);
          }
          
          NSDictionary *comparisonKey = [gradientView gradientComparisonKey:actualFrame];
          
          if (![comparisonKey isEqualToDictionary:gradientView->_previousComparisonKey]) {
            gradientView->_previousComparisonKey = comparisonKey;
            gradientView.color = [gradientView calculateGradient:actualFrame];
          }
          
          return gradientView.color;
        };
        
        [colors addObject:[[RNGradientValue alloc] initWithRange:range
                                                       isRawText:NO
                                                   colorForFrame:colorForFrame]];
      }
      
      for (RCTShadowView *subview in view.reactSubviews) {
        nextTextIndex = iter(subview, nextTextIndex);
      }
      
      return nextTextIndex;
    }
    
    return textIndex;
  };
  
  iter(view, 0);
  
  return colors;
  } @catch (NSException *exception) {
    NSLog(@"extractColorsForSubviews %@", exception.reason);
  }
}

+ (int)textLength:(RCTShadowText *)view
{
  int length = 0;
  
  for (RCTShadowView *subview in view.reactSubviews) {
    if ([subview isKindOfClass:[RCTShadowRawText class]]) {
      length += ((RCTShadowRawText *)subview).text.length;
      
    } else if ([subview isKindOfClass:[RCTShadowText class]]) {
      length += [RNShadowTextGradient textLength:(RCTShadowText *)subview];
    }
  }
  
  return length;
}

@end
