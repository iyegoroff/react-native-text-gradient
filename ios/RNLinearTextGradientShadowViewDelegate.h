#import "RNTextGradientShadowViewDelegate.h"

#ifndef RNLinearTextGradientShadowViewDelegate_h
#define RNLinearTextGradientShadowViewDelegate_h

@protocol RNLinearTextGradientShadowViewDelegate <RNTextGradientShadowViewDelegate>

- (CGPoint)gradientStart;
- (CGPoint)gradientEnd;

@end

#endif /* RNLinearTextGradientShadowViewDelegate_h */
