#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>
#import "RCTRawTextShadowView.h"
#import "RCTTextShadowView.h"
#import "RNTextGradientView.h"
#import "RNTextGradientValue.h"
#import "RNTextGradientShadowView.h"
#import "RNTextGradientUtils.h"

@interface RCTTextShadowView ()
{
@protected
  __weak RCTBridge *_bridge;
}

- (UIEdgeInsets)paddingAsInsets;

@end

@implementation RNTextGradientShadowView

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
  
  return [RNTextGradientUtils calculateGradient:frame shadowView:self patternCache:patternCache];
}

- (void)setColors:(NSArray<NSNumber *> *)colors
{
  _colors = [RNTextGradientUtils convertColors:colors];
}

- (void)uiManagerWillPerformMounting
{
  if (YGNodeIsDirty(self.yogaNode)) {
    return;
  }
  
  [super uiManagerWillPerformMounting];
  
  [_bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
    RNTextGradientView *view = (RNTextGradientView *)viewRegistry[self.reactTag];
    
    if (view && ![self.superview isKindOfClass:[RNTextGradientShadowView class]]) {
      view.colors = [RNTextGradientShadowView extractColorsForSubviews:self];
    }
  }];
  
}

+ (NSArray<RNTextGradientValue *> *)extractColorsForSubviews:(RCTShadowView *)view
{
  NSMutableArray<RNTextGradientValue *> *colors = [NSMutableArray new];
  
  int (^__block iter)(RCTShadowView *, int) = ^(RCTShadowView *view, int textIndex) {
    if ([view isKindOfClass:[RCTRawTextShadowView class]]) {
      BOOL parentIsGradient = [RNTextGradientUtils isTextGradientShadowView:view.superview];
      NSString *text = ((RCTRawTextShadowView *)view).text;
      
      if (parentIsGradient) {
        RNTextGradientBlock colorForFrame = ^(CGRect textFrame) {
          return ((RCTBaseTextShadowView *)view.superview).textAttributes.foregroundColor;
        };
        
        [colors addObject:[[RNTextGradientValue alloc] initWithRange:NSMakeRange(textIndex, text.length)
                                                           isRawText:YES
                                                       colorForFrame:colorForFrame]];
      }
      
      return (int)(textIndex + text.length);
      
    } else if ([view isKindOfClass:[RCTBaseTextShadowView class]]) {
      int nextTextIndex = textIndex;
      
      if ([RNTextGradientUtils isTextGradientShadowView:view]) {
        RCTBaseTextShadowView <RNTextGradientShadowViewDelegate> *gradientView =
        (RCTBaseTextShadowView <RNTextGradientShadowViewDelegate> *)view;
        NSRange range = NSMakeRange(textIndex, [RNTextGradientShadowView textLength:gradientView]);
        RNTextGradientBlock colorForFrame = ^(CGRect textFrame) {
          CGRect actualFrame;
          
          if ([gradientView useViewFrame]) {
            CGRect viewFrame = gradientView.layoutMetrics.frame;
            actualFrame = CGRectMake(0, 0, viewFrame.size.width, viewFrame.size.height);
            
          } else {
            UIEdgeInsets insets = [gradientView paddingAsInsets];
            RCTShadowView *parent = [gradientView reactSuperview];
            
            while ([RNTextGradientUtils isTextGradientShadowView:parent]) {
              insets = [(RCTBaseTextShadowView <RNTextGradientShadowViewDelegate> *)parent paddingAsInsets];
              parent = [parent reactSuperview];
            }
            
            actualFrame = CGRectMake(textFrame.origin.x + insets.left,
                                     textFrame.origin.y + insets.top,
                                     textFrame.size.width,
                                     textFrame.size.height);
          }
          
          NSDictionary *comparisonKey = [gradientView gradientComparisonKey:actualFrame];
          
          if (![comparisonKey isEqualToDictionary: gradientView.previousComparisonKey]) {
            gradientView.previousComparisonKey = comparisonKey;
            gradientView.textAttributes.foregroundColor = [gradientView calculateGradient:actualFrame];
          }
          
          return gradientView.textAttributes.foregroundColor;
        };
        
        [colors addObject:[[RNTextGradientValue alloc] initWithRange:range
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
}

+ (int)textLength:(RCTBaseTextShadowView *)view
{
  int length = 0;
  
  for (RCTShadowView *subview in view.reactSubviews) {
    if ([subview isKindOfClass:[RCTRawTextShadowView class]]) {
      length += ((RCTRawTextShadowView *)subview).text.length;
      
    } else if ([subview isKindOfClass:[RCTBaseTextShadowView class]]) {
      length += [RNTextGradientShadowView textLength:(RCTBaseTextShadowView *)subview];
    }
  }
  
  return length;
}

@end
