#import "RNLinearTextGradientShadowViewDelegate.h"
#import "RNVirtualTextGradientShadowView.h"

@interface RNVirtualLinearTextGradientShadowView : RNVirtualTextGradientShadowView <RNLinearTextGradientShadowViewDelegate>

@property (nonatomic, assign) CGPoint gradientStart;
@property (nonatomic, assign) CGPoint gradientEnd;

@end
